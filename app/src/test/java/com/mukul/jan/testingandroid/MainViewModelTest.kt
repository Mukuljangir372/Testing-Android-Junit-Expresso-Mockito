package com.mukul.jan.testingandroid

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import app.cash.turbine.test
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.*
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import org.mockito.kotlin.*

/**
 * @get: Rule
 * There are some predefined rules provided by testing framework,
 * to change the behaviour of How tests runs.
 *
 * Why InstantTaskExecutorRule ?
 * By default, Most of android architecture components uses Background executor to perform tasks
 * asynchronous. To change from asynchronous to synchronous, we use InstantTaskExecutorRule.
 * Example - LiveData in viewmodel works asynchronous. While testing, we need our result synchronously
 * to test its results/outcome.
 */

@OptIn(ExperimentalCoroutinesApi::class)
class MainViewModelTest {

    @get:Rule
    val mainTestDispatcherRule = MainDispatcherRule()

    @get:Rule
    val taskExecutorRule = InstantTaskExecutorRule()

    @Mock
    lateinit var repo: MainRepo
    lateinit var viewModel: MainViewModel

    @Mock
    lateinit var usersObserver : Observer<List<String>>

    @Before
    fun setup(){
        MockitoAnnotations.openMocks(this)

        viewModel = MainViewModel(repo)

        viewModel.users.observeForever(usersObserver)

    }

    @Test
    fun get_users_should_trigger_observer() = runTest {
        viewModel.getUsers()
        advanceUntilIdle()                   //execute all pending coroutines/jobs
        val result = viewModel.users.value
        verify(usersObserver).onChanged(result)   //verify onChanged() of observer called with same result
    }

    @Test
    fun get_users_flow_should_trigger_flow_collector() = runBlocking {
        val res = viewModel.getUsersByFlow().toList()
        Assert.assertEquals(listOf("1"),res[0])
    }



}

















