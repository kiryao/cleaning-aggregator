package ru.otus.otuskotlin.cleaningaggregator.common.models

import kotlin.jvm.JvmInline

@JvmInline
    value class ClagExecutorId(private val id: String) {
    fun asString() = id

    companion object {
        val NONE = ClagExecutorId("")
    }
}
