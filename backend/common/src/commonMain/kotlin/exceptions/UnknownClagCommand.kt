package ru.otus.otuskotlin.cleaningaggregator.common.exceptions

import ru.otus.otuskotlin.cleaningaggregator.common.models.ClagCommand

class UnknownClagCommand(command: ClagCommand) : Throwable("Wrong command $command at mapping toTransport stage")
