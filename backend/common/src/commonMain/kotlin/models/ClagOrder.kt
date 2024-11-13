package ru.otus.otuskotlin.cleaningaggregator.common.models

import kotlinx.datetime.Instant
import ru.otus.otuskotlin.cleaningaggregator.common.NONE

data class ClagOrder(
    var id: ClagOrderId = ClagOrderId.NONE,
    var title: String = "",
    var description: String = "",
    var specialRequirements: String = "",
    var cleaningType: ClagCleaningType = ClagCleaningType.NONE,
    var address: ClagAddress = ClagAddress(),
    var sheduledFor: Instant = Instant.NONE,
    var cost: ClagCost = ClagCost(),
    var executorId: ClagExecutorId = ClagExecutorId.NONE,
    var customerId: ClagCustomerId = ClagCustomerId.NONE,
    var status: ClagOrderStatus = ClagOrderStatus.DRAFT,
    var metadata: ClagOrderMetadata = ClagOrderMetadata(),
    var lock: ClagOrderLock = ClagOrderLock.NONE,
    val permissionsClient: MutableSet<ClagOrderPermissionsClient> = mutableSetOf(),
) {
    fun isEmpty() = this == NONE

    companion object {
        private val NONE = ClagOrder()
    }
}
