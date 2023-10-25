package com.odencave.letribunal.providers

import com.odencave.letribunal.models.Article
import com.odencave.letribunal.models.ArticleItem
import com.odencave.letribunal.models.ArticleSnippet
import org.jsoup.Jsoup

class JsoupArticleProvider : ArticleProvider {
    override suspend fun getArticle(url: String): Article {
        val doc = Jsoup.connect(url).get()
        val selection = doc.select(".left-article-section")
        val title = doc.select("h1").firstOrNull()?.text().orEmpty()
        val subtitle = doc.select(".left-article-section > h2").firstOrNull()?.text().orEmpty()
        val headerImageUrl = doc.select("picture").first()!!.select("img").attr("src")
        val headerImage = ArticleItem.Image(headerImageUrl)
        selection.select("article > div").remove()
        selection.select("hr").remove()

        val texts = selection.select("article").first()!!.children().mapNotNull {
            when (it.tag().name) {
                "p" -> ArticleItem.Text(it.text())
                "h2" -> ArticleItem.Subtitle(it.text())
                "figure" -> {
                    val imageUrl = it.select("img").attr("src")
                    ArticleItem.Image(imageUrl)
                }

                else -> null
            }
        }
        return Article(title, subtitle, headerImage, texts)
    }
}