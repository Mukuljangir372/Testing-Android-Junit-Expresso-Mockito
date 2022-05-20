package com.mukul.jan.testingandroid

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.mockito.kotlin.mock

class TestingFlows {

    lateinit var repo: MainRepo
    lateinit var viewModel: MainViewModel

    @Before
    fun setup() {
        repo = mock()
        viewModel = MainViewModel(repo)
    }

    @Test
    fun flow_test_1() = runBlocking{
        val res = viewModel.emitFakeItem().toList()
        Assert.assertEquals(listOf("1"),res)
    }



















}