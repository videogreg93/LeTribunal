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
    open val name: String,
    open val path: String,
    open val children: List<NavigationItem> = emptyList()
) {
    object Home : NavigationItem("Accueil", "")
    object News : NavigationItem("Actualités", "/actualites")
    object MyRegion : NavigationItem("Ma région", "/ma-region")

    object Chroniques : NavigationItem("Chroniques", "/chroniques") {
        object LeGamer : NavigationItem("Le Gamer", "/chroniques/le-gamer")
    }

    data class OutsideCategory(
        override val name: String = "",
        override val path: String = "",
        override val children: List<NavigationItem>,
    ) : NavigationItem(name, path)
}

sealed class MainSideEffect {
    data class OnArticleSnippetClicked(val article: ArticleSnippet) : MainSideEffect()
    object OnHomeTapped : MainSideEffect()
    object OnNewsTapped : MainSideEffect()
    object OnMyRegionTapped : MainSideEffect()
}