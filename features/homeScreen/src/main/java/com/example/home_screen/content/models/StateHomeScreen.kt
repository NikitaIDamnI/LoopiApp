package com.example.home_screen.content.models

import com.example.domain.models.Content
import com.example.domain.models.StateLoading

data class StateHomeScreen (
    val content: List<Content> = emptyList(),
    val stateLoading: StateLoading = StateLoading.Loading,
)
