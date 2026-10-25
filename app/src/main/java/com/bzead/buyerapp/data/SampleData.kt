package com.bzead.buyerapp.data

import com.bzead.buyerapp.model.Address
import com.bzead.buyerapp.model.Category
import com.bzead.buyerapp.model.Product
import com.bzead.buyerapp.model.ProductVariant
import com.bzead.buyerapp.model.UserProfile

object SampleData {
    val categories = listOf(
        Category("cat_elec", "Electronics", "electronics", "devices", 1420),
        Category("cat_fash", "Fashion & Apparel", "fashion", "checkroom", 3890),
        Category("cat_home", "Home Appliances", "home-appliances", "kitchen", 980),
        Category("cat_beauty", "Beauty & Care", "beauty-and-personal-care", "face", 1240),
        Category("cat_groc", "Gourmet Grocery", "grocery-and-gourmet-food", "shopping_basket", 2150),
        Category("cat_sports", "Sports & Outdoors", "sports-and-outdoors", "fitness_center", 670),
        Category("cat_combo", "Combo Offers", "combo-offers", "local_offer", 320)
    )

    val products = listOf(
        Product(
            id = "prod_001",
            title = "BZEAD Pro ANC Wireless Earbuds",
            description = "Active noise cancellation with 42dB ambient reduction, quad-mic ENC for crystal-clear calls, 36h battery life and IPX5 splash resistance.",
            price = 2499.0,
            mrp = 4999.0,
            discountPercent = 50,
            category = "Electronics",
            brand = "BZEAD Audio",
            rating = 4.8,
            reviewCount = 1240,
            imageUrl = "https://images.unsplash.com/photo-1590658268037-6bf12165a8df?w=600&auto=format&fit=crop&q=80",
            isFeatured = true,
            isDeal = true,
            variants = listOf(
                ProductVariant("var_001_blk", "Matte Black", "BZ-EAR-BLK", 2499.0, 4999.0, 25, color = "Black"),
                ProductVariant("var_001_wht", "Glacier White", "BZ-EAR-WHT", 2499.0, 4999.0, 18, color = "White")
            )
        ),
        Product(
            id = "prod_002",
            title = "Pure Cotton Oxford Casual Shirt",
            description = "Tailored regular fit crafted from 100% breathable organic combed cotton with button-down collar and durable double-stitch seams.",
            price = 1199.0,
            mrp = 2399.0,
            discountPercent = 50,
            category = "Fashion & Apparel",
            brand = "BZEAD Apparel",
            rating = 4.6,
            reviewCount = 890,
            imageUrl = "https://images.unsplash.com/photo-1596755094514-f87e34085b2c?w=600&auto=format&fit=crop&q=80",
            isFeatured = true,
            isDeal = false,
            variants = listOf(
                ProductVariant("var_002_m", "Sky Blue - M", "BZ-SHT-BLU-M", 1199.0, 2399.0, 30, size = "M"),
                ProductVariant("var_002_l", "Sky Blue - L", "BZ-SHT-BLU-L", 1199.0, 2399.0, 22, size = "L"),
                ProductVariant("var_002_xl", "Sky Blue - XL", "BZ-SHT-BLU-XL", 1199.0, 2399.0, 15, size = "XL")
            )
        ),
        Product(
            id = "prod_003",
            title = "Smart Electric Air Fryer 4.5L",
            description = "360-degree rapid air circulation technology for 85% less oil. Touchscreen digital panel with 8 pre-set cooking modes.",
            price = 3799.0,
            mrp = 7499.0,
            discountPercent = 49,
            category = "Home Appliances",
            brand = "KitchenPro",
            rating = 4.9,
            reviewCount = 2100,
            imageUrl = "https://images.unsplash.com/photo-1585515320310-259814833e62?w=600&auto=format&fit=crop&q=80",
            isFeatured = true,
            isDeal = true,
            variants = listOf(
                ProductVariant("var_003_std", "Graphite 4.5L", "BZ-AF-45", 3799.0, 7499.0, 12)
            )
        ),
        Product(
            id = "prod_004",
            title = "Advanced Hydrating Serum 50ml",
            description = "Infused with 2% pure hyaluronic acid and vitamin B5 to intensely hydrate and plump dull skin within 7 days.",
            price = 649.0,
            mrp = 1099.0,
            discountPercent = 41,
            category = "Beauty & Care",
            brand = "GlowLab",
            rating = 4.7,
            reviewCount = 450,
            imageUrl = "https://images.unsplash.com/photo-1620916566398-39f1143ab7be?w=600&auto=format&fit=crop&q=80",
            isFeatured = false,
            isDeal = true,
            isSponsored = true,
            variants = listOf(
                ProductVariant("var_004_50", "Standard 50ml", "BZ-GLOW-50", 649.0, 1099.0, 40)
            )
        ),
        Product(
            id = "prod_005",
            title = "Artisan Roasted Coffee Beans 500g",
            description = "Single-origin Arabica beans roasted in small batches from Chikmagalur hills with notes of dark chocolate and hazelnut.",
            price = 599.0,
            mrp = 899.0,
            discountPercent = 33,
            category = "Gourmet Grocery",
            brand = "Monsoon Roasters",
            rating = 4.8,
            reviewCount = 630,
            imageUrl = "https://images.unsplash.com/photo-1559056199-641a0ac8b55e?w=600&auto=format&fit=crop&q=80",
            isFeatured = true,
            isDeal = false,
            variants = listOf(
                ProductVariant("var_005_whole", "Whole Beans 500g", "BZ-COF-W500", 599.0, 899.0, 30),
                ProductVariant("var_005_ground", "Medium Roast Ground", "BZ-COF-G500", 599.0, 899.0, 25)
            )
        ),
        Product(
            id = "prod_006",
            title = "Pro Performance Stainless Steel Bottle 1L",
            description = "Double-wall vacuum insulated flask keeping beverages cold for 24h and hot for 12h. BPA-free leakproof lid.",
            price = 849.0,
            mrp = 1499.0,
            discountPercent = 43,
            category = "Sports & Outdoors",
            brand = "Apex Hydro",
            rating = 4.7,
            reviewCount = 310,
            imageUrl = "https://images.unsplash.com/photo-1602143407151-7111542de6e8?w=600&auto=format&fit=crop&q=80",
            isFeatured = false,
            isDeal = true,
            variants = listOf(
                ProductVariant("var_006_mat", "Matte Obsidian", "BZ-BOT-1000-BLK", 849.0, 1499.0, 50),
                ProductVariant("var_006_sil", "Brushed Silver", "BZ-BOT-1000-SLV", 849.0, 1499.0, 35)
            )
        )
    )

    val defaultAddress = Address(
        fullName = "Nitin Sharma",
        phone = "+91 98765 43210",
        streetAddress = "Flat 402, Greenfield Residences, Bandra West",
        apartment = "Tower B",
        city = "Mumbai",
        state = "Maharashtra",
        pincode = "400050",
        isDefault = true
    )

    val defaultUser = UserProfile(
        id = "usr_bzead_in_01",
        name = "Nitin Sharma",
        email = "nitin.sharma@example.com",
        phone = "+91 98765 43210",
        defaultAddress = defaultAddress
    )
}
