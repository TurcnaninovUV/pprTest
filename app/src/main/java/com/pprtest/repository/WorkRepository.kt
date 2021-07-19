package com.pprtest.repository

import androidx.lifecycle.LiveData
import com.pprtest.dto.NumberItem

interface WorkRepository {

    val simpleNumbersLiveData: LiveData<List<NumberItem>>
    val fibonacciNumberLiveData: LiveData<List<NumberItem>>

    suspend fun loadNumbers()
    suspend fun loadNumbersFibonacci()

}