package ke.co.mobank.ui.transactions.add

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.navigation.NavController
import androidx.navigation.Navigation
import ke.co.mobank.R
import ke.co.mobank.databinding.FragmentTransactionDetailsBinding
import ke.co.mobank.internal.Constants

class TransactionDetailsFragment : Fragment() {

    private lateinit var binding: FragmentTransactionDetailsBinding
    private lateinit var navController: NavController

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



        binding.finishButton.setOnClickListener { navController.navigate(R.id.action_transaction_stored) }
    }
}