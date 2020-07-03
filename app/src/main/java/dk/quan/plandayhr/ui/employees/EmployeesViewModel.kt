package dk.quan.plandayhr.ui.employees

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.DataSource
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import dk.quan.plandayhr.data.models.EmployeeData
import dk.quan.plandayhr.data.models.EmployeesData
import dk.quan.plandayhr.data.preferences.PreferenceProvider
import dk.quan.plandayhr.data.preferences.PreferenceProvider.Companion.TOKEN
import dk.quan.plandayhr.data.preferences.PreferenceProvider.Companion.TOKEN_EXPIRES_AT
import dk.quan.plandayhr.data.repositories.AuthRepository
import dk.quan.plandayhr.data.repositories.EmployeesRepository
import dk.quan.plandayhr.ui.PlandayApiListener
import dk.quan.plandayhr.util.ApiException
import dk.quan.plandayhr.util.NoInternetException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.*

class EmployeesViewModel(
    private val prefs: PreferenceProvider,
    private val authRepository: AuthRepository,
    private val employeesRepository: EmployeesRepository
) : ViewModel() {

    val employeesPagedListLiveData = initializedPagedListLiveData()
    private var itemPositionalDataSource: ItemPositionalDataSource? = null

    private val _employee = MutableLiveData<EmployeeData>()
    val employee: LiveData<EmployeeData>
        get() = _employee

    init {
        if (!isTokenValid()) authenticate()
    }

    var authListener: AuthListener? = null
    var plandayApiListener: PlandayApiListener? = null

    fun isTokenValid(): Boolean {
/*
        Log.d("carhauge", "TOKEN: " + prefs.get(TOKEN, null))
        Log.d("carhauge", "TOKEN_EXPIRES_AT: " + prefs.get(
            TOKEN_EXPIRES_AT,
            0L
        ))
        Log.d("carhauge", "timeInMillis    : " + Calendar.getInstance().timeInMillis)
*/
        return prefs.get(TOKEN, null) != null && prefs.get(
            TOKEN_EXPIRES_AT,
            0L
        ) > Calendar.getInstance().timeInMillis
    }

    fun authenticate() {
        authListener?.onAuthStarted()
        viewModelScope.launch {
            try {
                val token = authRepository.authenticate()
                prefs.put(TOKEN, token.accessToken)
                prefs.put(
                    TOKEN_EXPIRES_AT,
                    Calendar.getInstance().timeInMillis + (token.expiresIn * 1000)
                )
                Log.d("carhauge", "TOKEN_EXPIRES_AT: " + token.expiresIn)
                authListener?.onAuthSuccess()
            } catch (e: ApiException) {
                e.message?.let { authListener?.onAuthFailure(it) }
            } catch (e: NoInternetException) {
                e.message?.let { authListener?.onAuthFailure(it) }
            }
        }
    }

    fun getEmployees(offset: Int) {
        viewModelScope.launch {
            try {
                val employees = employeesRepository.getEmployees(offset)
                invalidateEmployees()
            } catch (exception: Exception) {
                Log.e("getEmployees", "Failed to fetch data! : " + exception.message)
            }
        }
    }

    fun invalidateEmployees() {
        itemPositionalDataSource?.invalidate()
    }

    fun getEmployee(id: Int) {
        plandayApiListener?.onPlandayApiStarted()
        viewModelScope.launch {
            try {
                val employee = employeesRepository.getEmployee(id)
                _employee.value = employee.data
                plandayApiListener?.onPlandayApiSuccess()
            } catch (e: ApiException) {
                e.message?.let { plandayApiListener?.onPlandayApiFailure(it) }
            } catch (e: NoInternetException) {
                e.message?.let { plandayApiListener?.onPlandayApiFailure(it) }
            }
        }
    }

    fun onUpdateButtonClick() {
        plandayApiListener?.onPlandayApiStarted()
        viewModelScope.launch {
            try {
                employeesRepository.putEmployee(employee.value!!.id, employee.value!!)
                plandayApiListener?.onPlandayApiSuccess()
            } catch (e: ApiException) {
                e.message?.let { plandayApiListener?.onPlandayApiFailure(it) }
            } catch (e: NoInternetException) {
                e.message?.let { plandayApiListener?.onPlandayApiFailure(it) }
            }
        }
    }

    private fun initializedPagedListLiveData(): LiveData<PagedList<EmployeesData>> {

        val config =
            PagedList.Config.Builder()
                .setEnablePlaceholders(true)
                .setPrefetchDistance(5)
                .setInitialLoadSizeHint(50)
                .setPageSize(50)
                .build()

        val dataSource = object : DataSource.Factory<Int, EmployeesData>() {
            override fun create(): ItemPositionalDataSource {
                return ItemPositionalDataSource(
                    Dispatchers.IO,
                    employeesRepository
                ).also {
                    itemPositionalDataSource = it
                }
            }
        }
        return LivePagedListBuilder(dataSource, config).build()
    }
}