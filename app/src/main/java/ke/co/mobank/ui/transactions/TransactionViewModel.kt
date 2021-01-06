package ke.co.mobank.ui.transactions

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ke.co.mobank.data.models.Transaction
import ke.co.mobank.data.repositories.TransactionRepository

private const val TAG = "TransactionViewModel"

class TransactionViewModel : ViewModel(), TransactionRepository.TransactionTasksListener {

    private val _transaction: MutableLiveData<Transaction> = MutableLiveData()
    private val _transactions: MutableLiveData<List<Transaction>> = MutableLiveData()
    private val _exception: MutableLiveData<Exception> = MutableLiveData()
    private val _transactionDeleted: MutableLiveData<Boolean> = MutableLiveData()

    val transaction: LiveData<Transaction> get() = _transaction
    val transactions: LiveData<List<Transaction>> get() = _transactions
    val exception: LiveData<Exception> get() = _exception
    val transactionDelete: LiveData<Boolean> get() = _transactionDeleted

    private val transactionRepository by lazy { TransactionRepository(this) }

    fun addTransaction(transaction: Transaction) {
        transactionRepository.addTransaction(transaction)
    }

    fun updateTransaction(transaction: Transaction) {
        transactionRepository.updateTransaction(transaction)
    }

    fun deleteTransaction(transaction: Transaction) {
        transactionRepository.deleteTransaction(transaction)
    }

    fun readAllTransactions(platform: String?) {
        transactionRepository.readAllTransactions(platform)
        Log.i(TAG, "readAllTransactions: platform => $platform")
    }

    override fun onTransactionAdded(transaction: Transaction) {
        _transaction.postValue(transaction)
    }

    override fun onTransactionDelete(transactionDelete: Boolean) {
        _transactionDeleted.postValue(transactionDelete)
    }

    override fun onTransactionUpdated(transaction: Transaction) {
        _transaction.postValue(transaction)
    }

    override fun onTransactionsRetrieved(transactions: MutableList<Transaction>?) {
        transactions?.let {
            _transactions.postValue(transactions)
        }
    }

    override fun onException(exception: Exception) {
        _exception.postValue(exception)
    }
}