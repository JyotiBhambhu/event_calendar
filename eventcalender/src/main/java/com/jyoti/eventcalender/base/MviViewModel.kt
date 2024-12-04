package com.jyoti.eventcalender.base

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

/**
 * Marker class for the view state
 */
interface State

/**
 * Marker class for View Intents
 */
interface MviIntent

/**
 * Marker class for the reducer actions
 */
interface ReduceAction

private const val FLOW_BUFFER_CAPACITY = 64

abstract class MviViewModel<S : State, I : MviIntent, R : ReduceAction>(
    initialState: S
) : ViewModel() {

    protected val simpleName: String = this.javaClass.simpleName

    private val stateFlow = MutableStateFlow<S>(initialState)
    val state: StateFlow<S> = stateFlow.asStateFlow()
    private val intentFlow = MutableSharedFlow<I>(extraBufferCapacity = FLOW_BUFFER_CAPACITY)
    private val reduceFlow = MutableSharedFlow<R>(extraBufferCapacity = FLOW_BUFFER_CAPACITY, replay = 1)

    init {
        intentFlow
            .onEach { intent ->
                executeIntent(intent)
            }
            .launchIn(viewModelScope)
        reduceFlow
            .onEach { action ->
                stateFlow.value = reduce(stateFlow.value, action)
            }
            .launchIn(viewModelScope)
    }

    fun onIntent(mviIntent: I) {
        Log.d("handle mvi intent", "${mviIntent.javaClass.simpleName} on $simpleName")
        intentFlow.tryEmit(mviIntent)
    }

    protected fun handle(reduceAction: R) {
        Log.d("handle mvi action"," ${reduceAction.javaClass.simpleName } on $simpleName", null)
        reduceFlow.tryEmit(reduceAction)
    }

    protected abstract fun executeIntent(mviIntent: I)

    protected abstract fun reduce(state: S, reduceAction: R): S
}

enum class LoadState {
    IDLE, LOADING, LOADED, ERROR
}
