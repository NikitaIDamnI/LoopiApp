package com.example.home_screen.content.models

import androidx.compose.runtime.Stable
import com.example.uikit.models.StateLoading
import com.example.uikit.models.ContentUI
import kotlinx.collections.immutable.PersistentList
import kotlinx.collections.immutable.persistentListOf

@Stable
data class StateHomeScreen (
    val content: PersistentList<ContentUI> = persistentListOf(),
    val page: Int = 1,
    val nextPage: Int = -1,
    val stateLoading: StateLoading = StateLoading.Loading,
){
    companion object{
        const val NULL_PAGE = -1
    }
}
