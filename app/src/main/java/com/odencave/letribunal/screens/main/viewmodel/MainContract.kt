package com.odencave.letribunal.screens.main.viewmodel

import com.odencave.letribunal.models.ArticleSnippet

data class MainState(
    val mostRecentArticles: List<ArticleSnippet> = emptyList(),
    val mostPopularArticles: List<ArticleSnippet> = emptyList(),
    val specificSectionArticles: List<ArticleSnippet> = emptyList(),
    val navigationItems: List<NavigationItem> = emptyList(),
    val selectedNavigationItem: NavigationItem,
)

sealed class NavigationItem(
    val name: String,
) {
    object Home: NavigationItem("Accueil")
    object News: NavigationItem("Actualités")
    object MyRegion: NavigationItem("Ma région")
}

sealed class MainSideEffect {
    data class OnArticleSnippetClicked(val article: ArticleSnippet): MainSideEffect()
    object OnHomeTapped: MainSideEffect()
    object OnNewsTapped: MainSideEffect()
    object OnMyRegionTapped: MainSideEffect()
}