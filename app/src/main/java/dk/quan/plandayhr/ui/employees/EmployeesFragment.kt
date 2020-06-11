package dk.quan.plandayhr.ui.employees

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import dk.quan.plandayhr.R
import dk.quan.plandayhr.extensions.hide
import dk.quan.plandayhr.extensions.show
import dk.quan.plandayhr.extensions.showSnackBar
import kotlinx.android.synthetic.main.employees_fragment.*
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.kodein
import org.kodein.di.generic.instance

class EmployeesFragment : Fragment(), AuthListener, KodeinAware, SwipeRefreshLayout.OnRefreshListener {

    override val kodein by kodein()
    private val factory: EmployeesViewModelFactory by instance()
    private lateinit var viewModel: EmployeesViewModel
    private lateinit var swipeRefreshLayout: SwipeRefreshLayout

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
        recyclerView.apply {
            layoutManager = LinearLayoutManager(context)
            setHasFixedSize(true)
        }
        val adapter = EmployeesAdapter( object : ClickListeners {
            override fun onItemClicked(id: Int) {
                // Navigate to employee fragment read the employee with id
            }
        })
        recyclerView.adapter = adapter
/*
        if (viewModel.isTokenValid()) {
            // Read the employees from cache or make a network request
        } else {
            viewModel.authenticate()
        }
*/
    }

    override fun onRefresh() {
        swipeRefreshLayout.isRefreshing = false
        viewModel.authenticate()
    }

    override fun onStarted() {
        progress_bar.show()
    }

    override fun onSuccess() {
        progress_bar.hide()
    }

    override fun onFailure(message: String) {
        progress_bar.hide()
        root_layout.showSnackBar(message)
    }
}