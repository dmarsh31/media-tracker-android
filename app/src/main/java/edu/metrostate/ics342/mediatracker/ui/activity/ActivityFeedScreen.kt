package edu.metrostate.ics342.mediatracker.ui.activity

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import edu.metrostate.ics342.mediatracker.R
import edu.metrostate.ics342.mediatracker.data.model.ActivityEvent
import edu.metrostate.ics342.mediatracker.data.model.Media
import edu.metrostate.ics342.mediatracker.data.model.creatorCredit
import edu.metrostate.ics342.mediatracker.data.model.descriptionText
import edu.metrostate.ics342.mediatracker.theme.BookContainer
import edu.metrostate.ics342.mediatracker.theme.MovieContainer
import edu.metrostate.ics342.mediatracker.theme.OnBookContainer
import edu.metrostate.ics342.mediatracker.theme.OnMovieContainer
import edu.metrostate.ics342.mediatracker.theme.OnTvContainer
import edu.metrostate.ics342.mediatracker.theme.TvContainer

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ActivityFeedScreen(
    onMediaClick: (Int) -> Unit,
    onUserClick: (String) -> Unit,
    viewModel: ActivityFeedViewModel = viewModel()
) {
    val feedItems by viewModel.feedItems.collectAsState()

    Column(modifier = Modifier.fillMaxSize()) {
        TopAppBar(title = { Text(stringResource(edu.metrostate.ics342.mediatracker.R.string.feed_title)) },
            colors = TopAppBarDefaults.topAppBarColors(
                titleContentColor = MaterialTheme.colorScheme.primary
            ))

        HorizontalDivider()

        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(vertical = 8.dp)
        ) {
            feedItems.forEach { event ->
                ActivityCard(
                    event        = event,
                    onMediaClick = { event.media?.id?.let(onMediaClick) },
                    onUserClick  = { event.user?.id?.let(onUserClick) }
                )
            }
        }
    }
}

@Composable
private fun ActivityCard(
    event: ActivityEvent,
    onMediaClick: () -> Unit,
    onUserClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 6.dp)
            .clickable { onMediaClick() },
        shape     = RoundedCornerShape(12.dp),
        colors    = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
    ) {
        Row(
            modifier = Modifier.padding(12.dp),
            verticalAlignment = Alignment.Top
        ) {
            Box(
                modifier = Modifier
                    .size(40.dp)
                    .clip(CircleShape)
                    .clickable { onUserClick() },
                contentAlignment = Alignment.Center
            ) {
                if (event.user?.avatarUrl != null) {
                    AsyncImage(
                        model              = event.user.avatarUrl,
                        contentDescription = event.user.displayName,
                        contentScale       = ContentScale.Crop,
                        modifier           = Modifier.fillMaxSize()
                    )
                } else {
                    Surface(
                        color    = MaterialTheme.colorScheme.primaryContainer,
                        modifier = Modifier.fillMaxSize()
                    ) {
                        Box(contentAlignment = Alignment.Center) {
                            Text(
                                event.user?.displayName?.firstOrNull()?.toString() ?: "?",
                                style = MaterialTheme.typography.titleMedium,
                                color = MaterialTheme.colorScheme.onPrimaryContainer
                            )
                        }
                    }
                }
            }

            Spacer(Modifier.width(8.dp))

            Column(modifier = Modifier.weight(1f)) {
                Text(event.descriptionText(LocalContext.current),
                    style      = MaterialTheme.typography.bodyMedium,
                    fontWeight = FontWeight.SemiBold)

                Spacer(Modifier.height(12.dp))

                event.media?.let { media ->
                    feedCard(event)
                }

                Spacer(Modifier.height(4.dp))
                Text(event.createdAt.take(10),
                    style = MaterialTheme.typography.labelSmall,
                    color = MaterialTheme.colorScheme.outline)
            }

            Spacer(Modifier.width(8.dp))

            Box(
                modifier = Modifier
                    .size(56.dp)
                    .clip(RoundedCornerShape(6.dp)),
                contentAlignment = Alignment.Center
            ) {
                if (event.media?.coverUrl != null) {
                    AsyncImage(
                        model = event.media.coverUrl,
                        contentDescription = event.media.title,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier.fillMaxSize()
                    )
                }
            }
        }
    }
}

@Composable
private fun feedCard(event: ActivityEvent){
    val mediaType = event.media?.mediaType

    val containerColor = when (mediaType) {
        "book" -> BookContainer
        "movie" -> MovieContainer
        else -> TvContainer
    }

    val iconTint = when (mediaType) {
        "book" -> OnBookContainer
        "movie" -> OnMovieContainer
        else -> OnTvContainer
    }
    Card(
        modifier = Modifier
            .fillMaxWidth(),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.surfaceVariant
            )
    ) {
        Row() {
            Box(
                modifier = Modifier
                    .scale(0.75f)
                    .width(68.dp)
                    .height(100.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .background(containerColor),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    painter = painterResource(
                        when (mediaType) {
                            "book" -> edu.metrostate.ics342.mediatracker.R.drawable.book_icon
                            "movie" -> edu.metrostate.ics342.mediatracker.R.drawable.movie_icon
                            else -> R.drawable.tv_icon
                        }
                    ),
                    contentDescription = null,
                    modifier = Modifier.size(32.dp),
                    tint = iconTint
                )
            }
            Spacer(Modifier.width(8.dp))
            Column() {
                Spacer(Modifier.height(4.dp))
                Text(event.media?.title  ?: stringResource(R.string.media_unknown_title),
                    style = MaterialTheme.typography.titleSmall)
                if (event.activityType == "review" && event.rating != null) {
                    Spacer(Modifier.height(4.dp))
                    Text(
                        "★".repeat(event.rating) + "☆".repeat(5 - event.rating),
                        color = MaterialTheme.colorScheme.secondary
                    )
                    if (!event.reviewText.isNullOrBlank()) {
                        Spacer(Modifier.height(2.dp))
                        Text(event.reviewText,
                            style   = MaterialTheme.typography.bodySmall,
                            color   = MaterialTheme.colorScheme.onSurfaceVariant,
                            maxLines = 3)
                    }
                }else{
                    Spacer(Modifier.height(12.dp))
                    Text(event.media?.creatorCredit(context = LocalContext.current) ?: stringResource(R.string.media_unknown_creator),
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        maxLines = 1,
                        )

                }
            }
        }
    }
}
