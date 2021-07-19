package com.pprtest.util

import com.pprtest.dto.NumberItem

object CountUtil {

    fun isWhiteColor(numbers: List<NumberItem>): Boolean {
        val lastIndex = numbers.lastIndex
        return when {
            lastIndex == -1 -> false
            lastIndex == 0 -> true
            numbers[lastIndex].whiteColorNumber == numbers[lastIndex - 1].whiteColorNumber -> !numbers[lastIndex].whiteColorNumber
            else -> numbers[lastIndex].whiteColorNumber
        }
    }

    fun isRequiredNumber(number: Int): Boolean {
        var flag = false
        for (i in 2..number / 2) {
            if (number % i == 0) {
                flag = true
                break
            }
        }
        return !flag
    }
}