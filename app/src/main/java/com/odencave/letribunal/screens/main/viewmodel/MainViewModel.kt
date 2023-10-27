package com.odencave.letribunal.screens.main.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.odencave.letribunal.managers.ArticleSnippetManager
import com.odencave.letribunal.managers.RetrofitManager
import com.odencave.letribunal.models.ArticleSnippet
import com.odencave.letribunal.providers.ArticleProvider
import com.odencave.letribunal.providers.JsoupArticleProvider
import org.orbitmvi.orbit.ContainerHost
import org.orbitmvi.orbit.syntax.simple.intent
import org.orbitmvi.orbit.syntax.simple.postSideEffect
import org.orbitmvi.orbit.syntax.simple.reduce
import org.orbitmvi.orbit.viewmodel.container
import retrofit2.create

class MainViewModel(
    val articleSnippetProvider: ArticleSnippetManager,
) : ContainerHost<MainState, MainSideEffect>, ViewModel() {

    val navigationItems = listOf(
        NavigationItem.Home, NavigationItem.MyRegion, NavigationItem.News
    )

    override val container = container<MainState, MainSideEffect>(
        MainState(
            navigationItems = navigationItems,
            selectedNavigationItem = navigationItems.first(),
        )
    ) {
        fetchRecentArticles()
        fetchPopularArticles()
    }

    fun onSelectNavigationItem(navigationItem: NavigationItem) = intent {
        val articles = articleSnippetProvider.fetchSnippetsFromSection("actualites/")
        reduce {
            state.copy(selectedNavigationItem = navigationItem, specificSectionArticles = articles)
        }
    }

    private fun triggerEffect(effect: MainSideEffect) = intent {
        postSideEffect(effect)
    }

    private fun fetchRecentArticles() = intent {
        val articles = articleSnippetProvider.fetchMostRecentArticles()
        reduce {
            state.copy(
                mostRecentArticles = articles
            )
        }
    }

    private fun fetchPopularArticles() = intent {
        val articles = articleSnippetProvider.fetchMostPopularArticles()
        reduce {
            state.copy(
                mostPopularArticles = articles
            )
        }
    }

    fun onClickCategory(article: ArticleSnippet) = intent {
        postSideEffect(MainSideEffect.OnArticleSnippetClicked(article))
    }

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                MainViewModel(
                    ArticleSnippetManager(RetrofitManager.retrofit.create())
                )
            }
        }
    }

}