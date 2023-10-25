package com.odencave.letribunal.models

import com.google.gson.annotations.SerializedName
import java.time.LocalDateTime

data class ArticleSnippetLT(
    @SerializedName("content_elements")
val contentElements: List<ContentElement>
)

data class ContentElement(
    @SerializedName("_id")
    val id: String,
    val credits: Credits,
    val description: Description,
    @SerializedName("display_date")
    val displayDate: String,
    @SerializedName("promo_items")
    val promoItems: PromoItems,
    val headlines: Description,
    val subtype: String,
    val type: String,
    val websites: Websites
)

data class Websites (
    @SerializedName("latribune")
    val data: WebsiteData
)

data class WebsiteData (
    @SerializedName("website_url")
    val websiteURL: String
)

data class Credits (
    val by: List<By>
)

data class Description (
    val basic: String
)

data class PromoItems (
    @SerializedName("basic")
    val info: PromoItemInfo
)

data class PromoItemInfo (
    val caption: String,
    val url: String
)

data class By (
    val name: String,
)
