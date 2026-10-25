# BZEAD India - Native Android Buyer Application

A modern, high-performance Android e-commerce buyer storefront built with **Kotlin** and **Jetpack Compose**, targeting the Indian marketplace (`bzead.in` / `https://www.bzead.in`).

## Features
- **Storefront Discovery:** Dynamic Home feed featuring hero promotional banners, category rails, Hot Deals, curated product grids, and trust badges.
- **Product Details & Variants:** Interactive Product Detail Screen (PDP) with image previews, variant selection (size/color/specs), pricing in INR with MRP discounts, real-time India pincode delivery checker, and stock statuses.
- **Cart & Pricing Engine:** Full-featured shopping cart with item steppers, free shipping threshold progress tracking (free delivery above ₹499), and automatic coupon discounts.
- **India Checkout & Address Management:** Tailored Indian shipping address fields, UPI / Google Pay / Card / NetBanking / COD payment methods, and immediate order placement.
- **Orders & Tracking:** Real-time buyer order history with status badges (Confirmed, Processing, Shipped, Delivered) and Shiprocket AWB tracking numbers.
- **Account & Wishlist:** Buyer profile management, saved shipping addresses, notifications preferences, and instant wishlist toggling.

## Architecture & Tech Stack
- **Language:** Kotlin (100%)
- **UI Framework:** Jetpack Compose with Material Design 3 (M3)
- **Architecture:** MVVM (Model-View-ViewModel) with StateFlow
- **Navigation:** Jetpack Navigation Compose with type-safe `@Serializable` route contracts
- **Image Loading:** Coil Compose
- **Serialization:** Kotlinx Serialization
- **Build System:** Gradle Kotlin DSL (`build.gradle.kts`) with Version Catalog (`libs.versions.toml`)
- **Package:** `com.bzead.buyerapp`
- **Domain:** `bzead.in`
- **Public URL:** `https://www.bzead.in`
