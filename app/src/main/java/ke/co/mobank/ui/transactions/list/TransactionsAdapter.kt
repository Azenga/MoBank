package ke.co.mobank.ui.transactions.list

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ke.co.mobank.data.models.Transaction
import ke.co.mobank.databinding.TransactionItemBinding

class TransactionsAdapter : RecyclerView.Adapter<TransactionsAdapter.TransactionHolder>() {

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

        holder.binding.contactTextView.text = transactions!![position].customerContact
        holder.binding.amountTextView.text = transactions!![position].transactionAmount.toString()
        holder.binding.typeTextView.text = transactions!![position].transactionType.toString()
        holder.binding.platformTextView.text = transactions!![position].transactionPlatform
    }

    inner class TransactionHolder(val binding: TransactionItemBinding) :
        RecyclerView.ViewHolder(binding.root)
}