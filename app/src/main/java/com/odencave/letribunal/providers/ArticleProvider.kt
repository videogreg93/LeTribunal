package com.odencave.letribunal.providers

import com.odencave.letribunal.models.Article

interface ArticleProvider {
    suspend fun getArticle(url: String): Article
}