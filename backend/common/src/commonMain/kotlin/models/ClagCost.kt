package ru.otus.otuskotlin.cleaningaggregator.common.models

import com.ionspin.kotlin.bignum.decimal.BigDecimal

data class ClagCost(
    var amount: BigDecimal = BigDecimal.ZERO,
    var currency: String = "RUB"
) {
    fun isZero() = amount == BigDecimal.ZERO
}
