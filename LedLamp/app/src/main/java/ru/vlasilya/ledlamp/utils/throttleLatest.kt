package ru.vlasilya.ledlamp.utils

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

fun <T> throttleLatest(
    intervalMs: Long = 100L,
    scope: CoroutineScope,
    destinationFunction: suspend (T) -> Unit
): (T) -> Unit {
    var throttleJob: Job? = null
    var latestParam: T
    return { param: T ->
        latestParam = param
        if (throttleJob?.isCompleted != false) {
            throttleJob = scope.launch {
                delay(intervalMs)
                destinationFunction(latestParam)
            }
        }
    }
}