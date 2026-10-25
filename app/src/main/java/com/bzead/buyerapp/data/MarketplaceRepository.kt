package com.bzead.buyerapp.data

import com.bzead.buyerapp.model.Address
import com.bzead.buyerapp.model.CartItem
import com.bzead.buyerapp.model.Category
import com.bzead.buyerapp.model.Order
import com.bzead.buyerapp.model.OrderStatus
import com.bzead.buyerapp.model.Product
import com.bzead.buyerapp.model.ProductVariant
import com.bzead.buyerapp.model.UserProfile
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class MarketplaceRepository {
    private val _products = MutableStateFlow(SampleData.products)
    val products: StateFlow<List<Product>> = _products.asStateFlow()

    private val _categories = MutableStateFlow(SampleData.categories)
    val categories: StateFlow<List<Category>> = _categories.asStateFlow()

    private val _cart = MutableStateFlow<List<CartItem>>(emptyList())
    val cart: StateFlow<List<CartItem>> = _cart.asStateFlow()

    private val _wishlist = MutableStateFlow<Set<String>>(setOf("prod_001", "prod_005"))
    val wishlist: StateFlow<Set<String>> = _wishlist.asStateFlow()

    private val _userProfile = MutableStateFlow(SampleData.defaultUser)
    val userProfile: StateFlow<UserProfile> = _userProfile.asStateFlow()

    private val _orders = MutableStateFlow<List<Order>>(
        listOf(
            Order(
                id = "BZEAD-IN-782194",
                createdAt = "22 Oct 2026, 04:30 PM",
                items = listOf(
                    CartItem(SampleData.products[0], SampleData.products[0].variants.firstOrNull(), 1)
                ),
                shippingAddress = SampleData.defaultAddress,
                subtotal = 2499.0,
                shippingFee = 0.0,
                discount = 200.0,
                totalAmount = 2299.0,
                paymentMethod = "UPI (Google Pay)",
                status = OrderStatus.SHIPPED,
                trackingNumber = "SR729183492IN"
            )
        )
    )
    val orders: StateFlow<List<Order>> = _orders.asStateFlow()

    fun getProductById(id: String): Product? {
        return _products.value.find { it.id == id }
    }

    fun getProductsByCategory(categoryName: String): List<Product> {
        return _products.value.filter {
            it.category.equals(categoryName, ignoreCase = true) ||
                    categoryName.contains(it.category, ignoreCase = true)
        }
    }

    fun searchProducts(query: String): List<Product> {
        val q = query.trim().lowercase()
        if (q.isEmpty()) return _products.value
        return _products.value.filter {
            it.title.lowercase().contains(q) ||
                    it.description.lowercase().contains(q) ||
                    it.category.lowercase().contains(q) ||
                    it.brand.lowercase().contains(q)
        }
    }

    fun addToCart(product: Product, variant: ProductVariant? = null, qty: Int = 1) {
        _cart.update { current ->
            val existing = current.find {
                it.product.id == product.id && it.selectedVariant?.id == variant?.id
            }
            if (existing != null) {
                current.map {
                    if (it == existing) it.copy(quantity = it.quantity + qty) else it
                }
            } else {
                current + CartItem(product, variant, qty)
            }
        }
    }

    fun updateCartQuantity(productId: String, variantId: String?, newQuantity: Int) {
        _cart.update { current ->
            if (newQuantity <= 0) {
                current.filterNot { it.product.id == productId && it.selectedVariant?.id == variantId }
            } else {
                current.map {
                    if (it.product.id == productId && it.selectedVariant?.id == variantId) {
                        it.copy(quantity = newQuantity)
                    } else it
                }
            }
        }
    }

    fun removeFromCart(productId: String, variantId: String?) {
        _cart.update { current ->
            current.filterNot { it.product.id == productId && it.selectedVariant?.id == variantId }
        }
    }

    fun clearCart() {
        _cart.value = emptyList()
    }

    fun toggleWishlist(productId: String) {
        _wishlist.update { current ->
            if (current.contains(productId)) current - productId else current + productId
        }
    }

    fun placeOrder(address: Address, paymentMethod: String): Order {
        val currentCart = _cart.value
        val subtotal = currentCart.sumOf { it.totalPrice }
        val shippingFee = if (subtotal >= 499.0) 0.0 else 49.0
        val discount = if (subtotal >= 2000.0) 200.0 else 0.0
        val total = (subtotal + shippingFee - discount).coerceAtLeast(0.0)

        val dateFormat = SimpleDateFormat("dd MMM yyyy, hh:mm a", Locale.getDefault())
        val newOrder = Order(
            id = "BZEAD-IN-" + (100000..999999).random(),
            createdAt = dateFormat.format(Date()),
            items = currentCart,
            shippingAddress = address,
            subtotal = subtotal,
            shippingFee = shippingFee,
            discount = discount,
            totalAmount = total,
            paymentMethod = paymentMethod,
            status = OrderStatus.CONFIRMED,
            trackingNumber = "SR" + (100000000..999999999).random() + "IN"
        )

        _orders.update { listOf(newOrder) + it }
        clearCart()
        return newOrder
    }
}
