package com.example.home_screen.content.models

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.Stable
import com.example.domain.models.Content
import com.example.domain.models.StateLoading
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.ImmutableSet
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.persistentSetOf

@Immutable
data class StateHomeScreen (
    val content: Set<Content> = emptySet(),
    val page: Int = 1,
    val nextPage: Int = -1,
    val stateLoading: StateLoading = StateLoading.Loading,
){
    companion object{
        const val NULL_PAGE = -1
    }
}
