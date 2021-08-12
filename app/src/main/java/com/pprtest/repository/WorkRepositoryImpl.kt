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

    private var flagThreeOrTwoColumns = true

    override suspend fun loadNumbers() {
        CoroutineScope(Dispatchers.Default).launch {
            var countForLoading = 0
            while (countForLoading <= COUNT_FOR_LOADING) {
                countForLoading++
                if (listSimpleNumber.isEmpty()) {
                    listSimpleNumber.add(NumberItem((1).toBigInteger(), isWhiteColor(listSimpleNumber)))
                } else {
                    val lastNumber = listSimpleNumber[listSimpleNumber.lastIndex].number.toInt()
                    listSimpleNumber.add(
                            NumberItem((lastNumber + 1).toBigInteger(),
                                    isWhiteColor(listSimpleNumber)))
                }
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
                    val lastNumber = listFibonacciNumber[listFibonacciNumber.lastIndex].number.toInt()
                    val preLastNumber = listFibonacciNumber[listFibonacciNumber.lastIndex - 1].number.toInt()
                    listFibonacciNumber.add(NumberItem((lastNumber + preLastNumber).toBigInteger(),
                            isWhiteColor(listFibonacciNumber)))
                }
            }
            _fibonacciNumberLiveData.postValue(listFibonacciNumber)
        }
    }

    // этот метод режет глаз и где-то плачет принцип DRY
    // но как не старался, пока не могу понять, как сделать его лучше
    override suspend fun treeOrTwoColumnsList(flagThreeColumns: Boolean) {
        flagThreeOrTwoColumns = flagThreeColumns
        CoroutineScope(Dispatchers.Default).launch {
            if (!flagThreeColumns) {
                var countSimple = 0
                var countFibonacci = 0
                listSimpleNumber.map {
                    listSimpleNumber[0].whiteColorNumber = false
                    if (listSimpleNumber.indexOf(it) > 0) {
                        it.whiteColorNumber = !listSimpleNumber[countSimple - 1].whiteColorNumber
                    }
                    countSimple++
                }
                listFibonacciNumber.map {
                    listFibonacciNumber[0].whiteColorNumber = false
                    if (listFibonacciNumber.indexOf(it) > 0) {
                        it.whiteColorNumber = !listFibonacciNumber[countFibonacci - 1].whiteColorNumber
                    }
                    countFibonacci++
                }
            } else {
                var countSimple = 0
                var countFibonacci = 0
                listSimpleNumber.map {
                    listSimpleNumber[0].whiteColorNumber = false
                    listSimpleNumber[1].whiteColorNumber = true
                    if (listSimpleNumber.indexOf(it) > 1) {
                        it.whiteColorNumber = !listSimpleNumber[countSimple - 2].whiteColorNumber
                    }
                    countSimple++
                }
                listFibonacciNumber.map {
                    listFibonacciNumber[0].whiteColorNumber = false
                    listFibonacciNumber[1].whiteColorNumber = true
                    if (listFibonacciNumber.indexOf(it) > 1) {
                        it.whiteColorNumber = !listFibonacciNumber[countFibonacci - 2].whiteColorNumber
                    }
                    countFibonacci++
                }
            }
        }
    }


    private fun isWhiteColor(numbers: List<NumberItem>): Boolean {
        val lastIndex = numbers.lastIndex
        return when {
            lastIndex == -1 -> false
            lastIndex == 0 -> true
            flagThreeOrTwoColumns && numbers[lastIndex].whiteColorNumber == numbers[lastIndex - 1]
                    .whiteColorNumber -> !numbers[lastIndex].whiteColorNumber
            !flagThreeOrTwoColumns && numbers[lastIndex].whiteColorNumber == numbers[lastIndex]
                    .whiteColorNumber -> !numbers[lastIndex].whiteColorNumber
            else -> numbers[lastIndex].whiteColorNumber
        }
    }
}


