package ke.co.mobank.ui.transactions.list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import ke.co.mobank.R
import ke.co.mobank.databinding.TransactionsListFragmentBinding
import ke.co.mobank.ui.transactions.TransactionViewModel

class TransactionsListFragment : Fragment() {

    private lateinit var viewModel: TransactionViewModel
    private lateinit var binding: TransactionsListFragmentBinding
    private lateinit var navController: NavController

    private val transactionsAdapter: TransactionsAdapter by lazy { TransactionsAdapter() }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = TransactionsListFragmentBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        navController = Navigation.findNavController(view)

        //Setup the recyclerview
        binding.transactionsRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.transactionsRecyclerView.addItemDecoration(
            DividerItemDecoration(
                requireContext(),
                DividerItemDecoration.VERTICAL
            )
        )
        binding.transactionsRecyclerView.adapter = transactionsAdapter

        binding.addTransactionFab.setOnClickListener { navController.navigate(R.id.action_get_customer_details) }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(TransactionViewModel::class.java)

        viewModel.readAllTransactions()

        viewModel.transactions.observe(viewLifecycleOwner, Observer { transactions ->
            transactionsAdapter.transactions = transactions
        })
    }

}