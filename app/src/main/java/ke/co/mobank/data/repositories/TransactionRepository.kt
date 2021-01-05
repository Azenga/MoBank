package ke.co.mobank.data.repositories

import android.util.Log
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

    fun deleteTransaction(transaction: Transaction) {

        transaction.id?.let {
            firebaseFirestore.collection("transactions")
                .document(transaction.id)
                .delete()
                .addOnCompleteListener { deleteTransactionTask ->
                    if (deleteTransactionTask.isSuccessful) {
                        transactionTasksListener?.onTransactionDelete(true)
                    } else {
                        transactionTasksListener?.onException(deleteTransactionTask.exception!!)
                    }
                }
        }

        transactionTasksListener?.onTransactionDelete(false)
    }

    fun updateTransaction(transaction: Transaction) {
        transaction.id?.let {
            firebaseFirestore.collection("transactions")
                .document(transaction.id)
                .set(transaction)
                .addOnCompleteListener { addTransactionTask ->
                    if (addTransactionTask.isSuccessful) {
                        transactionTasksListener?.onTransactionUpdated(transaction)
                    } else {
                        transactionTasksListener?.onException(addTransactionTask.exception!!)
                    }
                }
        }
    }


    interface TransactionTasksListener {
        fun onTransactionAdded(transaction: Transaction)

        fun onTransactionDelete(transactionDelete: Boolean)

        fun onTransactionUpdated(transaction: Transaction)

        fun onTransactionsRetrieved(transactions: MutableList<Transaction>?)

        fun onException(exception: Exception)
    }
}