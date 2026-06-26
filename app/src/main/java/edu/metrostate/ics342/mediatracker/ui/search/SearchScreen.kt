package edu.metrostate.ics342.mediatracker.ui.search

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import edu.metrostate.ics342.mediatracker.data.FakeMediaRepository
import edu.metrostate.ics342.mediatracker.data.model.Media
import edu.metrostate.ics342.mediatracker.ui.auth.LoginScreen

// ── STUB — Students build this in Week 5 ─────────────────────────────────────
//
// Week 5 task: Build the Search screen.
//   1. Add a search bar (SearchBar or OutlinedTextField) at the top.
//   2. Add FilterChips for All / Books / Movies / Shows in a horizontally scrollable Row.
//   3. Display results in a LazyColumn (you'll learn why Column won't work here).
//   4. Wire to GET /media?query=...&type=...
//   5. Handle loading, empty, and error states.


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchScreen(
    onMediaClick: (Int) -> Unit
) {
    var searchText by remember { mutableStateOf("") }

    val filteredMedia = FakeMediaRepository.mediaList.filter { media ->
        media.title.contains(searchText, ignoreCase = true) ||
                media.mediaType.contains(searchText, ignoreCase = true) ||
                media.genres.any { genre ->
                    genre.contains(searchText, ignoreCase = true)
                }
    }

    Column(modifier = Modifier.fillMaxSize()) {
        TopAppBar(title = { Text(stringResource(edu.metrostate.ics342.mediatracker.R.string.search_title)) })

        OutlinedTextField(
            value = searchText,
            onValueChange = { searchText = it },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 10.dp),
            placeholder = { Text(stringResource(edu.metrostate.ics342.mediatracker.R.string.searchbar_placeholder)) },
            singleLine = true,
            shape = RoundedCornerShape(30.dp),
        )

        Spacer(Modifier.height(12.dp))


        LazyColumn {
            items(filteredMedia) { media ->
                MediaSearchResultItem(media = media)
            }
        }

    }
}

@Composable
fun MediaSearchResultItem(media: Media) {

    val (icon, iconColor, backgroundColor) = when (media.mediaType) {
        "book" -> Triple(
            edu.metrostate.ics342.mediatracker.R.drawable.book_icon,
            Color(0xFF7C3AED),
            Color(0xFFE8DEF8)      // desaturated purple
        )
        "movie" -> Triple(
            edu.metrostate.ics342.mediatracker.R.drawable.movie_icon,
            Color(0xFFF44336),
            Color(0xFFF8D7D4)      // desaturated red
        )
        "show" -> Triple(
            edu.metrostate.ics342.mediatracker.R.drawable.tv_icon,
            Color(0xFFFF9800),
            Color(0xFFFCE6C4)      // desaturated orange
        )
        else -> Triple(
            edu.metrostate.ics342.mediatracker.R.drawable.ic_launcher_foreground,
            Color.Gray,
            Color(0xFFE0E0E0)
        )
    }

    val creator = media.author
        ?: media.director
        ?: media.creator
        ?: "Unknown"

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 6.dp),
        shape = RoundedCornerShape(16.dp)
    ) {

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(110.dp)
        ) {

            // Left colored rectangle
            Box(
                modifier = Modifier
                    .fillMaxHeight()
                    .width(90.dp)
                    .background(backgroundColor),
                contentAlignment = Alignment.Center
            ) {
                Image(
                    painter = painterResource(icon),
                    contentDescription = media.mediaType,
                    modifier = Modifier.size(42.dp),
                    colorFilter = ColorFilter.tint(iconColor)
                )
            }

            // Right side
            Column(
                modifier = Modifier
                    .padding(horizontal = 16.dp, vertical = 12.dp)
                    .fillMaxHeight(),
                verticalArrangement = Arrangement.SpaceBetween
            ) {

                Text(
                    text = media.title,
                    style = MaterialTheme.typography.titleMedium,
                    maxLines = 1
                )

                Text(
                    text = creator,
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color.Gray
                )

                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    /*
                    Icon(
                        imageVector = image edu.metrostate.ics342.mediatracker.R.drawable.logo,
                        contentDescription = null,
                        tint = iconColor,
                        modifier = Modifier.size(16.dp)
                    )
                    */
                    Spacer(Modifier.width(4.dp))

                    Text(
                        text = "%.1f".format(media.averageRating),
                        style = MaterialTheme.typography.bodySmall
                    )

                    Text(
                        text = " • ${media.mediaType.replaceFirstChar { it.uppercase() }} • ${media.publishedYear}",
                        style = MaterialTheme.typography.bodySmall,
                        color = Color.Gray
                    )
                }
            }
        }
    }
}

@Preview
@Composable
fun SearchScreenPreview() {
    SearchScreen(
        onMediaClick = {}
    )
}