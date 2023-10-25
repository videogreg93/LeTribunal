package com.odencave.letribunal.screens.article.viewmodel

import com.odencave.letribunal.models.Article

data class ArticleState(
    val article: Article? = null
)

sealed class ArticleSideEffect {}