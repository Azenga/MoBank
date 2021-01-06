package ke.co.mobank.ui.transactions.details

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.Navigation
import ke.co.mobank.data.models.Transaction
import ke.co.mobank.databinding.FragmentSingleTransactionBinding
import ke.co.mobank.ui.transactions.TransactionViewModel

private const val TAG = "SingleTransactionFrag"

class SingleTransactionFragment : Fragment() {

    private lateinit var transaction: Transaction
    private lateinit var binding: FragmentSingleTransactionBinding
    private lateinit var viewModel: TransactionViewModel
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        transaction = SingleTransactionFragmentArgs.fromBundle(requireArguments()).transaction
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSingleTransactionBinding.inflate(inflater, container, false);
        // Inflate the layout for this fragment
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        navController = Navigation.findNavController(view)

        binding.customerNameTextView.text = transaction.customerName
        binding.customerContactTextView.text = transaction.customerContact
        binding.customerNationalIdTextView.text =
            if (transaction.customerNationalId != null) transaction.customerNationalId else "No Set"

        binding.transactionIdTextView.text = transaction.transactionId
        binding.transactionAmountTextView.text = transaction.transactionAmount.toString()
        binding.transactionPlatformTextView.text = transaction.transactionPlatform
        binding.transactionTypeTextView.text = transaction.transactionType
        binding.transactionTimeTextView.text =
            if (transaction.transactionTime != null) transaction.transactionTime.toString() else "No Set"

        binding.editButton.setOnClickListener {
            val action = SingleTransactionFragmentDirections.actionEditTransaction(transaction)
            navController.navigate(action)
        }

        binding.deleteButton.setOnClickListener { viewModel.deleteTransaction(transaction) }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        viewModel = ViewModelProvider(this).get(TransactionViewModel::class.java)

        viewModel.transactionDelete.observe(viewLifecycleOwner, Observer { transactionDeleted ->
            if (transactionDeleted) {
                Toast.makeText(
                    requireContext(),
                    "Transaction Deleted Successfully",
                    Toast.LENGTH_SHORT
                ).show()
                requireActivity().onBackPressed()
            }
        })

        viewModel.exception.observe(viewLifecycleOwner, Observer {
            Log.e(TAG, "onActivityCreated: ", it)
            Toast.makeText(requireContext(), it.localizedMessage, Toast.LENGTH_SHORT).show()
        })

    }

}