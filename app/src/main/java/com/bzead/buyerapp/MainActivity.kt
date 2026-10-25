package com.bzead.buyerapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.bzead.buyerapp.navigation.Screen
import com.bzead.buyerapp.theme.BzeadTheme
import com.bzead.buyerapp.ui.components.BzeadBottomBar
import com.bzead.buyerapp.ui.components.BzeadTopBar
import com.bzead.buyerapp.ui.screens.CategoryListScreen
import com.bzead.buyerapp.ui.screens.CategoryProductsScreen
import com.bzead.buyerapp.ui.screens.CartScreen
import com.bzead.buyerapp.ui.screens.CheckoutScreen
import com.bzead.buyerapp.ui.screens.HomeScreen
import com.bzead.buyerapp.ui.screens.MyOrdersScreen
import com.bzead.buyerapp.ui.screens.OrderConfirmationScreen
import com.bzead.buyerapp.ui.screens.ProductDetailScreen
import com.bzead.buyerapp.ui.screens.ProfileScreen
import com.bzead.buyerapp.ui.screens.SearchScreen
import com.bzead.buyerapp.viewmodel.MarketplaceViewModel

class MainActivity : ComponentActivity() {
    private val viewModel: MarketplaceViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            BzeadTheme {
                BzeadApp(viewModel = viewModel)
            }
        }
    }
}

@Composable
fun BzeadApp(viewModel: MarketplaceViewModel) {
    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    val cartCount by viewModel.cartItemCount.collectAsState()
    val wishlist by viewModel.wishlist.collectAsState()

    // Determine current screen abstraction
    val isRootScreen = currentRoute == Screen.Home::class.qualifiedName ||
            currentRoute == Screen.Categories::class.qualifiedName ||
            currentRoute == Screen.Cart::class.qualifiedName ||
            currentRoute == Screen.Profile::class.qualifiedName

    var currentScreen: Screen by remember { mutableStateOf(Screen.Home) }

    Scaffold(
        topBar = {
            BzeadTopBar(
                title = when {
                    currentRoute?.contains("CategoryProducts") == true -> "Category"
                    currentRoute?.contains("ProductDetail") == true -> "Product"
                    currentRoute?.contains("Checkout") == true -> "Checkout"
                    currentRoute?.contains("OrderConfirmation") == true -> "Order Confirmation"
                    currentRoute?.contains("MyOrders") == true -> "My Orders"
                    currentRoute?.contains("Cart") == true -> "Cart"
                    currentRoute?.contains("Search") == true -> "Search"
                    currentRoute?.contains("Profile") == true -> "Profile"
                    else -> null
                },
                canNavigateBack = !isRootScreen,
                onNavigateBack = { navController.popBackStack() },
                cartItemCount = cartCount,
                wishlistItemCount = wishlist.size,
                onSearchClick = {
                    navController.navigate(Screen.Search()) {
                        launchSingleTop = true
                    }
                },
                onCartClick = {
                    navController.navigate(Screen.Cart) {
                        launchSingleTop = true
                    }
                },
                onWishlistClick = {
                    navController.navigate(Screen.Search(initialQuery = "")) {
                        launchSingleTop = true
                    }
                }
            )
        },
        bottomBar = {
            BzeadBottomBar(
                currentScreen = currentScreen,
                cartItemCount = cartCount,
                onNavigate = { screen ->
                    currentScreen = screen
                    navController.navigate(screen) {
                        popUpTo(navController.graph.findStartDestination().id) {
                            saveState = true
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                }
            )
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            NavHost(
                navController = navController,
                startDestination = Screen.Home
            ) {
                composable<Screen.Home> {
                    currentScreen = Screen.Home
                    HomeScreen(
                        viewModel = viewModel,
                        onProductClick = { product ->
                            navController.navigate(Screen.ProductDetail(product.id))
                        },
                        onCategoryClick = { category ->
                            navController.navigate(Screen.CategoryProducts(category.name))
                        },
                        onSearchClick = {
                            navController.navigate(Screen.Search())
                        },
                        onViewAllDeals = {
                            navController.navigate(Screen.Search())
                        }
                    )
                }

                composable<Screen.Categories> {
                    currentScreen = Screen.Categories
                    CategoryListScreen(
                        viewModel = viewModel,
                        onCategoryClick = { category ->
                            navController.navigate(Screen.CategoryProducts(category.name))
                        }
                    )
                }

                composable<Screen.CategoryProducts> { backStackEntry ->
                    val args = backStackEntry.toRoute<Screen.CategoryProducts>()
                    currentScreen = Screen.CategoryProducts(args.categoryName)
                    CategoryProductsScreen(
                        categoryName = args.categoryName,
                        viewModel = viewModel,
                        onProductClick = { product ->
                            navController.navigate(Screen.ProductDetail(product.id))
                        }
                    )
                }

                composable<Screen.ProductDetail> { backStackEntry ->
                    val args = backStackEntry.toRoute<Screen.ProductDetail>()
                    currentScreen = Screen.ProductDetail(args.productId)
                    ProductDetailScreen(
                        productId = args.productId,
                        viewModel = viewModel,
                        onAddToCart = { product, variant ->
                            viewModel.addToCart(product, variant)
                        },
                        onBuyNow = { product, variant ->
                            viewModel.addToCart(product, variant)
                            navController.navigate(Screen.Checkout)
                        }
                    )
                }

                composable<Screen.Search> { backStackEntry ->
                    val args = backStackEntry.toRoute<Screen.Search>()
                    currentScreen = Screen.Search(args.initialQuery)
                    SearchScreen(
                        initialQuery = args.initialQuery,
                        viewModel = viewModel,
                        onProductClick = { product ->
                            navController.navigate(Screen.ProductDetail(product.id))
                        }
                    )
                }

                composable<Screen.Cart> {
                    currentScreen = Screen.Cart
                    CartScreen(
                        viewModel = viewModel,
                        onProceedToCheckout = {
                            navController.navigate(Screen.Checkout)
                        },
                        onStartShopping = {
                            navController.navigate(Screen.Home) {
                                popUpTo(navController.graph.findStartDestination().id)
                            }
                        }
                    )
                }

                composable<Screen.Checkout> {
                    currentScreen = Screen.Checkout
                    CheckoutScreen(
                        viewModel = viewModel,
                        onOrderPlaced = { order ->
                            navController.navigate(Screen.OrderConfirmation(order.id)) {
                                popUpTo(Screen.Cart) { inclusive = true }
                            }
                        }
                    )
                }

                composable<Screen.OrderConfirmation> { backStackEntry ->
                    val args = backStackEntry.toRoute<Screen.OrderConfirmation>()
                    currentScreen = Screen.OrderConfirmation(args.orderId)
                    OrderConfirmationScreen(
                        orderId = args.orderId,
                        viewModel = viewModel,
                        onViewOrderDetails = {
                            navController.navigate(Screen.MyOrders)
                        },
                        onContinueShopping = {
                            navController.navigate(Screen.Home) {
                                popUpTo(navController.graph.findStartDestination().id) {
                                    inclusive = true
                                }
                            }
                        }
                    )
                }

                composable<Screen.MyOrders> {
                    currentScreen = Screen.MyOrders
                    MyOrdersScreen(
                        viewModel = viewModel,
                        onOrderClick = { order ->
                            // View details
                        }
                    )
                }

                composable<Screen.Profile> {
                    currentScreen = Screen.Profile
                    ProfileScreen(
                        viewModel = viewModel,
                        onNavigateToOrders = {
                            navController.navigate(Screen.MyOrders)
                        }
                    )
                }
            }
        }
    }
}
