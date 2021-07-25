package com.pprtest.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.map
import com.pprtest.dto.NumberItem
import kotlinx.coroutines.*
import javax.inject.Inject
import javax.inject.Singleton


const val COUNT_FOR_LOADING = 50


@Singleton
class WorkRepositoryImpl @Inject constructor() : WorkRepository {

    private var _simpleNumbersLiveData = MutableLiveData<MutableList<NumberItem>>()
    override val simpleNumbersLiveData: LiveData<List<NumberItem>>
        get() = _simpleNumbersLiveData.map { it.toList() }

    private var _fibonacciNumberLiveData = MutableLiveData<MutableList<NumberItem>>()
    override val fibonacciNumberLiveData: LiveData<List<NumberItem>>
        get() = _fibonacciNumberLiveData.map { it.toList() }

    private var listSimpleNumber = mutableListOf<NumberItem>()
    private var listFibonacciNumber = mutableListOf<NumberItem>()


    override suspend fun loadNumbers() {
        CoroutineScope(Dispatchers.Default).launch {
            var countForLoading = 0
            while (countForLoading <= COUNT_FOR_LOADING) {
                countForLoading++
                listSimpleNumber.add(NumberItem((listSimpleNumber.size + 1).toBigInteger(),
                        isWhiteColor(listSimpleNumber)))
            }
            _simpleNumbersLiveData.postValue(listSimpleNumber)
        }
    }

    override suspend fun loadNumbersFibonacci() {
        CoroutineScope(Dispatchers.Default).launch {
            var countForLoading = 0
            while (countForLoading <= COUNT_FOR_LOADING) {
                countForLoading++
                if (listFibonacciNumber.isEmpty()) {
                    listFibonacciNumber.add(NumberItem((0).toBigInteger(),
                            isWhiteColor(listFibonacciNumber)))
                    listFibonacciNumber.add(NumberItem((1).toBigInteger(),
                            isWhiteColor(listFibonacciNumber)))
                } else {
                    val lastNumber = listFibonacciNumber[listFibonacciNumber.size - 1]
                    val preLastNumber = listFibonacciNumber[listFibonacciNumber.size - 2]
                    listFibonacciNumber.add(NumberItem((lastNumber.number + preLastNumber.number),
                            isWhiteColor(listFibonacciNumber)))
                }
            }
            _fibonacciNumberLiveData.postValue(listFibonacciNumber)
        }
    }

    private fun isWhiteColor(numbers: List<NumberItem>): Boolean {
        val lastIndex = numbers.lastIndex
        return when {
            lastIndex == -1 -> false
            lastIndex == 0 -> true
            numbers[lastIndex].whiteColorNumber == numbers[lastIndex - 1].whiteColorNumber ->
                !numbers[lastIndex].whiteColorNumber
            else -> numbers[lastIndex].whiteColorNumber
        }
    }
}


