package com.example.contentdetailsscreen

import kotlinx.coroutines.flow.StateFlow

interface DetailsComponent {
    val model: StateFlow<ContentDetailsStore.State>
    fun onClickLike()
    fun onBackClick()
    fun onSettingClick()

}
