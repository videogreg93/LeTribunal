package com.odencave.letribunal.models

data class Article(
    val title: String,
    val subtitle: String,
    val headerImage: ArticleItem.Image,
    val texts: List<ArticleItem>
)
