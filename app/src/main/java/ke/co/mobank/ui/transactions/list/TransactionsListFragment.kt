package ke.co.mobank.ui.transactions.list

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
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import ke.co.mobank.data.models.Transaction
import ke.co.mobank.databinding.TransactionsListFragmentBinding
import ke.co.mobank.ui.transactions.TransactionViewModel

private const val TAG = "TransactionsListFrag"

class TransactionsListFragment : Fragment(), TransactionsAdapter.TransactionItemClickListener {

    private lateinit var viewModel: TransactionViewModel
    private lateinit var binding: TransactionsListFragmentBinding
    private lateinit var navController: NavController

    private val transactionsAdapter: TransactionsAdapter by lazy { TransactionsAdapter(this) }

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

        val action = TransactionsListFragmentDirections.actionGetCustomerDetails()
        binding.addTransactionFab.setOnClickListener { navController.navigate(action) }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(TransactionViewModel::class.java)

        viewModel.readAllTransactions()

        viewModel.transactions.observe(viewLifecycleOwner, Observer { transactions ->
            transactionsAdapter.transactions = transactions
        })

        viewModel.exception.observe(viewLifecycleOwner, Observer {
            Log.e(TAG, "onActivityCreated: ", it)
            Toast.makeText(requireContext(), it.localizedMessage, Toast.LENGTH_SHORT).show()
        })
    }

    override fun onItemClick(transaction: Transaction) {
        val action = TransactionsListFragmentDirections.actionViewTransactionDetails(transaction)
        navController.navigate(action)
    }

}