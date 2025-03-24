package com.example.contentdetailsscreen

import com.arkivanov.mvikotlin.core.store.Reducer
import com.arkivanov.mvikotlin.core.store.Store
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.arkivanov.mvikotlin.extensions.coroutines.CoroutineBootstrapper
import com.arkivanov.mvikotlin.extensions.coroutines.CoroutineExecutor
import com.example.contentdetailsscreen.ContentDetailsStore.Intent
import com.example.contentdetailsscreen.ContentDetailsStore.Label
import com.example.contentdetailsscreen.ContentDetailsStore.State
import com.example.uikit.models.ContentUI
import jakarta.inject.Inject

interface ContentDetailsStore : Store<Intent, State, Label> {

    sealed interface Intent {
        data class ClickShare(val content: ContentUI) : Intent
        data object ClickLike : Intent
        data object ClickSetting : Intent
        data object ClickBack : Intent
    }

    data class State(
        val content: ContentUI,
        val isFavorite: Boolean,

        )

    sealed interface Label {
        data object ClickBack : Label
        data object ClickSetting : Label
        data object ClickShare : Label

    }
}

 class ContentDetailsStoreFactory @Inject constructor(
     private val storeFactory: StoreFactory,
) {

    fun create(
        content: ContentUI,
    ): ContentDetailsStore =
        object : ContentDetailsStore, Store<Intent, State, Label> by storeFactory.create(
            name = "ContentDetailsStore",
            initialState = State(content = content, isFavorite = false),
            bootstrapper = BootstrapperImpl(),
            executorFactory = ::ExecutorImpl,
            reducer = ReducerImpl
        ) {}

    private sealed interface Action {
    }

    private sealed interface Msg {
        data class FavouriteStatusChanged(val isFavorite: Boolean) : Msg
    }

    private class BootstrapperImpl : CoroutineBootstrapper<Action>() {
        override fun invoke(){
        }
    }

    private class ExecutorImpl : CoroutineExecutor<Intent, Action, State, Msg, Label>() {
        override fun executeIntent(intent: Intent) {
            when (intent) {
                Intent.ClickBack -> {
                    publish(Label.ClickBack)
                }
                Intent.ClickLike -> {
                    dispatch(Msg.FavouriteStatusChanged (false))
                }
                Intent.ClickSetting -> {
                    publish(Label.ClickSetting)
                }
                is Intent.ClickShare -> {
                    publish(Label.ClickShare)
                }
            }
        }

        override fun executeAction(action: Action) {
        }
    }

    private object ReducerImpl : Reducer<State, Msg> {
        override fun State.reduce(message: Msg): State =
            when (message) {
                is Msg.FavouriteStatusChanged -> {
                    copy(isFavorite = message.isFavorite)
                }
            }
    }
}
