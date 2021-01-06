package ke.co.mobank.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.Navigation
import ke.co.mobank.databinding.FragmentHomeBinding

class HomeFragment : Fragment(), View.OnClickListener {

    private lateinit var binding: FragmentHomeBinding
    private lateinit var navController: NavController

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        navController = Navigation.findNavController(view)

        binding.mpesaTextView.setOnClickListener(this)
        binding.kcbTextView.setOnClickListener(this)
        binding.airtelTextView.setOnClickListener(this)
        binding.equityTextView.setOnClickListener(this)
        binding.coopTextView.setOnClickListener(this)
        binding.telcomTextView.setOnClickListener(this)
    }

    override fun onClick(v: View?) {

        val action = when (v) {
            binding.mpesaTextView -> HomeFragmentDirections.actionBrowseTransactions("M-Pesa")
            binding.airtelTextView -> HomeFragmentDirections.actionBrowseTransactions("Airtel")
            binding.telcomTextView -> HomeFragmentDirections.actionBrowseTransactions("Telecom")
            binding.equityTextView -> HomeFragmentDirections.actionBrowseTransactions("Equity")
            binding.coopTextView -> HomeFragmentDirections.actionBrowseTransactions("Cooperative")
            binding.kcbTextView -> HomeFragmentDirections.actionBrowseTransactions("KCB")
            else -> HomeFragmentDirections.actionBrowseTransactions()
        }

        navController.navigate(action)

    }
}