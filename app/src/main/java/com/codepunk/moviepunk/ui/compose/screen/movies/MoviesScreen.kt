package com.codepunk.moviepunk.ui.compose.screen.movies

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.pulltorefresh.PullToRefreshDefaults.Indicator
import androidx.compose.material3.pulltorefresh.PullToRefreshState
import androidx.compose.material3.pulltorefresh.pullToRefresh
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalInspectionMode
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import androidx.paging.PagingData
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemKey
import coil3.compose.AsyncImage
import coil3.compose.SubcomposeAsyncImage
import com.codepunk.moviepunk.R
import com.codepunk.moviepunk.domain.extension.getPosterUrl
import com.codepunk.moviepunk.domain.model.Configuration
import com.codepunk.moviepunk.domain.model.CuratedContentItem
import com.codepunk.moviepunk.domain.model.CuratedContentType
import com.codepunk.moviepunk.domain.model.Movie
import com.codepunk.moviepunk.ui.compose.screen.preview.ScreenPreviews
import com.codepunk.moviepunk.ui.theme.MoviePunkTheme
import com.codepunk.moviepunk.ui.theme.dimens
import com.codepunk.moviepunk.ui.theme.movieCardHeight
import com.codepunk.moviepunk.ui.theme.movieCardWidth
import com.codepunk.moviepunk.ui.theme.tmdbWhite
import kotlinx.coroutines.flow.flowOf
import timber.log.Timber

@Composable
fun MoviesScreen(
    state: MoviesState,
    trendingMovies: LazyPagingItems<Movie>,
    modifier: Modifier = Modifier,
    @Suppress("unused")
    sendIntent: (MoviesIntent) -> Unit = {}
) {
    /*
    val configuration = LocalConfiguration.current
    val windowInfo = LocalWindowInfo.current
    val density = LocalDensity.current
     */

    LaunchedEffect(key1 = state) {
        Timber.i("state.configuration.images=${state.configuration.images}")
    }

    LaunchedEffect(key1 = trendingMovies.loadState) {
        Timber.i("trendingMovies.loadState = ${trendingMovies.loadState}")
        /* TODO
        if (trendingMovies.loadState.refresh is LoadState.Error) {

        }
         */
    }

    val pullToRefreshState: PullToRefreshState = rememberPullToRefreshState()

    Box(
        modifier = modifier.fillMaxSize()
            .pullToRefresh(
                state = pullToRefreshState,
                isRefreshing = state.isLoadingAny,
                onRefresh = {
                    sendIntent(MoviesIntent.RefreshIntent)
                }
            )
    ) {
        if (!LocalInspectionMode.current && trendingMovies.loadState.refresh is LoadState.Loading) {
            CircularProgressIndicator(
                modifier = Modifier.align(Alignment.Center)
            )
        } else {
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(12.dp),
            ) {
                item {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .aspectRatio(880f / 600f)
                    ) {
                        if (LocalInspectionMode.current) {
                            Image(
                                modifier = Modifier.fillMaxWidth(),
                                contentDescription = "Curated Content Image",
                                painter = painterResource(id = R.drawable.dummy_featured_item),
                                contentScale = ContentScale.Crop
                            )
                        } else {
                            SubcomposeAsyncImage(
                                modifier = Modifier.fillMaxWidth(),
                                model = state.featuredContentItem?.url,
                                contentDescription = "Curated Content Image",
                                success = { successState ->
                                    Image(
                                        painter = successState.painter,
                                        contentDescription = contentDescription,
                                        contentScale = ContentScale.Crop,
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .aspectRatio(880f / 600f)
                                    )
                                }
                            )
                        }

                        Column(
                            modifier = Modifier
                                .padding(MaterialTheme.dimens.paddingStandard)
                                .fillMaxSize(),
                            verticalArrangement = Arrangement.Center
                        ) {
                            Text(
                                text = stringResource(R.string.welcome),
                                style = MaterialTheme.typography.displayLarge,
                                fontWeight = FontWeight.Black,
                                color = tmdbWhite
                            )
                            Text(
                                text = stringResource(R.string.millions_of_movies),
                                style = MaterialTheme.typography.headlineLarge,
                                color = tmdbWhite
                            )
                        }
                    }
                }

                item {
                    TrendingSection(
                        configuration = state.configuration,
                        trendingMovies = trendingMovies
                    )
                }
            }
        }

        Indicator(
            modifier = Modifier.align(Alignment.TopCenter),
            isRefreshing = state.isLoadingAny,
            state = pullToRefreshState
        )
    }
}

@Composable
fun TrendingSection(
    configuration: Configuration,
    trendingMovies: LazyPagingItems<Movie>,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = Modifier.fillMaxWidth()
    ) {

        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(MaterialTheme.dimens.paddingLarge)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = MaterialTheme.dimens.paddingXLarge)
            ) {
                Text(
                    text = stringResource(R.string.trending),
                    style = MaterialTheme.typography.titleLarge,
                    modifier = modifier
                )
            }

            Box(
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier.matchParentSize()
                ) {
                    Spacer(modifier = Modifier.weight(1f))
                    Image(
                        modifier = Modifier
                            .padding(top = MaterialTheme.dimens.paddingXLarge)
                            .fillMaxWidth()
                            .weight(3f),
                        painter = painterResource(R.drawable.trending_background),
                        contentDescription = null,
                        contentScale = ContentScale.FillHeight,
                    )
                }

                LazyRow(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(MaterialTheme.dimens.paddingXLarge),
                    contentPadding = PaddingValues(horizontal = MaterialTheme.dimens.paddingXLarge)
                ) {
                    items(
                        count = trendingMovies.itemCount,
                        key = trendingMovies.itemKey { it.id }
                    ) { index ->
                        trendingMovies[index]?.also { movie ->
                            MoviePreviewView(
                                configuration = configuration,
                                movie = movie
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun MoviePreviewView(
    configuration: Configuration,
    movie: Movie,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .size(movieCardWidth, movieCardHeight)
    ) {
        val movieCardWidthPx = with(LocalDensity.current) {
            movieCardWidth.roundToPx()
        }
        val url = configuration.getPosterUrl(
            path = movie.posterPath,
            widthPx = movieCardWidthPx
        )
        AsyncImage(
            model = url,
            contentDescription = movie.title,
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
        )
        Text(
            text = movie.title
        )
    }
}

@ScreenPreviews
@Composable
fun MoviesScreenPreview() {
    MoviePunkTheme {
        Scaffold { padding ->
            val state = MoviesState(
                isConnected = true,
                isLoadingAny = false,
                curatedContentLoading = false,
                featuredContentItem = CuratedContentItem(
                    id = 1,
                    label = "Movie",
                    type = CuratedContentType.FEATURED,
                    href = "",
                    url = "",
                ),
                curatedContentError = null
            )
            val pagingData = PagingData
                .from(emptyList<Movie>())
            val emptyMovies = flowOf(pagingData).collectAsLazyPagingItems()
            MoviesScreen(
                state = state,
                trendingMovies = emptyMovies,
                modifier = Modifier.padding(padding)
            )
        }
    }
}
