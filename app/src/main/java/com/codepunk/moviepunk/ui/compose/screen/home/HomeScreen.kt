package com.codepunk.moviepunk.ui.compose.screen.home

import android.content.res.Configuration
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.isUnspecified
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalWindowInfo
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import androidx.paging.PagingData
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemKey
import coil3.compose.SubcomposeAsyncImage
import com.codepunk.moviepunk.domain.model.Movie
import com.codepunk.moviepunk.ui.compose.screen.preview.ScreenPreviews
import com.codepunk.moviepunk.ui.theme.MoviePunkTheme
import kotlinx.coroutines.flow.flowOf
import timber.log.Timber

@Composable
fun HomeScreen(
    state: HomeState,
    trendingMovies: LazyPagingItems<Movie>,
    modifier: Modifier = Modifier,
    @Suppress("unused")
    onIntent: (HomeIntent) -> Unit = {}
) {
    LaunchedEffect(key1 = trendingMovies.loadState) {
        Timber.i("trendingMovies.loadState = ${trendingMovies.loadState}")
        if (trendingMovies.loadState.refresh is LoadState.Error) {
            "Hello"
        }
    }

    Timber.d("curatedContentItem = ${state.curatedContentItem}")

    val configuration = LocalConfiguration.current
    val windowInfo = LocalWindowInfo.current
    val density = LocalDensity.current

    // In portrait, the height will be whatever
    // In landscape, the height should be no larger than the screen WIDTH

    Box(
        modifier = modifier.fillMaxSize()
    ) {
        if (trendingMovies.loadState.refresh is LoadState.Loading) {
            CircularProgressIndicator(
                modifier = Modifier.align(Alignment.Center)
            )
        } else {
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(12.dp),
            ) {
                item {
                    SubcomposeAsyncImage(
                        modifier = Modifier
                            .fillMaxWidth(),
                        model = state.curatedContentItem.url,
                        contentDescription = "Curated Content Image",
                        success = { successState ->
                            Image(
                                painter = successState.painter,
                                contentDescription = contentDescription,
                                contentScale = ContentScale.Crop,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .then(
                                        // TODO This is messy but it works for now. Need to find a cleaner way.
                                        when {
                                            configuration.orientation == Configuration.ORIENTATION_LANDSCAPE -> {
                                                Modifier.height(
                                                    with(density) {
                                                        windowInfo.containerSize.height.toDp()
                                                    }
                                                )
                                            }
                                            painter.intrinsicSize.isUnspecified -> {
                                                Modifier.height(
                                                    with(density) {
                                                        windowInfo.containerSize.width.times(880f / 600f).toDp()
                                                    }
                                                )
                                            }
                                            else -> {
                                                Modifier.aspectRatio(
                                                    painter.intrinsicSize.width / painter.intrinsicSize.height
                                                )
                                            }
                                        }
                                    )
                            )
                        }
                    )
                }

                items(
                    count = trendingMovies.itemCount,
                    key = trendingMovies.itemKey { it.id }
                ) { index ->
                    trendingMovies[index]?.also { movie ->
                        MovieCard(
                            movie = movie
                        )
                    }
                }

                item {
                    if (trendingMovies.loadState.append is LoadState.Loading) {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(150.dp)
                        ) {
                            CircularProgressIndicator(
                                modifier = Modifier.align(Alignment.Center)
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun MovieCard(
    movie: Movie,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .height(150.dp)
    ) {
        Text(
            text = movie.title
        )
    }
}

@ScreenPreviews
@Composable
fun HomeScreenPreview() {
    MoviePunkTheme {
        Scaffold { padding ->
            val emptyMovies = flowOf(PagingData.empty<Movie>()).collectAsLazyPagingItems()
            HomeScreen(
                state = HomeState(),
                trendingMovies = emptyMovies,
                modifier = Modifier.padding(padding)
            )
        }
    }
}
