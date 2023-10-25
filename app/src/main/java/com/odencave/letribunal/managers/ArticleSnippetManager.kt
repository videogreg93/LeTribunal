package com.odencave.letribunal.managers

import android.os.Build
import androidx.annotation.RequiresApi
import com.odencave.letribunal.models.ArticleSnippet
import com.odencave.letribunal.models.ContentElement
import com.odencave.letribunal.providers.ArticleSnippetProvider
import retrofit2.await
import java.text.SimpleDateFormat
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter
import java.util.Locale

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