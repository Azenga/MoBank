package ke.co.mobank.ui.transactions.list

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ke.co.mobank.data.models.Transaction
import ke.co.mobank.databinding.TransactionItemBinding

class TransactionsAdapter(private val itemClickListener: TransactionItemClickListener?) :
    RecyclerView.Adapter<TransactionsAdapter.TransactionHolder>() {

    var transactions: List<Transaction>? = null
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TransactionHolder =
        TransactionHolder(
            TransactionItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )

    override fun getItemCount(): Int = transactions?.size ?: 0


    override fun onBindViewHolder(holder: TransactionHolder, position: Int) {


        val transaction = transactions!![position];

        holder.binding.contactTextView.text = transaction.customerContact
        holder.binding.amountTextView.text = transaction.transactionAmount.toString()
        holder.binding.typeTextView.text = transaction.transactionType.toString()
        holder.binding.platformTextView.text = transaction.transactionPlatform
        holder.binding.nationalIdTextView.text =
            if (transaction.customerNationalId == null) "ID MIA" else transaction.customerNationalId

        holder.binding.root.setOnClickListener { itemClickListener?.onItemClick(transaction) }
    }

    inner class TransactionHolder(val binding: TransactionItemBinding) :
        RecyclerView.ViewHolder(binding.root)

    interface TransactionItemClickListener {
        fun onItemClick(transaction: Transaction)
    }
}