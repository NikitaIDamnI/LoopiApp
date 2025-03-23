package com.example.home_screen.trend

import androidx.compose.runtime.Stable
import com.arkivanov.mvikotlin.core.store.Reducer
import com.arkivanov.mvikotlin.core.store.Store
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.arkivanov.mvikotlin.extensions.coroutines.CoroutineBootstrapper
import com.arkivanov.mvikotlin.extensions.coroutines.CoroutineExecutor
import com.example.common.utils.TIME_DELAY_LOADING
import com.example.common.utils.UNKNOWN_ERROR
import com.example.common.utils.logD
import com.example.common.utils.parseNextPage
import com.example.domain.useCases.TrendsUseCase
import com.example.uikit.models.ContentUI
import com.example.uikit.models.StateLoading
import com.example.uikit.models.toUI
import kotlinx.collections.immutable.PersistentList
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toPersistentList
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import okhttp3.internal.toImmutableList
import javax.inject.Inject


interface TrendsScreenStore :
    Store<TrendsScreenStore.Intent, TrendsScreenStore.State, TrendsScreenStore.Label> {

    sealed interface Intent {
        data class LoadNextContent(val page: State.Page) : Intent
        data class ClickContent(val сontent: ContentUI) : Intent

    }

    @Stable
    data class State(
        val content: PersistentList<ContentUI> = persistentListOf(),
        val page: Page = Page(),
        val stateLoading: StateLoading = StateLoading.Loading,
    ) {
        data class Page(
            val current: Int = 1,
            val next: Int = NULL_PAGE,
        )

        companion object {
            const val NULL_PAGE = -1
        }
    }


    sealed interface Label {
        data class ClickContent(val сontent: ContentUI) : Label
    }
}

class TrendsScreenStoreFactory @Inject constructor(
    private val storeFactory: StoreFactory,
    private val trendsUseCase: TrendsUseCase,
    ) {

    fun create(): TrendsScreenStore =
        object : TrendsScreenStore,
            Store<TrendsScreenStore.Intent, TrendsScreenStore.State, TrendsScreenStore.Label> by storeFactory.create(
                name = "TrendsScreenStore",
                initialState = TrendsScreenStore.State(),
                bootstrapper = BootstrapperImpl(),
                executorFactory = ::ExecutorImpl,
                reducer = ReducerImpl
            ) {}

    private sealed interface Action {
        data class LoadFirstContent(
            val content: Set<ContentUI>,
            val page: TrendsScreenStore.State.Page,
        ) : Action

        data class LoadingError(val error: String) : Action
    }

    private sealed interface Msg {
        data class LoadContent(
            val content: Set<ContentUI>,
            val page: TrendsScreenStore.State.Page,
        ) : Msg

        data class LoadingError(val message: String) : Msg
        data object Loading : Msg
    }

    private inner class BootstrapperImpl : CoroutineBootstrapper<Action>() {
        override fun invoke() {
            scope.launch {
                trendsUseCase()
                    .catch {
                        dispatch(Action.LoadingError(it.message ?: UNKNOWN_ERROR))
                    }
                    .collect { resultApi ->
                        dispatch(
                            Action.LoadFirstContent(
                                content = resultApi.content.toUI(),
                                page = TrendsScreenStore.State.Page(
                                    current = resultApi.page,
                                    next = resultApi.nextPage.parseNextPage()
                                )
                            )
                        )
                    }
            }
        }
    }

    private inner class ExecutorImpl :
        CoroutineExecutor<TrendsScreenStore.Intent, Action, TrendsScreenStore.State, Msg, TrendsScreenStore.Label>() {
        private val loadingMutex = Mutex()
        private var isThrottled = false


        override fun executeIntent(
            intent: TrendsScreenStore.Intent,
            getState: () -> TrendsScreenStore.State,
        ) {
            when (intent) {
                is TrendsScreenStore.Intent.LoadNextContent -> loadNextContent(intent)

                is TrendsScreenStore.Intent.ClickContent -> {
                    publish(TrendsScreenStore.Label.ClickContent(intent.сontent))
                }

            }
        }


        override fun executeAction(action: Action, getState: () -> TrendsScreenStore.State) {
            when (action) {
                is Action.LoadFirstContent -> {
                    dispatch(
                        Msg.LoadContent(
                            content = action.content,
                            page = action.page
                        )
                    )
                }

                is Action.LoadingError -> {
                    dispatch(Msg.LoadingError(message = action.error))
                }
            }
        }

        private fun loadNextContent(intent: TrendsScreenStore.Intent.LoadNextContent) {
            scope.launch {
                if (isThrottled) {
                    logD(this@ExecutorImpl, "Throttling active: Function call ignored")
                    return@launch
                }
                loadingMutex.withLock {
                    if (intent.page.next != TrendsScreenStore.State.Companion.NULL_PAGE) {
                        logD(this@ExecutorImpl, "No more pages to load or already loading")
                        return@withLock
                    }
                    isThrottled = true
                    dispatch(Msg.Loading)
                }
                val nextPage = intent.page.next
                logD(this@ExecutorImpl, "Start loading content page = $nextPage")
                trendsUseCase(page = nextPage)
                    .catch { exception ->
                        dispatch(Msg.LoadingError(exception.message ?: UNKNOWN_ERROR))
                        isThrottled = false
                    }
                    .collect { resultApi ->
                        val content = resultApi.content.toUI()
                        dispatch(
                            Msg.LoadContent(
                                content = content, TrendsScreenStore.State.Page(
                                    current = resultApi.page,
                                    next = resultApi.nextPage.parseNextPage()
                                )
                            )
                        )
                        delay(TIME_DELAY_LOADING)
                        logD(this@ExecutorImpl, "Finish Delay")
                        isThrottled = false
                    }
            }

        }
    }

    private object ReducerImpl : Reducer<TrendsScreenStore.State, Msg> {
        override fun TrendsScreenStore.State.reduce(message: Msg): TrendsScreenStore.State =
            when (message) {
                is Msg.LoadContent -> {
                    logD(this@ReducerImpl, "message.content = ${message.page}")
                    val shuffledContent = message.content.shuffled()
                    val updateContent = updateList(content, shuffledContent)
                    copy(
                        content = updateContent.toPersistentList(),
                        stateLoading = StateLoading.Success,
                        page = message.page
                    )
                }

                Msg.Loading -> {
                    copy(stateLoading = StateLoading.Loading,)
                }

                is Msg.LoadingError -> {
                    copy(stateLoading = StateLoading.Error(message.message),)
                }
            }

        private fun updateList(
            oldList: List<ContentUI>,
            newList: List<ContentUI>,
        ): List<ContentUI> {
            val mutableList: MutableList<ContentUI> = mutableListOf<ContentUI>()
            mutableList.addAll(oldList)
            mutableList.addAll(newList)
            return mutableList.toImmutableList()
        }
    }

}


