package com.mukul.jan.testingandroid

class Calculator(val operation: Operation){
    fun addTwoNumbers(a: Int, b: Int) = operation.add(a,b)
}
object Operation {
    fun add(a: Int, b: Int) = a + b
    fun subtract(a: Int, b: Int) = a - b
}
