package com.mukul.jan.testingandroid

class FakeRoomDb{
    lateinit var fakeDao: FakeDao
}
interface FakeDao{
    suspend fun addUsers(name: String) : Long
    suspend fun getUsers() : List<String>
}