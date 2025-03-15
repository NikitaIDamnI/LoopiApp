package com.example.home_screen.trend

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.mvikotlin.core.instancekeeper.getStore
import com.arkivanov.mvikotlin.extensions.coroutines.labels
import com.arkivanov.mvikotlin.extensions.coroutines.stateFlow
import com.example.common.utils.componentScope
import com.example.common.utils.logD
import com.example.uikit.models.ContentUI
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class DefaultTrendsComponent @AssistedInject constructor(
    val trendsScreenStoreFactory: TrendsScreenStoreFactory,
    @Assisted("onContentClick") private val onContentClick: (ContentUI) -> Unit,
    @Assisted("componentContext") componentContext: ComponentContext,
) : TrendsComponent, ComponentContext by componentContext {

    private val store = instanceKeeper.getStore { trendsScreenStoreFactory.create() }
    private val scope = componentScope()

    @OptIn(ExperimentalCoroutinesApi::class)
    override val model: StateFlow<TrendsScreenStore.State>
        get() = store.stateFlow

    init {
        scope.launch {
            store.labels.collect {
                when (it) {
                    is TrendsScreenStore.Label.ClickContent -> onContentClick(it.сontent)
                }
            }
        }
        scope.launch{
            model.collect{
                logD(this@DefaultTrendsComponent, "${it.stateLoading}")
            }
        }
    }


    override fun onClickContent(сontentUI: ContentUI) {
        store.accept(TrendsScreenStore.Intent.ClickContent(сontentUI))
    }

    override fun loadNextContent() {
        logD(this@DefaultTrendsComponent, "Start loadNextContent")
        store.accept(TrendsScreenStore.Intent.LoadNextContent(model.value.page))
    }

    @AssistedFactory
    interface Factory {
        fun create(
            @Assisted("onContentClick") onContentClick: (ContentUI) -> Unit,
            @Assisted("componentContext") componentContext: ComponentContext,
        ): DefaultTrendsComponent
    }
}
