package ke.co.mobank.data.models

import java.util.*

data class Transaction(
    val customerName: String? = null,
    val customerContact: String? = null,
    val transactionId: String? = null,
    val transactionAmount: Double? = null,
    val transactionTime: Date? = null,
    val transactionType: String? = null,
    val transactionPlatform: String? = null
)