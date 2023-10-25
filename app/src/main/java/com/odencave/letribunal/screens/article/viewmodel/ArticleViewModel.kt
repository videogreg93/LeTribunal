package com.odencave.letribunal.screens.article.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.odencave.letribunal.common.getActivity
import com.odencave.letribunal.providers.ArticleProvider
import com.odencave.letribunal.providers.JsoupArticleProvider
import com.odencave.letribunal.screens.article.ArticleActivity
import org.orbitmvi.orbit.ContainerHost
import org.orbitmvi.orbit.syntax.simple.intent
import org.orbitmvi.orbit.syntax.simple.reduce
import org.orbitmvi.orbit.viewmodel.container

class ArticleViewModel(
    val articleProvider: ArticleProvider,
    val url: String,
) : ContainerHost<ArticleState, ArticleSideEffect>, ViewModel() {

    override val container = container<ArticleState, ArticleSideEffect>(ArticleState()) {
        fetchInitialArticle()
    }

    private fun fetchInitialArticle() = intent {
        val deleteMe = "https://www.latribune.ca/actualites/actualites-locales/sherbrooke/2023/10/25/les-plans-de-la-future-place-publique-sur-frontenac-devoiles-6LLQQADL3ZGLPAKRYYD66PZWDA/"
        val article = articleProvider.getArticle(url)
        reduce {
            state.copy(article)
        }
    }

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val url = getActivity<ArticleActivity>().articleUrl
                ArticleViewModel(JsoupArticleProvider(), url)
            }
        }
    }

}