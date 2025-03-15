package com.example.contentdetailsscreen

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.uikit.models.ContentUI
import com.example.uikit.models.ContentUI.PhotoUI

@Composable
fun DetailsContent(component: DetailsComponent) {
    val state = component.model.collectAsState()
    val user = when (val content = state.value.content) {
        is ContentUI.PhotoUI -> Pair<String, String>(content.photographer, content.photographerUrl)
        is ContentUI.VideoUI -> Pair<String, String>(content.user.name, content.user.url)
    }
    val alt = when (val content = state.value.content) {
        is ContentUI.PhotoUI -> listOf<String>()
        is ContentUI.VideoUI -> content.tags
    }

    Scaffold {
        LazyColumn(
            modifier = Modifier.background(MaterialTheme.colorScheme.background),
            contentPadding = PaddingValues(start = 3.dp, end = 3.dp)
        ) {

            item {
                CardContent(
                    modifier = Modifier
                        .fillMaxWidth()
                        .heightIn(min = 150.dp, max = 500.dp)
                        .padding(top = it.calculateTopPadding()),
                    content = state.value.content,
                )
            }
            item {
                UserProfileCard(user.first)
            }
            item {
                ContentActions(
                    isFavorite = state.value.isFavorite,
                    onClickLike = { component.onClickLike() },
                    onClickShare = { }
                )
            }
            item {
                Spacer(modifier = Modifier.padding(top = 20.dp))
                MoreSimilarContent(alt = alt)
            }
        }
        ButtomBack(modifier = Modifier.padding(it)) { component.onBackClick() }
    }


}

@Preview
@Composable
private fun DetailsContentPreview() {
    Scaffold() {

        LazyColumn(
            modifier = Modifier.background(MaterialTheme.colorScheme.background),
        ) {

            item {
                CardContent(
                    modifier = Modifier
                        .fillMaxWidth()
                        .heightIn(min = 150.dp, max = 500.dp)
                        .padding(top = it.calculateTopPadding()),
                    content = PhotoUI()
                )
                ContentActions(isFavorite = true, onClickLike = { }, onClickShare = { })
                Spacer(Modifier.padding(top = 20.dp))
                MoreSimilarContent(alt = listOf<String>())
            }
        }
        ButtomBack(modifier = Modifier.padding(it)) { }
    }
}


@Composable
fun UserProfileCard(
    userName: String,
) {

    val default = painterResource(com.example.uikit.R.drawable.ic_launcher_playstore)
    val pexelsLogo = painterResource(com.example.uikit.R.drawable.pexels_logo)

    Row(
        Modifier
            .padding(5.dp)
    ) {
        Image(
            modifier = Modifier
                .clip(CircleShape)
                .size(50.dp), painter = default, contentDescription = "Фото профиля",
            contentScale = ContentScale.Crop
        )

        Spacer(Modifier.padding(5.dp))

        Text(
            modifier = Modifier.align(Alignment.CenterVertically),
            text = userName,
            fontSize = 15.sp
        )
        Spacer(Modifier.weight(1f))
        Box(
            modifier = Modifier
                .size(35.dp)
                .clip(RoundedCornerShape(5.dp))
                .align(Alignment.CenterVertically)
                .background(Color.Black),
            contentAlignment = Alignment.Center
        ) {
            Image(
                painter = pexelsLogo, "", modifier = Modifier.padding(1.dp)
            )
        }
    }
}

@Composable
private fun MoreSimilarContent(modifier: Modifier = Modifier, alt: List<String>) {
    Column(modifier = modifier) {
        Text(
            text = "More similar content",
            color = MaterialTheme.colorScheme.onBackground,
            fontSize = 23.sp,
        )
        LazyRow {
            items(alt.size) {
                Card {
                    Text(text = alt[it])
                }
            }
        }

    }

}


@Composable
private fun ButtomBack(modifier: Modifier = Modifier, onClickBack: () -> Unit) {
    IconButton(modifier = modifier, onClick = { onClickBack() }) {
        Box(
            modifier = Modifier
                .clip(CircleShape)
                .background(MaterialTheme.colorScheme.background.copy(.4f))
        ) {
            Icon(
                imageVector = Icons.AutoMirrored.Default.ArrowBack,
                "",
                modifier = Modifier.padding(5.dp),
                tint = MaterialTheme.colorScheme.onBackground
            )
        }
    }
}


@Composable
private fun ContentActions(
    modifier: Modifier = Modifier,
    isFavorite: Boolean,
    onClickLike: () -> Unit,
    onClickShare: () -> Unit,
    onClickLoad: () -> Unit = {},
) {
    // Анимация масштаба при лайке
    val scale by animateFloatAsState(
        targetValue = if (isFavorite) 1.3f else 1f, // Увеличиваем, затем возвращаем в норму
        animationSpec = tween(durationMillis = 200, easing = FastOutSlowInEasing),
        label = "Like Scale"
    )

    // Анимация смены иконки
    val iconFavorite = if (isFavorite) {
        Icons.Filled.Favorite
    } else {
        Icons.Outlined.FavoriteBorder
    }


    Row(
        modifier = modifier.padding(start = 5.dp, end = 5.dp, top = 10.dp),
        horizontalArrangement = Arrangement.SpaceEvenly,

        ) {
        // Кнопка лайка с анимацией
        Row {
            Icon(
                modifier = Modifier
                    .graphicsLayer(scaleX = scale, scaleY = scale)
                    .pointerInput(Unit) {
                        detectTapGestures {
                            onClickLike()
                        }
                    },
                imageVector = iconFavorite,
                contentDescription = "Лайк",
                tint = if (isFavorite) Color.Red else Color.Gray,
            )

            Spacer(modifier = Modifier.width(15.dp))

            // Кнопка "Поделиться"
            Icon(
                modifier = Modifier
                    .pointerInput(Unit) {
                        detectTapGestures {
                            onClickShare()
                        }
                    }, imageVector = Icons.Default.Share, contentDescription = "Поделиться"
            )
        }
        Spacer(modifier = Modifier.weight(1f))

        Button(
            onClick = { onClickLoad() },
            shape = RoundedCornerShape(5.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color.Black)
        ) {
            Text("Loading")
        }

    }
}


@Composable
private fun CardContent(
    modifier: Modifier = Modifier,
    content: ContentUI,
) {
    Card(
        modifier = modifier
            .background(Color.Transparent)
    ) {

        when (content) {
            is ContentUI.PhotoUI -> {
                AsyncImage(
                    modifier = Modifier
                        .fillMaxWidth(),
                    model = content.src.original,
                    contentScale = ContentScale.FillWidth,
                    contentDescription = null
                )
            }

            is ContentUI.VideoUI -> {
                AsyncImage(
                    modifier = Modifier
                        .fillMaxWidth(),
                    model = content.image,
                    contentScale = ContentScale.FillWidth,
                    contentDescription = null
                )
            }
        }

    }

}


