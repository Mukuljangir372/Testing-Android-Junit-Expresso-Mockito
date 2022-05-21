package com.mukul.jan.testingandroid

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.services.storage.internal.TestStorageUtil
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException


@RunWith(AndroidJUnit4::class)
class RoomTest {

    lateinit var fakeRoomDb: FakeRoomDb
    lateinit var fakeDao: FakeDao

    @Before
    fun setup() {
        var context = ApplicationProvider.getApplicationContext<Context>()
//        fakeRoomDb = Room.inMemoryDatabaseBuilder(
//            context, FakeRoomDb::class
//        ).build()

//        fakeDao = fakeRoomDb.fakeDao
    }

    @After
    @Throws(IOException::class)
    fun tear() {
//        fakeRoomDb.close()
    }

    @Test
    fun add_user() = runBlocking {
        val user = "mukul"
        fakeDao.addUsers(user)
        val list = fakeDao.getUsers()
        Assert.assertEquals(list.contains(user),true)
    }


}
