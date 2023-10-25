package com.odencave.letribunal.screens.main

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.odencave.letribunal.common.Title
import com.odencave.letribunal.models.ArticleSnippet
import com.odencave.letribunal.screens.article.ArticleActivity
import com.odencave.letribunal.screens.main.ui.theme.LeTribunalTheme
import com.odencave.letribunal.screens.main.viewmodel.MainSideEffect
import com.odencave.letribunal.screens.main.viewmodel.MainViewModel
import org.orbitmvi.orbit.compose.collectAsState
import org.orbitmvi.orbit.compose.collectSideEffect

class MainActivity : ComponentActivity() {
    private val viewModel by viewModels<MainViewModel> { MainViewModel.Factory }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            LeTribunalTheme {
                // A surface container using the 'background' color from the theme
                val state = viewModel.collectAsState().value
                viewModel.collectSideEffect { handleSideEffect(it) }
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    LazyColumn(
                        modifier = Modifier.padding(
                            horizontal = 8.dp
                        )
                    ) {
                        item {
                            Title("Les plus récents")
                        }
                        items(state.mostRecentArticles) {
                            ArticleSnippet(it)
                        }
                        item {
                            Title("Les plus populaires")
                        }
                        items(state.mostPopularArticles) {
                            ArticleSnippet(it)
                        }
                    }
                }
            }
        }
    }

    private fun handleSideEffect(effect: MainSideEffect) {
        when (effect) {
            is MainSideEffect.OnArticleSnippetClicked -> {
                val intent = ArticleActivity.intent(this, effect.article)
                startActivity(intent)
            }
        }
    }

    @Composable
    fun ArticleSnippet(snippet: ArticleSnippet) {
        Row(
            modifier = Modifier.padding(
                horizontal = 4.dp,
                vertical = 8.dp,
            ).height(100.dp).clickable {
                viewModel.onClickCategory(snippet)
            }
        ) {
            Column(
                modifier = Modifier.weight(1.5f).padding(end = 10.dp)
            ) {
                Text(text = snippet.title)
                Text(text = snippet.dateLabel,
                    fontStyle = FontStyle.Italic,
                    style = TextStyle(
                        color = Color.Gray,
                        fontSize = TextUnit(value = 16.0F, type = TextUnitType.Sp),
                    ),
                )
            }
            AsyncImage(
                model = snippet.imageUrl,
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier.weight(1f).height(100.dp)
            )
        }
    }
}