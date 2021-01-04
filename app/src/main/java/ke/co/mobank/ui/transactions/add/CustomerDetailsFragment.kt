package ke.co.mobank.ui.transactions.add

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.Navigation
import ke.co.mobank.data.models.Transaction
import ke.co.mobank.databinding.FragmentCustomerDetailsBinding

class CustomerDetailsFragment : Fragment() {

    private lateinit var binding: FragmentCustomerDetailsBinding

    private lateinit var navController: NavController

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentCustomerDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        navController = Navigation.findNavController(view)

        binding.continueButton.setOnClickListener {
            val name = binding.customerNameField.text.toString()
            val contact = binding.customerContactField.text.toString()

            if (name.isBlank()) {
                binding.customerNameField.error = "Customer Name is required"
                return@setOnClickListener
            }

            if (contact.isBlank()) {
                binding.customerContactField.error = "Customer Contact is required"
                return@setOnClickListener
            }

            val transaction = Transaction(customerName = name, customerContact = contact)

            val action = CustomerDetailsFragmentDirections.actionGetTransactionDetails(transaction)

            navController.navigate(action)
        }
    }
}