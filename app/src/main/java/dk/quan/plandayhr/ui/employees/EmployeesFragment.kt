package dk.quan.plandayhr.ui.employees

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import dk.quan.plandayhr.R
import dk.quan.plandayhr.data.models.EmployeesData
import dk.quan.plandayhr.extensions.hide
import dk.quan.plandayhr.extensions.show
import dk.quan.plandayhr.extensions.showSnackBar
import kotlinx.android.synthetic.main.employees_fragment.*
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.kodein
import org.kodein.di.generic.instance


class EmployeesFragment : Fragment(), AuthListener, KodeinAware,
    SwipeRefreshLayout.OnRefreshListener {

    override val kodein by kodein()
    private val factory: EmployeesViewModelFactory by instance()
    private lateinit var viewModel: EmployeesViewModel
    private lateinit var swipeRefreshLayout: SwipeRefreshLayout
    private lateinit var adapter: EmployeesAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.employees_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this, factory).get(EmployeesViewModel::class.java)
        viewModel.authListener = this
        swipeRefreshLayout = swipe_container
        swipeRefreshLayout.setOnRefreshListener(this)
        initRecyclerView()
        if (viewModel.isTokenValid()) {
            // Read the employees from cache or make a network request
            observeLiveData()
        }/* else {
            viewModel.authenticate()
        }*/
    }

    private fun initRecyclerView() {
        val navController = findNavController()
        adapter = EmployeesAdapter(object : ClickListeners {
            override fun onItemClicked(employee: EmployeesData, offset: Int) {
                val action =
                    EmployeesFragmentDirections.actionEmployeeEditFragment(employee.id, offset)
                navController.navigate(action)
            }
        })
        recyclerView.apply {
            layoutManager = LinearLayoutManager(requireActivity())
            setHasFixedSize(true)
            adapter = this@EmployeesFragment.adapter
            addItemDecoration(
                DividerItemDecoration(
                    requireContext(),
                    DividerItemDecoration.VERTICAL
                )
            )
        }
    }

    private fun observeLiveData() {
        viewModel.employeesPagedListLiveData.observe(viewLifecycleOwner, Observer {
            adapter.submitList(it)
        })
    }

    override fun onRefresh() {
        viewModel.invalidateEmployees()
        swipeRefreshLayout.isRefreshing = false
    }

    override fun onAuthStarted() {
        progress_bar.show()
    }

    override fun onAuthSuccess() {
        progress_bar.hide()
        // Read the employees from cache or make a network request
        observeLiveData()
    }

    override fun onAuthFailure(message: String) {
        progress_bar.hide()
        root_layout.showSnackBar(message)
    }
}