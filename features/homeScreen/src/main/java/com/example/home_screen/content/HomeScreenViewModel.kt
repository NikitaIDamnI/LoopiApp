package com.example.home_screen.content

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.common.utils.logD
import com.example.domain.models.StateLoading
import com.example.domain.useCases.TrendsUseCase
import com.example.home_screen.content.models.StateHomeScreen
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class HomeScreenViewModel @Inject constructor(
    trendsUseCase: TrendsUseCase,
) : ViewModel() {

    val state = trendsUseCase.getTrendsPhotos()
        .map {
            StateHomeScreen(
                content = it.content,
                stateLoading = StateLoading.Success,
            )
        }
        .onEach {
            logD(this, it.toString())
        }
        .catch {
           emit( StateHomeScreen(
                content = emptyList(),
                stateLoading = StateLoading.Error(it.message ?: ""),
            ))
        }
        .stateIn(viewModelScope, SharingStarted.Eagerly, StateHomeScreen())

    init {
        viewModelScope.launch (Dispatchers.IO){
            state.collect {
                logD(this@HomeScreenViewModel, it.toString())


            }
        }
    }

}

