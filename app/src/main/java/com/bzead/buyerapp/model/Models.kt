package com.bzead.buyerapp.model

import kotlinx.serialization.Serializable

@Serializable
data class ProductVariant(
    val id: String,
    val name: String,
    val sku: String,
    val price: Double,
    val mrp: Double,
    val stockQuantity: Int = 10,
    val color: String? = null,
    val size: String? = null
)

@Serializable
data class Product(
    val id: String,
    val title: String,
    val description: String,
    val price: Double,
    val mrp: Double,
    val discountPercent: Int,
    val category: String,
    val brand: String,
    val rating: Double,
    val reviewCount: Int,
    val imageUrl: String,
    val additionalImages: List<String> = emptyList(),
    val isFeatured: Boolean = false,
    val isDeal: Boolean = false,
    val isSponsored: Boolean = false,
    val inStock: Boolean = true,
    val variants: List<ProductVariant> = emptyList(),
    val deliveryDays: Int = 3,
    val returnDays: Int = 7
)

@Serializable
data class Category(
    val id: String,
    val name: String,
    val slug: String,
    val iconName: String,
    val itemCount: Int
)

@Serializable
data class CartItem(
    val product: Product,
    val selectedVariant: ProductVariant? = null,
    val quantity: Int = 1
) {
    val unitPrice: Double
        get() = selectedVariant?.price ?: product.price

    val totalPrice: Double
        get() = unitPrice * quantity
}

@Serializable
data class Address(
    val id: String = "addr_default",
    val fullName: String,
    val phone: String,
    val streetAddress: String,
    val apartment: String = "",
    val city: String,
    val state: String,
    val pincode: String,
    val isDefault: Boolean = true
)

@Serializable
enum class OrderStatus {
    CONFIRMED,
    PROCESSING,
    SHIPPED,
    DELIVERED,
    CANCELLED
}

@Serializable
data class Order(
    val id: String,
    val createdAt: String,
    val items: List<CartItem>,
    val shippingAddress: Address,
    val subtotal: Double,
    val shippingFee: Double,
    val discount: Double,
    val totalAmount: Double,
    val paymentMethod: String,
    val status: OrderStatus,
    val trackingNumber: String? = null
)

data class UserProfile(
    val id: String,
    val name: String,
    val email: String,
    val phone: String,
    val defaultAddress: Address? = null
)
