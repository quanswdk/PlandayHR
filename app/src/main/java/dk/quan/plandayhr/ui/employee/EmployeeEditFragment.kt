package dk.quan.plandayhr.ui.employee

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import dk.quan.plandayhr.R
import dk.quan.plandayhr.databinding.EmployeeEditFragmentBinding
import dk.quan.plandayhr.extensions.hide
import dk.quan.plandayhr.extensions.show
import dk.quan.plandayhr.extensions.showSnackBar
import dk.quan.plandayhr.ui.PlandayApiListener
import dk.quan.plandayhr.ui.employees.EmployeesViewModel
import dk.quan.plandayhr.ui.employees.EmployeesViewModelFactory
import kotlinx.android.synthetic.main.employee_edit_fragment.*
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.kodein
import org.kodein.di.generic.instance

class EmployeeEditFragment : Fragment(), PlandayApiListener, KodeinAware {

    override val kodein by kodein()
    private val factory: EmployeesViewModelFactory by instance()
    private lateinit var viewModel: EmployeesViewModel
    private lateinit var binding: EmployeeEditFragmentBinding
    val args: EmployeeEditFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding =
            DataBindingUtil.inflate(inflater, R.layout.employee_edit_fragment, container, false)
        viewModel = ViewModelProvider(this, factory).get(EmployeesViewModel::class.java)
        viewModel.plandayApiListener = this
        binding.viewModel = viewModel
        binding.lifecycleOwner = this
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel.getEmployee(args.id)
    }

    override fun onPlandayApiStarted() {
        progress_bar.show()
    }

    override fun onPlandayApiSuccess() {
        progress_bar.hide()
    }

    override fun onPlandayApiFailure(message: String) {
        progress_bar.hide()
        root_layout.showSnackBar(message)
    }
}