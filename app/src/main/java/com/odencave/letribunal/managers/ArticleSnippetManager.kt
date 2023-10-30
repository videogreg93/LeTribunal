package com.odencave.letribunal.managers

import android.os.Build
import androidx.annotation.RequiresApi
import com.odencave.letribunal.models.ArticleSnippet
import com.odencave.letribunal.models.ContentElement
import com.odencave.letribunal.providers.ArticleSnippetProvider
import com.odencave.letribunal.screens.main.viewmodel.NavigationItem
import retrofit2.await
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter

@RequiresApi(Build.VERSION_CODES.O)
class ArticleSnippetManager(
    private val provider: ArticleSnippetProvider
) {

    suspend fun fetchMostRecentArticles(): List<ArticleSnippet> {
        return provider.fetchMostRecentArticles().await().contentElements.map {
            toArticleSnippet(it)
        }
    }


    suspend fun fetchMostPopularArticles(): List<ArticleSnippet> {
        return provider.fetchMostPopularArticles().await().contentElements.map {
            toArticleSnippet(it)
        }
    }

    suspend fun fetchSnippetsFromSection(path: String): List<ArticleSnippet> {
        val query =
            "{\"feature\":\"top-table-list\",\"feedOffset\":0,\"feedSize\":9,\"includeSections\":\"$path\"}"

        return provider.fetchSection(query).await().contentElements.map {
            toArticleSnippet(it)
        }
    }

    suspend fun fetchAllCategories(): List<NavigationItem> {
        val test = provider.getAllCategories().await()
        return listOf(NavigationItem.Home) + test.children.filter { it.nodeType != "link" }
            .flatMap { innerCategory ->
                val name = when (innerCategory.nodeType) {
                    "link" -> innerCategory.displayName
                    "section" -> innerCategory.name
                    else -> innerCategory.name
                }.orEmpty()
                val link = when (innerCategory.nodeType) {
                    "link" -> innerCategory.url
                    "section" -> innerCategory.id
                    else -> innerCategory.id
                }.orEmpty()
                val children = innerCategory.children.orEmpty().map { child ->
                    NavigationItem.OutsideCategory(child.name, child.url, emptyList())
                }
                val category = NavigationItem.OutsideCategory(name, link, children)
                // todo this only looks one level deep nesting wise
                listOf(category)
            }
    }


    private fun toArticleSnippet(it: ContentElement): ArticleSnippet {
        val date = ZonedDateTime.parse(it.displayDate)
        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
        return ArticleSnippet(
            it.headlines.basic,
            date.format(formatter),
            it.promoItems.info.url,
            RetrofitManager.BASE_URL + it.websites.data.websiteURL
        )
    }
}