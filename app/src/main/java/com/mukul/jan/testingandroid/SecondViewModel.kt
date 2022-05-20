package com.mukul.jan.testingandroid

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.*

class SecondViewModel(
    private val ioDispatcher : CoroutineDispatcher
): ViewModel() {
    val scope = CoroutineScope(ioDispatcher)
    val users = arrayListOf<String>()

    suspend fun addUser(name: String) {
        scope.launch {
            users.add(name)
        }
    }

    suspend fun getUsers() = withContext(ioDispatcher){
        delay(1000)
        users
    }

}