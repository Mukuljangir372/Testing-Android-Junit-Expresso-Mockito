package com.mukul.jan.testingandroid

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainViewModel(
   private val repo: MainRepo
): ViewModel() {

    private val _users = MutableLiveData<List<String>>()
    val users : LiveData<List<String>> get() =  _users

    private val registerUsers: ArrayList<String> = arrayListOf()

    fun getUsers(){
        viewModelScope.launch {
            val result = repo.getUsers()
            delay(2000)
            _users.value = result
        }
    }

    var myUsers = arrayListOf<String>()
    fun addMyUser(name: String) = viewModelScope.launch {
        myUsers.add(name)
    }

    suspend fun getRegisterUsers() : ArrayList<String> {
        delay(1000)
        return registerUsers
    }

    suspend fun register(name: String) : Boolean{
        delay(1000)
        val isExits = registerUsers.contains(name)
        if(!isExits){
            registerUsers.add(name)
        }
        return !isExits
    }

    suspend fun isUserRegistered(name: String): Boolean{
        delay(1000)
        return registerUsers.contains(name)
    }

    suspend fun getRegisteredUsers() : ArrayList<String>{
        delay(1000)
        return registerUsers
    }


    //--------------------------- FLOWS -------------------------------------
    suspend fun emitFakeItem() = flow {
        emit("1")
    }


}

class MainRepo(){
    fun getUsers() : List<String>{
        return listOf("mukul","jangir")
    }
}