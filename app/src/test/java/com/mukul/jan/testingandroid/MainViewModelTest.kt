package com.mukul.jan.testingandroid

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.*
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.kotlin.doAnswer
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever

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

class MainViewModelTest {

    @get:Rule
    val taskExecutorRule = InstantTaskExecutorRule()

    lateinit var repo: MainRepo
    lateinit var viewModel: MainViewModel
    lateinit var usersObserver : Observer<List<String>>

    @OptIn(ExperimentalCoroutinesApi::class)
    @Before
    fun setup(){
        repo = mock()
        viewModel = MainViewModel(repo)

        usersObserver = mock()

        viewModel.users.observeForever(usersObserver)

    }

    @Test
    fun getUser_should_return_users(){
        val result = repo.getUsers()
        verify(usersObserver).onChanged(result)
    }

}

















