package com.example.uikit.cards

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.SmartDisplay
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material.icons.outlined.Lock
import androidx.compose.material.icons.rounded.Person
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.domain.models.Content
import com.example.domain.models.Content.Video.Companion.getVideo
import com.example.domain.models.Registration
import com.example.domain.models.VideoType
import com.example.uikit.R
import com.example.uikit.exoPlayer.ExoPlayerManager
import com.example.uikit.exoPlayer.VideoPlayer
import com.example.uikit.loginUiKit.LoggingBottoms
import com.example.uikit.loginUiKit.LoggingTextField
import com.example.uikit.theme.ColorMainGreen
import com.example.uikit.theme.MontserratRegular


@Composable
fun RegistrationCard(
    modifier: Modifier = Modifier,
    onLoginRequest: (Registration) -> Unit,
    onGuestRequest: () -> Unit,
) {
    var login by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var passwordHidden by rememberSaveable { mutableStateOf(true) }
    val visualTransformation =
        if (passwordHidden) PasswordVisualTransformation() else VisualTransformation.None

    val interactionSource = remember { MutableInteractionSource() }


    Box(
        modifier = modifier
            .clip(shape = RoundedCornerShape(20.dp))

    ) {
        Image(
            painter = painterResource(id = R.drawable.auth_card_back),
            contentDescription = null,
            modifier = Modifier
                .width(400.dp)
                .height(300.dp),
            contentScale = ContentScale.Crop
        )
        Card(
            modifier = Modifier
                .width(400.dp)
                .height(300.dp),

            colors = CardDefaults.cardColors(containerColor = Color.White.copy(0.1f)),
            shape = RoundedCornerShape(20.dp),
            border = BorderStroke(1.dp, Color.White.copy(alpha = 0.50f, green = 0.90f))


        )
        {

            Spacer(Modifier.height(50.dp))
            LoggingTextField(
                Modifier
                    .padding(start = 20.dp, end = 20.dp)
                    .align(Alignment.CenterHorizontally),
                value = login,
                onValueChange = { login = it },
                label = "Login",
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Rounded.Person,
                        contentDescription = "username",
                        tint = ColorMainGreen
                    )
                }
            )
            Spacer(Modifier.height(10.dp))
            LoggingTextField(
                Modifier
                    .padding(start = 20.dp, end = 20.dp)
                    .align(Alignment.CenterHorizontally),
                value = password,
                onValueChange = { password = it },
                label = "Password",
                visualTransformation = visualTransformation,
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Outlined.Lock,
                        contentDescription = "password",
                        tint = ColorMainGreen
                    )
                },
                trailingIcon = {
                    val image =
                        if (passwordHidden) Icons.Filled.Visibility else Icons.Filled.VisibilityOff
                    IconButton(onClick = { passwordHidden = !passwordHidden }) {
                        Icon(imageVector = image, contentDescription = "Пароль")
                    }
                }
            )
            Spacer(Modifier.height(30.dp))
            LoggingBottoms(
                modifier = Modifier.align(Alignment.CenterHorizontally),
                collBack = { onLoginRequest(Registration(login, password)) })

            Spacer(Modifier.height(10.dp))

            Text(
                modifier = Modifier
                    .align(alignment = Alignment.CenterHorizontally)
                    .clickable(
                        onClick = { onGuestRequest() },
                        indication = null,
                        interactionSource = interactionSource
                    ),
                text = "Login as a guest",
                style = TextStyle(
                    color = Color.White, // Text color
                    fontSize = 14.sp, // Text size
                    fontWeight = FontWeight.Normal, // Font weight
                    textDecoration = TextDecoration.Underline,
                    fontFamily = MontserratRegular// Add underline,

                )
            )
            Spacer(Modifier.height(5.dp))
        }
    }
}

@Composable
fun ContentCard(
    modifier: Modifier = Modifier,
    content: Content,
    exoPlayerManager: ExoPlayerManager,
    onClickContent: (Content) -> Unit,
    isPlayVideo: (video: String) -> Boolean,
    isShowVideo: (video: String) -> Boolean,
    onPlayVideo: (url: String) -> Unit,
    onPauseVideo: () -> Unit,
) {
    when (val content = content) {
        is Content.Photo -> {
            CardPhoto(
                modifier.fillMaxSize(),
                content = content,
                onClickContent = onClickContent
            )
        }

        is Content.Video -> {
            CardVideo(
                modifier.fillMaxSize(),
                content = content,
                exoPlayerManager = exoPlayerManager,
                onClickContent = onClickContent,
                isShowVideo = isShowVideo,
                isPlayVideo = isPlayVideo,
                onPlayVideo = onPlayVideo,
                onPauseVideo = onPauseVideo
            )
        }
    }


}

@Composable
private fun CardPhoto(
    modifier: Modifier = Modifier,
    content: Content.Photo,
    onClickContent: (Content) -> Unit,
) {
    Box(
        modifier = modifier
            .clip(CardDefaults.shape)
            .background(Color(content.avgColor))
    ) {
        AsyncImage(
            model = content.src.original,
            contentDescription = null,
            modifier = Modifier

                .clickable(onClick = { onClickContent(content) }),
            contentScale = ContentScale.Crop
        )
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomStart)
                .background(Color.Transparent.copy(0.2f))
        ) {
            Text(
                modifier = Modifier
                    .weight(1f)
                    .padding(start = 10.dp),
                text = content.photographer,
                fontSize = 12.sp,
                color = Color.White,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}

@Composable
private fun CardVideo(
    modifier: Modifier = Modifier,
    content: Content.Video,
    exoPlayerManager: ExoPlayerManager,
    onPlayVideo: (String) -> Unit,
    onPauseVideo: () -> Unit,
    isPlayVideo: (video: String) -> Boolean,
    isShowVideo: (video: String) -> Boolean,
    onClickContent: (Content) -> Unit,
) {
    val video = content.getVideo(VideoType.HD)
    val playVideo = isPlayVideo(video.link)

    Box(modifier = modifier.clip(CardDefaults.shape)) {
        VideoPlayer(
            exoPlayerManager = exoPlayerManager,
            placeholderUrl = content.image,
            isPlayVideo = { playVideo },
            isShowVideo = { isShowVideo(video.link) },
            modifier = Modifier.fillMaxSize(),
        )
        Icon(
            modifier = Modifier
                .align(Alignment.TopStart)
                .padding(10.dp)
                .clickable {
                    if (playVideo) {
                        onPauseVideo()
                    } else {
                        onPlayVideo(video.link)
                    }
                },
            imageVector = Icons.Default.SmartDisplay,
            contentDescription = "Play Video",
            tint = if (isPlayVideo(video.link)) Color.Green else Color.White
        )
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomStart)
                .background(Color.Transparent.copy(0.2f))
        ) {
            Text(
                modifier = Modifier
                    .weight(1f)
                    .padding(start = 10.dp),
                text = content.user.name,
                fontSize = 12.sp,
                color = Color.White,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }
    }

}

@Preview
@Composable
fun Preview() {
    RegistrationCard(
        onLoginRequest = {},
        onGuestRequest = {}
    )
}
