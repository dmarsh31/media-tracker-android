package edu.metrostate.ics342.mediatracker.ui.search

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import edu.metrostate.ics342.mediatracker.R
import edu.metrostate.ics342.mediatracker.data.model.Media
import edu.metrostate.ics342.mediatracker.data.model.creatorCredit
import edu.metrostate.ics342.mediatracker.theme.BookContainer
import edu.metrostate.ics342.mediatracker.theme.MovieContainer
import edu.metrostate.ics342.mediatracker.theme.OnBookContainer
import edu.metrostate.ics342.mediatracker.theme.OnMovieContainer
import edu.metrostate.ics342.mediatracker.theme.OnTvContainer
import edu.metrostate.ics342.mediatracker.theme.TvContainer

@Composable
fun MediaTypeFilterChips(
    selectedType: String,
    onTypeSelect: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    val types = listOf(
        "" to R.string.filter_all,
        "book" to R.string.filter_books,
        "movie" to R.string.filter_movies,
        "show" to R.string.filter_shows,
    )

    Row(
        modifier = modifier.horizontalScroll(rememberScrollState()),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        types.forEach { (type, labelRes) ->
            FilterChip(
                selected = selectedType == type,
                onClick = { onTypeSelect(type) },
                label = { Text(stringResource(labelRes)) }
            )
        }
    }
}

@Composable
fun MediaResultCard(
    media: Media,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 4.dp)
            .clickable { onClick() },
        shape = RoundedCornerShape(8.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
    ) {
        Row(
            modifier = Modifier.padding(18.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            val containerColor = when (media.mediaType) {
                "book"  -> BookContainer
                "movie" -> MovieContainer
                else    -> TvContainer
            }
            val iconTint = when (media.mediaType) {
                "book"  -> OnBookContainer
                "movie" -> OnMovieContainer
                else    -> OnTvContainer
            }

            Box(
                modifier = Modifier
                    .width(68.dp)
                    .height(100.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .background(containerColor),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    painter = painterResource(when (media.mediaType) {
                        "book"  -> R.drawable.book_icon
                        "movie" -> R.drawable.movie_icon
                        else    -> R.drawable.tv_icon
                    }),
                    contentDescription = null,
                    modifier = Modifier.size(32.dp),
                    tint = iconTint
                )
            }

            Spacer(Modifier.width(12.dp))

            Column(modifier = Modifier
                .weight(1f)
                .align(Alignment.Top)) {
                Text(
                    text = media.title,
                    style = MaterialTheme.typography.titleSmall,
                    fontWeight = FontWeight.SemiBold,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )
                Spacer(Modifier.height(8.dp))
                Text(
                    text = media.creatorCredit(LocalContext.current),
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Spacer(Modifier.height(8.dp))
                Text(
                    text = buildAnnotatedString {
                        withStyle(
                            style = SpanStyle(
                                color = MaterialTheme.colorScheme.tertiary,
                                //I would have used its own style, but it just looked like the star part was bolded
                                fontWeight = FontWeight.Bold
                                )
                            ) {
                            append("★ ${"%.1f".format(media.averageRating)}")
                        }

                        append(" · ${media.mediaType.replaceFirstChar { it.uppercase() }}")

                        media.publishedYear?.let {
                            append(" · $it")
                        }
                    },
                    style = MaterialTheme.typography.labelMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                )
            }
        }
    }
}