package ke.co.mobank.data.repositories

import com.google.firebase.firestore.FirebaseFirestore
import ke.co.mobank.data.models.Transaction

class TransactionRepository(private val transactionTasksListener: TransactionTasksListener?) {

    private val firebaseFirestore by lazy { FirebaseFirestore.getInstance() }

    fun addTransaction(transaction: Transaction) {

        firebaseFirestore.collection("transactions")
            .add(transaction)
            .addOnCompleteListener { addTransactionTask ->

                if (addTransactionTask.isSuccessful) {
                    transactionTasksListener?.onTransactionAdded(transaction)
                } else {
                    transactionTasksListener?.onException(addTransactionTask.exception!!)
                }
            }
    }

    fun readAllTransactions() {
        firebaseFirestore.collection("transactions")
            .addSnapshotListener { value, error ->

                if (error != null) {
                    transactionTasksListener?.onException(error)
                    return@addSnapshotListener
                }

                transactionTasksListener?.onTransactionsRetrieved(value?.toObjects(Transaction::class.java))
            }
    }


    interface TransactionTasksListener {
        fun onTransactionAdded(transaction: Transaction)

        fun onTransactionsRetrieved(transactions: MutableList<Transaction>?)

        fun onException(exception: Exception)
    }
}