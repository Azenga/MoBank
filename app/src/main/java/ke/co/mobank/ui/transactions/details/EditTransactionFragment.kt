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
import ke.co.mobank.databinding.FragmentEditTransactionBinding
import ke.co.mobank.ui.transactions.TransactionViewModel

private const val TAG = "EditTransactionFrag"

class EditTransactionFragment : Fragment() {

    private lateinit var binding: FragmentEditTransactionBinding
    private lateinit var navController: NavController
    private lateinit var viewModel: TransactionViewModel
    private lateinit var transaction: Transaction

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        transaction = EditTransactionFragmentArgs.fromBundle(requireArguments()).transaction
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentEditTransactionBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        navController = Navigation.findNavController(view)

        binding.customerContactField.setText(transaction.customerContact)
        binding.customerNameField.setText(transaction.customerName)
        binding.transactionAmountField.setText(transaction.transactionAmount.toString())
        binding.transactionTypeField.setText(transaction.transactionType)
        binding.transactionPlatformField.setText(transaction.transactionPlatform)

        binding.updateTransaction.setOnClickListener {
            try {
                val name = binding.customerNameField.text.toString()
                val contact = binding.customerContactField.text.toString()
                val amount = binding.transactionAmountField.text.toString().toDouble()
                val type = binding.transactionTypeField.text.toString()
                val platform = binding.transactionPlatformField.text.toString()

                if (name.isBlank()) {
                    binding.customerNameField.error = "Customer Name is required"
                    return@setOnClickListener
                }

                if (contact.isBlank()) {
                    binding.customerContactField.error = "Customer Contact is required"
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

                transaction.transactionAmount = amount
                transaction.transactionType = type
                transaction.transactionPlatform = platform
                transaction.customerName = name
                transaction.customerName = contact

                viewModel.updateTransaction(transaction)

            } catch (exception: NumberFormatException) {
                Log.e(TAG, "onViewCreated: edit transaction validation: ", exception)
            }
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        viewModel = ViewModelProvider(this).get(TransactionViewModel::class.java)

        viewModel.transaction.observe(viewLifecycleOwner, Observer {
            Toast.makeText(requireContext(), "Transaction Updated Successfully", Toast.LENGTH_SHORT)
                .show()
            requireActivity().onBackPressed()
        })

        viewModel.exception.observe(viewLifecycleOwner, Observer {
            Log.e(TAG, "onActivityCreated: ", it)
            Toast.makeText(requireContext(), it.localizedMessage, Toast.LENGTH_SHORT).show()
        })
    }

}