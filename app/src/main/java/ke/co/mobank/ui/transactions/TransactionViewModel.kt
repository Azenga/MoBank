package ke.co.mobank.ui.transactions

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ke.co.mobank.data.models.Transaction
import ke.co.mobank.data.repositories.TransactionRepository

class TransactionViewModel : ViewModel(), TransactionRepository.TransactionTasksListener {

    private val _transaction: MutableLiveData<Transaction> = MutableLiveData()
    private val _transactions: MutableLiveData<List<Transaction>> = MutableLiveData()
    private val _exception: MutableLiveData<Exception> = MutableLiveData()

    val transaction: LiveData<Transaction> get() = _transaction
    val transactions: LiveData<List<Transaction>> get() = _transactions
    val exception: LiveData<Exception> get() = _exception

    private val transactionRepository by lazy { TransactionRepository(this) }

    fun addTransaction(transaction: Transaction) {
        transactionRepository.addTransaction(transaction)
    }

    fun readAllTransactions() {
        transactionRepository.readAllTransactions()
    }

    override fun onTransactionAdded(transaction: Transaction) {
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