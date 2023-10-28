package com.odencave.letribunal.models

import com.google.gson.annotations.SerializedName

data class CategoryWrapper(
    @SerializedName("_id")
    val id: String,
    val children: List<CategoryLT>
)

data class CategoryLT(
    @SerializedName("_id")
    val id: String = "",
    val children: List<Section>? = emptyList(),
    @SerializedName("display_name")
    val displayName: String? = "",
    @SerializedName("node_type")
    val nodeType: String = "",
    val url: String? = "",
) {
    data class Section(
        @SerializedName("_id")
        val url: String,
        val name: String,
        @SerializedName("node_type")
        val nodeType: String,
    )
}
