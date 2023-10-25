package com.odencave.letribunal.models

sealed class ArticleItem {
    class Text(val text: String): ArticleItem()
    class Subtitle(val text: String): ArticleItem()
    class Image(val url: String): ArticleItem()
}
