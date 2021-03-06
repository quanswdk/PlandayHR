package dk.quan.plandayhr.ui.employees

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import dk.quan.plandayhr.data.preferences.PreferenceProvider
import dk.quan.plandayhr.data.repositories.AuthRepository
import dk.quan.plandayhr.data.repositories.EmployeesRepository

@Suppress("UNCHECKED_CAST")
class EmployeesViewModelFactory(
    private val prefs: PreferenceProvider,
    private val authRepository: AuthRepository,
    private val employeesRepository: EmployeesRepository
) : ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return EmployeesViewModel(prefs, authRepository, employeesRepository) as T
    }
}