package com.pprtest.viewmodels


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pprtest.repository.WorkRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class NumberViewModel @Inject constructor(
    private val repository: WorkRepository
) : ViewModel() {

    val simpleData = repository.simpleNumbersLiveData
    val fibonacciData = repository.fibonacciNumberLiveData


    fun loadNumbers() = viewModelScope.launch {
        repository.loadNumbers()
    }

    fun loadNumbersFibonacci() = viewModelScope.launch {
        repository.loadNumbersFibonacci()
    }


}