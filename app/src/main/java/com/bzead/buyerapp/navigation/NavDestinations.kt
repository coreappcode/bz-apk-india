package com.bzead.buyerapp.navigation

import kotlinx.serialization.Serializable

sealed interface Screen {
    @Serializable
    data object Home : Screen

    @Serializable
    data object Categories : Screen

    @Serializable
    data class CategoryProducts(val categoryName: String) : Screen

    @Serializable
    data class ProductDetail(val productId: String) : Screen

    @Serializable
    data class Search(val initialQuery: String = "") : Screen

    @Serializable
    data object Cart : Screen

    @Serializable
    data object Checkout : Screen

    @Serializable
    data class OrderConfirmation(val orderId: String) : Screen

    @Serializable
    data object MyOrders : Screen

    @Serializable
    data class OrderDetails(val orderId: String) : Screen

    @Serializable
    data object Wishlist : Screen

    @Serializable
    data object Profile : Screen
}
