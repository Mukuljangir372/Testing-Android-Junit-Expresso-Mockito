package com.mukul.jan.testingandroid

import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.*

/**
 * Mockito Framework :
 * Basically, it used for mocking the classes.
 *
 * @Mock
 * It means mocking the class, In simple words, We are creating the object of specified class
 *
 * @Before
 * Any functions annotated with @Before, will called everytime before any test start running.
 * Basically, we setup some object to use in testing.
 *
 * verify() (function of mockito kotlin)
 * it verifies that a function from mocked class called or not.
 */

@RunWith(MockitoJUnitRunner::class)
class CalculatorTest {

    @Mock
    lateinit var operation: Operation

    lateinit var calculator: Calculator

    @Before
    fun setup(){
        calculator = Calculator(operation)
    }

    @Test
    fun given_valid_input_when_add_should_call_operation() {
        val a = 1
        val b = 10
        calculator.addTwoNumbers(a,b)
        verify(operation,times(1)).add(eq(a), eq(b))

//      times() - number of times a function called
//      add() is a function from mocked class that will verified for if called or not with
//      specified params passed as same as parent class function.
//      eq() passing params values into mocked object
//
//      Explain : This test will verify that add(..) of Operation object called or not with same params
//      as passed in calculator.addTwoNumbers(a,b)
    }

    @Test   //test functions name is typed random
    fun given_1_2_numbers_to_add_should_return_4() {
        val a = 1
        val b = 2
        whenever(calculator.addTwoNumbers(1,2)).thenReturn(4)
        val result = calculator.addTwoNumbers(a,b)
        Assert.assertEquals(4,result)
//      whenever().then..
//      It verifies that given function with specified params if occurs, then return x result.
    }


}