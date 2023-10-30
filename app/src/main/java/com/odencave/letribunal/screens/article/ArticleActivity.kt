package com.odencave.letribunal.screens.article

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.odencave.letribunal.common.Title
import com.odencave.letribunal.models.ArticleItem
import com.odencave.letribunal.models.ArticleSnippet
import com.odencave.letribunal.screens.article.viewmodel.ArticleViewModel
import com.odencave.letribunal.screens.main.ui.theme.LeTribunalTheme
import org.orbitmvi.orbit.compose.collectAsState

class ArticleActivity : ComponentActivity() {
    private val viewModel by viewModels<ArticleViewModel> { ArticleViewModel.Factory }
    val articleUrl: String by lazy { intent.getStringExtra(ARTICLE_URL_KEY).orEmpty() }

    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            LeTribunalTheme {
                // A surface container using the 'background' color from the theme
                val state = viewModel.collectAsState().value
                Scaffold { padding ->
                    Surface(
                        modifier = Modifier.fillMaxSize().padding(padding),
                        color = MaterialTheme.colorScheme.background
                    ) {
                        state.article?.let { article ->
                            LazyColumn(
                                modifier = Modifier.padding(
                                    horizontal = 8.dp
                                )
                            ) {
                                item {
                                    Title(article.title)
                                }
                                item {
                                    AsyncImage(
                                        model = article.headerImage.url,
                                        contentDescription = null,
                                        modifier = Modifier.padding(
                                            top = 4.dp,
                                            bottom = 4.dp
                                        )
                                    )
                                }
                                item {
                                    Text(
                                        text = article.subtitle,
                                        modifier = Modifier.padding(
                                            start = 4.dp,
                                            end = 4.dp,
                                            top = 4.dp,
                                            bottom = 8.dp
                                        ),
                                        style = TextStyle(
                                            fontSize = TextUnit(value = 24.0F, type = TextUnitType.Sp)
                                        ),
                                    )
                                }
                                items(article.texts) {
                                    when (it) {
                                        is ArticleItem.Image -> AsyncImage(
                                            model = it.url,
                                            contentDescription = null,
                                            modifier = Modifier.padding(
                                                top = 4.dp,
                                                bottom = 4.dp
                                            )
                                        )

                                        is ArticleItem.Subtitle -> Text(
                                            text = it.text,
                                            modifier = Modifier.padding(top = 8.dp, bottom = 4.dp),
                                            style = TextStyle(
                                                fontSize = TextUnit(
                                                    value = 24.0F,
                                                    type = TextUnitType.Sp
                                                )
                                            ),
                                        )

                                        is ArticleItem.Text -> Text(
                                            text = it.text,
                                            textAlign = TextAlign.Justify,
                                            style = TextStyle(
                                                lineHeight = 26.sp,
                                                fontSize = TextUnit(
                                                    value = 18.0F,
                                                    type = TextUnitType.Sp
                                                )
                                            ),
                                            modifier = Modifier.padding(vertical = 4.dp)
                                        )
                                    }
                                    Spacer(modifier = Modifier.padding(4.dp))
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    companion object {

        private const val ARTICLE_URL_KEY = "ArticleUrlKey"

        fun intent(context: Context, article: ArticleSnippet): Intent {
            return Intent(context, ArticleActivity::class.java).apply {
                putExtra(ARTICLE_URL_KEY, article.websiteUrl)
            }
        }
    }
}