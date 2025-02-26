package com.example.home_screen.trend


import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import com.example.uikit.home.AspectoLazyColum
import com.example.uikit.models.ContentUI
import com.example.uikit.models.StateLoading


@Composable
fun TrendsContent(
    modifier: Modifier = Modifier,
    componentContext: TrendsComponent,
    onClickContent: (ContentUI) -> Unit,
    onSetting: () -> Unit,
) {
    val contents = componentContext.model.collectAsState()

    Surface(modifier.fillMaxSize()) {
        AspectoLazyColum(
            contents = { contents.value.content },
            isLoading = { contents.value.stateLoading is StateLoading.Loading },
            onClickContent = onClickContent,
            onSettingContent = onSetting,
            onLoadNextContent = { componentContext.loadNextContent() }
       )
    }
}



