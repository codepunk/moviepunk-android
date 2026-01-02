package com.codepunk.moviepunk.ui.compose.component

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import coil3.compose.AsyncImage
import com.codepunk.moviepunk.domain.extension.getPosterUrl
import com.codepunk.moviepunk.domain.model.Configuration
import com.codepunk.moviepunk.domain.model.Movie
import com.codepunk.moviepunk.ui.compose.screen.preview.ComponentPreviews
import com.codepunk.moviepunk.ui.theme.MoviePunkTheme
import com.codepunk.moviepunk.ui.theme.movieCardHeight
import com.codepunk.moviepunk.ui.theme.movieCardWidth
import kotlinx.datetime.toJavaLocalDate
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle

@Composable
fun MovieCardView(
    configuration: Configuration,
    movie: Movie,
    modifier: Modifier = Modifier,
    cardWidth: Dp = movieCardWidth,
    cardHeight: Dp = movieCardHeight
) {
    val formatter = DateTimeFormatter.ofLocalizedDate(FormatStyle.MEDIUM)
    Column(
        modifier = modifier.width(cardWidth)
    ) {
        Box(
            modifier = Modifier.fillMaxWidth()
        ) {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(cardHeight)
            ) {
                val movieCardWidthPx = with(LocalDensity.current) {
                    cardWidth.roundToPx()
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
            }

            // TODO Rating circle
        }

        Text(
            text = movie.title,
            style = MaterialTheme.typography.titleMedium
        )
        movie.releaseDate?.also { releaseDate ->
            Text(
                text = formatter.format(releaseDate.toJavaLocalDate())
            )
        }
    }
}

// TODO
@ComponentPreviews
@Composable
fun MovieCardPreviews() {
    MoviePunkTheme {
        MovieCardView(
            configuration = Configuration(),
            movie = Movie(

            ),

        )
    }
}