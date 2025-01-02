package com.example.authscreen.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.domain.models.Registration
import com.example.uikit.R
import com.example.uikit.cards.RegistrationCard
import com.example.uikit.theme.MainBackgroundColor


@Composable
fun AuthScreenContent(
    modifier: Modifier = Modifier, onLoginRequest: (Registration) -> Unit,
    onGuestRequest: () -> Unit,
) {
    AuthScreen(
        modifier = modifier,
        onLoginRequest = onLoginRequest,
        onGuestRequest = onGuestRequest
    )
}

@Composable
internal fun AuthScreen(
    modifier: Modifier = Modifier,
    onLoginRequest: (Registration) -> Unit,
    onGuestRequest: () -> Unit,
) {

    Box(
        modifier = modifier
            .background(MainBackgroundColor)
    ) {

        Image(
            painter = painterResource(id = R.drawable.auth_background),
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )
        Spacer(Modifier.height(30.dp))
        RegistrationCard(modifier = Modifier
            .align(Alignment.Center)
            .padding(horizontal = 20.dp),
            onLoginRequest = onLoginRequest,
            onGuestRequest = onGuestRequest
        )
    }

}
