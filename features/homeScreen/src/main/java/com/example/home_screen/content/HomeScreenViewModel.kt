package com.example.home_screen.content

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.common.utils.logD
import com.example.common.utils.mergeWith
import com.example.common.utils.parseNextPage
import com.example.domain.models.StateLoading
import com.example.domain.useCases.TrendsUseCase
import com.example.home_screen.content.models.StateHomeScreen
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import javax.inject.Inject
import kotlin.collections.toSet

@HiltViewModel
class HomeScreenViewModel @Inject constructor(
    private val trendsUseCase: TrendsUseCase,
) : ViewModel() {

    private val trendsContent = trendsUseCase()
    private val eventLoadNextContent: MutableSharedFlow<StateHomeScreen> = MutableSharedFlow()

    private val mutex = Mutex()
    private var isThrottled = false

    val state = trendsContent
        .map {
            val shuffledContent = it.content.shuffled().toSet()
            StateHomeScreen(
                content = shuffledContent,
                stateLoading = StateLoading.Success,
                page = it.page,
                nextPage = it.nextPage.parseNextPage()
            )
        }
        .mergeWith(eventLoadNextContent)
        .catch {
            emit(
                StateHomeScreen(
                    stateLoading = StateLoading.Error(it.message ?: "")
                )
            )
        }
        .stateIn(viewModelScope, SharingStarted.Eagerly, StateHomeScreen())

    init {
        viewModelScope.launch {
            state.collect {
                logD(this@HomeScreenViewModel, "State updated: ${it.stateLoading}")
            }
        }
    }

    fun loadNextContent() {
        viewModelScope.launch {
            if (isThrottled) {
                logD(this@HomeScreenViewModel, "Throttling active: Function call ignored")
                return@launch
            }

            mutex.withLock {
                val currentState = state.value
                val nextPage = currentState.nextPage

                if (nextPage == StateHomeScreen.NULL_PAGE || currentState.stateLoading is StateLoading.Loading) {
                    logD(this@HomeScreenViewModel, "No more pages to load or already loading")
                    return@withLock
                }

                isThrottled = true
                eventLoadNextContent.emit(currentState.copy(stateLoading = StateLoading.Loading))
            }

            logD(this@HomeScreenViewModel, "Starting content loading")
            trendsUseCase(page = state.value.nextPage)
                .map {
                    val currentState = state.value
                    val shuffledContent = it.content.shuffled().toSet()
                    val newContent = currentState.content + shuffledContent
                    currentState.copy(
                        content = newContent,
                        stateLoading = StateLoading.Success,
                        page = it.page,
                        nextPage = it.nextPage.parseNextPage()
                    )
                }
                .catch { exception ->
                    val currentState = state.value
                    eventLoadNextContent.emit(
                        currentState.copy(
                            stateLoading = StateLoading.Error(exception.message ?: "Unknown error")
                        )
                    )
                    isThrottled = false

                }
                .collect {
                    eventLoadNextContent.emit(it)
                    logD(this@HomeScreenViewModel, "Content loaded successfully")
                    logD(this@HomeScreenViewModel, "Start delay")
                    delay(TIME_DELAY)
                    isThrottled = false
                    logD(this@HomeScreenViewModel, "Finish")

                }

        }

    }


    companion object {
        private const val TIME_DELAY = 2000L
    }
}

