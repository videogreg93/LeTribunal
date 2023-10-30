package com.odencave.letribunal.screens.main

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredWidth
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DrawerState
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.NavigationDrawerItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.odencave.letribunal.common.Title
import com.odencave.letribunal.models.ArticleSnippet
import com.odencave.letribunal.screens.article.ArticleActivity
import com.odencave.letribunal.screens.main.ui.theme.LeTribunalTheme
import com.odencave.letribunal.screens.main.viewmodel.MainSideEffect
import com.odencave.letribunal.screens.main.viewmodel.MainState
import com.odencave.letribunal.screens.main.viewmodel.MainViewModel
import com.odencave.letribunal.screens.main.viewmodel.NavigationItem
import kotlinx.coroutines.launch
import org.orbitmvi.orbit.compose.collectAsState
import org.orbitmvi.orbit.compose.collectSideEffect

@OptIn(ExperimentalMaterial3Api::class)
class MainActivity : ComponentActivity() {
    private val viewModel by viewModels<MainViewModel> { MainViewModel.Factory }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            LeTribunalTheme {
                // A surface container using the 'background' color from the theme
                val state = viewModel.collectAsState().value
                viewModel.collectSideEffect { handleSideEffect(it) }
                val scope = rememberCoroutineScope()
                val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
                Scaffold(
                    topBar = {
                        TopAppBar(
                            title = {
                                    Title(text = "Le Tribunal")
                            },
                            navigationIcon = {
                                Icon(
                                    Icons.Filled.Menu,
                                    "content description",
                                    modifier = Modifier.clickable {
                                        scope.launch { drawerState.open() }
                                    }
                                )
                            }
                        )
                    }
                ) { padding ->
                    Surface(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(padding),
                        color = MaterialTheme.colorScheme.background
                    ) {
                        val loadingComplete =
                            state.mostPopularArticles.isNotEmpty() &&
                                    state.mostRecentArticles.isNotEmpty() &&
                                    state.navigationItems.isNotEmpty()
                        if (loadingComplete) {
                            MainContentWrapper(state, drawerState)
                        } else {
                            Loading()
                        }
                    }
                }
            }
        }
    }


    @Composable
    private fun MainContentWrapper(
        state: MainState,
        drawerState: DrawerState = rememberDrawerState(initialValue = DrawerValue.Closed),
    ) {
        val scope = rememberCoroutineScope()
        val selectedItem = state.selectedNavigationItem
        ModalNavigationDrawer(drawerState = drawerState, drawerContent = {
            ModalDrawerSheet(
                modifier = Modifier.requiredWidth(320.dp)
            ) {
                LazyColumn {
                    item {
                        Spacer(Modifier.height(12.dp))
                    }
                    state.navigationItems.forEach { item ->
                        item {
                            var expanded by remember { mutableStateOf(false) }
                            val icon = if (expanded) {
                                Icons.Filled.KeyboardArrowUp
                            } else {
                                Icons.Filled.KeyboardArrowDown
                            }
                            NavigationDrawerItem(
                                label = {
                                    Text(item.name)
                                },
                                badge = {
                                    if (item.children.isNotEmpty()) {
                                        Box(
                                            modifier = Modifier
                                                .width(32.dp)
                                                .height(32.dp)
                                                .clickable { expanded = !expanded }
                                                .align(Alignment.CenterHorizontally)
                                        ) {
                                            Icon(icon, "contentDescription")
                                        }
                                    }
                                },
                                selected = item == selectedItem,
                                onClick = {
                                    scope.launch { drawerState.close() }
                                    viewModel.onSelectNavigationItem(item)
                                },
                                modifier = Modifier.padding(NavigationDrawerItemDefaults.ItemPadding)
                            )
                            if (expanded) {
                                item.children.forEach { child ->
                                    NavigationDrawerItem(
                                        label = {
                                            Text(child.name)
                                        },
                                        selected = child == selectedItem,
                                        onClick = {
                                            scope.launch { drawerState.close() }
                                            viewModel.onSelectNavigationItem(child)
                                        },
                                        modifier = Modifier.padding(NavigationDrawerItemDefaults.ItemPadding)
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }) {
            when (selectedItem) {
                NavigationItem.Home -> MainContent(state)
                else -> SpecificSectionContent(state)
            }
        }
    }

    @Composable
    private fun MainContent(state: MainState) {
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

    @Composable
    private fun SpecificSectionContent(state: MainState) {
        LazyColumn(
            modifier = Modifier.padding(
                horizontal = 8.dp
            )
        ) {
            item {
                Title(state.selectedNavigationItem.name)
            }
            items(state.specificSectionArticles) {
                ArticleSnippet(it)
            }
        }
    }

    @Composable
    private fun Loading() {
        Box(
            Modifier
                .fillMaxSize()
        ) {
            CircularProgressIndicator(
                modifier = Modifier
                    .align(Alignment.Center)
            )
        }
    }

    @Composable
    @Preview
    fun Preview() {
        Loading()
    }

    private fun handleSideEffect(effect: MainSideEffect) {
        when (effect) {
            is MainSideEffect.OnArticleSnippetClicked -> {
                val intent = ArticleActivity.intent(this, effect.article)
                startActivity(intent)
            }

            MainSideEffect.OnHomeTapped -> TODO()
            MainSideEffect.OnMyRegionTapped -> TODO()
            MainSideEffect.OnNewsTapped -> TODO()
        }
    }

    @Composable
    fun ArticleSnippet(snippet: ArticleSnippet) {
        Row(
            modifier = Modifier
                .padding(
                    horizontal = 4.dp,
                    vertical = 8.dp,
                )
                .height(100.dp)
                .clickable {
                    viewModel.onClickCategory(snippet)
                }
        ) {
            Column(
                modifier = Modifier
                    .weight(1.5f)
                    .padding(end = 10.dp)
            ) {
                Text(text = snippet.title)
                Text(
                    text = snippet.dateLabel,
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
                modifier = Modifier
                    .weight(1f)
                    .height(100.dp)
            )
        }
    }
}