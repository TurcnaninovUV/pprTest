package com.pprtest.dto

import java.math.BigInteger

data class NumberItem(
        val number: BigInteger,
        var whiteColorNumber: Boolean = true
)