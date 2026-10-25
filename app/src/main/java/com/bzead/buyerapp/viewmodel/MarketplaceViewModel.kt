package com.bzead.buyerapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bzead.buyerapp.data.MarketplaceRepository
import com.bzead.buyerapp.model.Address
import com.bzead.buyerapp.model.CartItem
import com.bzead.buyerapp.model.Category
import com.bzead.buyerapp.model.Order
import com.bzead.buyerapp.model.Product
import com.bzead.buyerapp.model.ProductVariant
import com.bzead.buyerapp.model.UserProfile
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

data class CartSummary(
    val subtotal: Double,
    val shippingFee: Double,
    val discount: Double,
    val total: Double,
    val freeShippingNeeded: Double
)

class MarketplaceViewModel(
    private val repository: MarketplaceRepository = MarketplaceRepository()
) : ViewModel() {

    val products: StateFlow<List<Product>> = repository.products
    val categories: StateFlow<List<Category>> = repository.categories
    val cart: StateFlow<List<CartItem>> = repository.cart
    val wishlist: StateFlow<Set<String>> = repository.wishlist
    val orders: StateFlow<List<Order>> = repository.orders
    val userProfile: StateFlow<UserProfile> = repository.userProfile

    val featuredProducts: StateFlow<List<Product>> = products.map { list ->
        list.filter { it.isFeatured }
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val dealProducts: StateFlow<List<Product>> = products.map { list ->
        list.filter { it.isDeal }
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val cartItemCount: StateFlow<Int> = cart.map { list ->
        list.sumOf { it.quantity }
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), 0)

    val cartSummary: StateFlow<CartSummary> = cart.map { list ->
        val subtotal = list.sumOf { it.totalPrice }
        val shipping = if (subtotal >= 499.0 || subtotal == 0.0) 0.0 else 49.0
        val discount = if (subtotal >= 2000.0) 200.0 else 0.0
        val total = (subtotal + shipping - discount).coerceAtLeast(0.0)
        val needed = (499.0 - subtotal).coerceAtLeast(0.0)
        CartSummary(subtotal, shipping, discount, total, needed)
    }.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5000),
        CartSummary(0.0, 0.0, 0.0, 0.0, 499.0)
    )

    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery.asStateFlow()

    private val _searchResults = MutableStateFlow<List<Product>>(emptyList())
    val searchResults: StateFlow<List<Product>> = _searchResults.asStateFlow()

    fun updateSearchQuery(query: String) {
        _searchQuery.value = query
        _searchResults.value = repository.searchProducts(query)
    }

    fun getProduct(productId: String): Product? {
        return repository.getProductById(productId)
    }

    fun getProductsByCategory(categoryName: String): List<Product> {
        return repository.getProductsByCategory(categoryName)
    }

    fun addToCart(product: Product, variant: ProductVariant? = null, qty: Int = 1) {
        repository.addToCart(product, variant, qty)
    }

    fun updateCartQty(productId: String, variantId: String?, qty: Int) {
        repository.updateCartQuantity(productId, variantId, qty)
    }

    fun removeFromCart(productId: String, variantId: String?) {
        repository.removeFromCart(productId, variantId)
    }

    fun toggleWishlist(productId: String) {
        repository.toggleWishlist(productId)
    }

    fun isWishlisted(productId: String): Boolean {
        return wishlist.value.contains(productId)
    }

    fun placeOrder(address: Address, paymentMethod: String): Order {
        return repository.placeOrder(address, paymentMethod)
    }

    fun getOrderById(orderId: String): Order? {
        return orders.value.find { it.id == orderId }
    }
}
