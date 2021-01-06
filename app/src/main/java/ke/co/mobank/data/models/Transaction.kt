package ke.co.mobank.data.models

import com.google.firebase.firestore.DocumentId
import com.google.firebase.firestore.ServerTimestamp
import java.io.Serializable
import java.util.*

data class Transaction(
    @DocumentId val id: String? = null,
    var customerName: String? = null,
    var customerContact: String? = null,
    var customerNationalId: String? = null,
    var transactionId: String? = null,
    var transactionAmount: Double? = null,
    @ServerTimestamp
    var transactionTime: Date? = null,
    var transactionType: String? = null,
    var transactionPlatform: String? = null
) : Serializable