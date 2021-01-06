package ke.co.mobank.ui.transactions.add

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.Navigation
import ke.co.mobank.R
import ke.co.mobank.data.models.Transaction
import ke.co.mobank.databinding.FragmentTransactionDetailsBinding
import ke.co.mobank.internal.Constants
import ke.co.mobank.ui.transactions.TransactionViewModel

private const val TAG = "TransactionDetailsFrag"

class TransactionDetailsFragment : Fragment() {

    private lateinit var binding: FragmentTransactionDetailsBinding
    private lateinit var viewModel: TransactionViewModel
    private lateinit var navController: NavController

    private lateinit var transaction: Transaction

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        transaction = TransactionDetailsFragmentArgs.fromBundle(requireArguments()).transaction
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentTransactionDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val typeAdapter = ArrayAdapter<String>(
            requireContext(),
            R.layout.dropdown_menu_popup_item,
            Constants.TRANSACTION_TYPES
        )
        binding.transactionTypeField.setAdapter(typeAdapter)

        val platformAdapter = ArrayAdapter<String>(
            requireContext(),
            R.layout.dropdown_menu_popup_item,
            Constants.PLATFORMS
        )
        binding.transactionPlatformField.setAdapter(platformAdapter)

        navController = Navigation.findNavController(view)



        binding.finishButton.setOnClickListener {

            try {

                val id = binding.transactionIdField.text.toString()
                val amount = binding.transactionAmountField.text.toString().toDouble()
                val type = binding.transactionTypeField.text.toString()
                val platform = binding.transactionPlatformField.text.toString()

                if (id.isBlank()) {
                    binding.transactionIdField.error = "Transaction ID is required"
                    return@setOnClickListener
                }

                if (type.isBlank()) {
                    binding.transactionTypeField.error = "Transaction Type is required"
                    return@setOnClickListener
                }

                if (platform.isBlank()) {
                    binding.transactionPlatformField.error = "Transaction Platform is required"
                    return@setOnClickListener
                }

                transaction.transactionId = id
                transaction.transactionAmount = amount
                transaction.transactionType = type
                transaction.transactionPlatform = platform

                viewModel.addTransaction(transaction)

            } catch (exception: NumberFormatException) {
                Log.e(TAG, "onViewCreated: transaction details validation: ", exception)
            }

        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        viewModel = ViewModelProvider(this).get(TransactionViewModel::class.java)

        viewModel.transaction.observe(
            viewLifecycleOwner,
            Observer { transaction ->
                Log.i(TAG, "onViewCreated: $transaction");
                Toast.makeText(requireContext(), "Transaction Added", Toast.LENGTH_SHORT).show()
                navController.navigate(R.id.action_transaction_stored)
            })
        viewModel.exception.observe(viewLifecycleOwner, Observer {
            Log.e(TAG, "onActivityCreated: ", it)
            Toast.makeText(requireContext(), it.localizedMessage, Toast.LENGTH_SHORT).show()
        })
    }
}