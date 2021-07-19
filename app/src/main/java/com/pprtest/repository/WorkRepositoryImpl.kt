package com.pprtest.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.map
import com.pprtest.dto.NumberItem
import com.pprtest.util.CountUtil.isWhiteColor
import kotlinx.coroutines.*
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import java.math.BigInteger
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

    override suspend fun loadNumbers() {
        CoroutineScope(Dispatchers.Default).launch {
            var countForLoading = 0
            while (countForLoading <= COUNT_FOR_LOADING) {
                countForLoading++
                _simpleNumbersLiveData.value?.add(
                    NumberItem(
                        (_simpleNumbersLiveData.value?.size!! + 1).toBigInteger(),
                        isWhiteColor(_simpleNumbersLiveData.value!!)
                    )
                )
            }
        }
    }

    override suspend fun loadNumbersFibonacci() {
        CoroutineScope(Dispatchers.Default).launch {
            var countForLoading = 0
            val lastNumber: BigInteger?
            val preLastNumber: BigInteger?
            var numberFibonacci: BigInteger = (0).toBigInteger()

            if (_fibonacciNumberLiveData.value != null) {
                lastNumber =
                    _fibonacciNumberLiveData.value?.get(_fibonacciNumberLiveData.value?.lastIndex!!)?.number
                preLastNumber =
                    _fibonacciNumberLiveData.value?.get(_fibonacciNumberLiveData.value?.lastIndex!! - 1)?.number
                if (lastNumber != null) {
                    numberFibonacci = lastNumber + preLastNumber!!
                }
            } else {
                numberFibonacci
            }
            while (countForLoading <= COUNT_FOR_LOADING) {
                countForLoading++
                _fibonacciNumberLiveData.value?.add(
                    NumberItem(
                        numberFibonacci,
                        isWhiteColor(_fibonacciNumberLiveData.value!!)
                    )
                )
            }
        }
    }
}