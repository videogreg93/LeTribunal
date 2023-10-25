package com.odencave.letribunal.screens.main.viewmodel

import com.odencave.letribunal.models.ArticleSnippet

data class MainState(
    val mostRecentArticles: List<ArticleSnippet> = emptyList(),
    val mostPopularArticles: List<ArticleSnippet> = emptyList()
)

sealed class MainSideEffect {
    data class OnArticleSnippetClicked(val article: ArticleSnippet): MainSideEffect()
}