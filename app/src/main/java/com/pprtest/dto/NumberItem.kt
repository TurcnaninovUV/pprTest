package com.pprtest.dto

import java.math.BigInteger

data class NumberItem(
    val number: BigInteger,
    val whiteColorNumber: Boolean = true
)