package com.mukul.jan.testingandroid

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.*
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.kotlin.mock

// Generally, while testing coroutines, we have to care about Dispatchers.
// Dispatchers - It determines which thread will use for coroutine
// Dispatcher.IO - Networks calls, database operations etc.
// Dispatcher.Main - runs on android main thread for UI
// Dispatcher.Default - for CPU intensive tasks, it uses CPU cores.
//
// Testing coroutines need TestDispatcher to run tests.
// Types of TestDispatcher - 1. StandardTestDispatcher
//                           2. UnconfinedTestDispatcher
// StandardTestDispatcher : A standard test dispatcher does not execute any tasks automatically.
// When coroutines are launched with this dispatcher, instead of executing immediately they are left in a pending state.
// It gives guarantee that all coroutines will run in order.
//
// But How to run them, if we use StandardTestDispatcher?
// To run/launch them, we have to use - 1. advanceUntilIdle()
//                                      2. runCurrent()
//                                      3. advanceByMills()
// advanceUntilIdle() - It executes all pending coroutines. (Most of people use this)
//
// UnconfinedTestDispatcher : It same as above, but a unconfined test dispatcher execute tasks automatically.
// It gives no guarantee that all coroutines will run in order
//

@OptIn(ExperimentalCoroutinesApi::class)
class CoroutineUnitTest {

    lateinit var repo: MainRepo
    lateinit var viewModel: MainViewModel

    @Before
    fun setup() {
        repo = mock()  //mock() - it provides a test object of given class (Part of Mockito framework)
        viewModel = MainViewModel(repo)

    }


    @Test
    fun register_user_if_not_exits_should_return_true() = runTest {
        val user = "mukul"
        val isRegistered = viewModel.register(user)
        Assert.assertTrue(isRegistered)
    }

    @Test
    fun register_user_if_exits_should_return_false() = runTest {
        val user = "mukul"
        viewModel.register(user) //duplicate
        val isRegistered = viewModel.register(user)
        Assert.assertFalse(isRegistered)
    }

    @Test
    fun register_user_should_found_in_registered_user_list() = runTest {
        //runTest { }
        //It launch a new coroutine using StandardTestDispatcher
        //It skips all delays.
        //
        //advanceUntilIdle() - it execute all pending coroutines before continue to next process.
        //
        val user1 = "mukul"
        val user2 = "jangir"
        launch { viewModel.register(user1) }
        launch { viewModel.register(user2) }
        advanceUntilIdle() //wait for all pending coroutines to complete
        //HINT : comment advanceUntilIdle() line, then try to rerun the test, it will fail
        val isUser2Registered = viewModel.isUserRegistered(user2)
        Assert.assertTrue(isUser2Registered)

        //OR
        Assert.assertEquals(listOf(user1,user2),viewModel.getRegisterUsers())
    }

    @Test
    fun get_user_with_dispatcher_passing_as_argument_test() = runTest {
        //Why runTest {}
        //we use runTest for running suspend functions.
        //
        //Sometimes you have to pass dispatcher in viewmodel or repo,
        //To test them, do like this.
        //
        //under standardTestDispatcher, coroutines not executes immediately.
        //To run them, you have to use advanceUntilIdle(), runCurrent() etc.
        //StandardTestDispatcher gives you full control then UnconfinedTestDispatcher

        val standardTestDispatcher = StandardTestDispatcher(testScheduler)
        val secondViewModel = SecondViewModel(standardTestDispatcher)
        //add first
        launch {
            secondViewModel.addUser("mukul")
        }
        launch {
            secondViewModel.addUser("mukul1")
            secondViewModel.addUser("mukul2")
        }
        //get
        val res = secondViewModel.getUsers()
        Assert.assertEquals(listOf("mukul","mukul1","mukul2"),res)


        //You might thinks, Here we used StandardTestDispatcher()
        //But we haven't used advanceUntilIdle() or any, to run coroutines
        //Bro, you are correct.
        //But here is mystery.
        //We already launched our coroutines in secondViewModel.addUser(...)
        //If this function only will suspend, then we have to use advanceUntilIdle()
    }


    @Test
    fun working_with_main_dispatcher() = runTest {
        //As you seen, We have worked with only io dispatcher.
        //Now, let's come to Main thread or Dispatcher.Main
        //
        //We usually use, lifecycleScope { } or viewModelScope { }
        //These scopes uses Dispatcher.Main behind the scenes.
        //As we are doing unit testing, running our tests on JVM, not android device
        //so we don't have main thread.
        //So How we can run our tests that uses main thread or Dispatcher.Main
        //
        //Here is solution,
        //Here we are switching from Dispatcher to TestDispatcher for running our tests.
        //
        val user = "mukul"

        val dispatcher = UnconfinedTestDispatcher(testScheduler)
        Dispatchers.setMain(dispatcher)
        viewModel.addMyUser(user)            //add
        Assert.assertEquals(listOf(user),viewModel.myUsers)
        Dispatchers.resetMain()
    }

    /**
     * REPLACEMENT FOR working_with_main_dispatcher() test login
     */

    //We have defined our own rule for TestDispatcher
    //for settings MainDispatcher to test dispatcher and reset after test done
    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    @Test
    fun working_with_main_dispatcher_replacement() = runTest {
        //have a look at above function
        //This is replacement for above testing logic
        //
        //We can make our own rule to avoid writing code everytime
        //while testing lifecycleScope or viewModelScope
        val user = "mukul"
        viewModel.addMyUser(user)
        Assert.assertEquals(listOf(user),viewModel.myUsers)
    }

    //You can also pass TestDispatcher from TestRule to any class
    //like this
    @Test
    fun what_about_case_2() = runTest{
        val secondViewModel = SecondViewModel(mainDispatcherRule.testDispatcher)
        secondViewModel.addUser("hello")
        Assert.assertEquals(listOf("hello"),secondViewModel.users)
    }

    val testScope = TestScope()

    @Test
    fun creating_our_own_scope() = testScope.runTest {
        viewModel.register("mukul")
        Assert.assertEquals(listOf("mukul"),viewModel.getRegisteredUsers())
    }

}


























