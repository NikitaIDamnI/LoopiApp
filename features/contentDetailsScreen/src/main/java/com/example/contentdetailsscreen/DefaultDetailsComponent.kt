package com.example.contentdetailsscreen

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.mvikotlin.core.instancekeeper.getStore
import com.arkivanov.mvikotlin.extensions.coroutines.labels
import com.arkivanov.mvikotlin.extensions.coroutines.stateFlow
import com.example.common.utils.componentScope
import com.example.uikit.models.ContentUI
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class DefaultDetailsComponent @AssistedInject  constructor(
    private val contentDetailsStore: ContentDetailsStoreFactory,
    @Assisted("content") private val content: ContentUI,
    @Assisted("onBackClicked") private val onBackClicked: () -> Unit,
    @Assisted("onClickSetting") private val onClickSetting: () -> Unit,
    @Assisted("onClickShare") private val onClickShare: () -> Unit,

    @Assisted("componentContext") componentContext: ComponentContext,
) : DetailsComponent, ComponentContext by componentContext {

    private val store = instanceKeeper.getStore {contentDetailsStore.create(content)}
    private val scope = componentScope()


    @OptIn(ExperimentalCoroutinesApi::class)
    override val model: StateFlow<ContentDetailsStore.State>
        get() = store.stateFlow


    init {
        scope.launch {
            store.labels.collect {
                when (it) {
                    ContentDetailsStore.Label.ClickBack -> onBackClicked()
                    ContentDetailsStore.Label.ClickSetting -> onClickSetting()
                    ContentDetailsStore.Label.ClickShare -> onClickShare()
                }
            }
        }
    }
    override fun onClickLike() {
        store.accept(ContentDetailsStore.Intent.ClickLike)
    }

    override fun onBackClick() {
        store.accept(ContentDetailsStore.Intent.ClickBack)
    }

    override fun onSettingClick() {
        store.accept(ContentDetailsStore.Intent.ClickSetting)
    }


    @AssistedFactory
    interface Factory {
        fun create(
            @Assisted("content") content: ContentUI,
            @Assisted("onBackClicked") onBackClicked: () -> Unit,
            @Assisted("onClickSetting") onClickSetting: () -> Unit,
            @Assisted("onClickShare") onClickShare: () -> Unit,
            @Assisted("componentContext") componentContext: ComponentContext,
        ) : DefaultDetailsComponent
    }
}
