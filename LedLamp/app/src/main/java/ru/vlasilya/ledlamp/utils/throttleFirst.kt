package ru.vlasilya.ledlamp.utils

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

fun <T> throttleFirst(
    skipMs: Long = 300L,
    scope: CoroutineScope,
    destinationFunction: suspend (T) -> Unit
): (T) -> Unit {
    var throttleJob: Job? = null
    return { param: T ->
        if (throttleJob?.isCompleted != false) {
            throttleJob = scope.launch {
                destinationFunction(param)
                delay(skipMs)
            }
        }
    }
}