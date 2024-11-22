package ru.otus.otuskotlin.cleaningaggregator.common.models

import kotlin.jvm.JvmInline

@JvmInline
value class ClagCustomerId(private val id: String) {
    fun asString() = id

    companion object {
        val NONE = ClagCustomerId("")
    }
}
