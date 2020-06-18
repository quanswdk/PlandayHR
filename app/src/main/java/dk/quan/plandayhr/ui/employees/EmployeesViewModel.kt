package dk.quan.plandayhr.ui.employees

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.DataSource
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import dk.quan.plandayhr.data.models.Employees
import dk.quan.plandayhr.data.models.EmployeesData
import dk.quan.plandayhr.data.preferences.PreferenceProvider
import dk.quan.plandayhr.data.preferences.PreferenceProvider.Companion.TOKEN
import dk.quan.plandayhr.data.preferences.PreferenceProvider.Companion.TOKEN_EXPIRES_AT
import dk.quan.plandayhr.data.repositories.AuthRepository
import dk.quan.plandayhr.data.repositories.EmployeesRepository
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

    //lateinit var employees :MutableLiveData<PagedList<Employees>>
    val employeesPagedListLiveData = initializedPagedListLiveData()
    private var itemPositionalDataSource: ItemPositionalDataSource? = null
/*
    val employees: LiveData<PagedList<Employees>>
        get() = _employees
*/
    //lateinit var employeesLiveData: LiveData<PagedList<EmployeesData>>

    init {
        if (!isTokenValid()) authenticate() else {
            //getEmployees()
/*
            val pagedListConfig =
                PagedList.Config.Builder().setEnablePlaceholders(true)
                    .setPrefetchDistance(5)
                    .setInitialLoadSizeHint(50)
                    .setPageSize(5).build()
            employees = initializedPagedListBuilder(pagedListConfig).build()
*/
            //_employees.value = initializedPagedListBuilder(pagedListConfig).build()
        }
    }

    var authListener: AuthListener? = null

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
        authListener?.onStarted()
        viewModelScope.launch {
            try {
                val token = authRepository.authenticate()
                prefs.put(TOKEN, token.accessToken)
                prefs.put(
                    TOKEN_EXPIRES_AT,
                    Calendar.getInstance().timeInMillis + (token.expiresIn * 1000)
                )
                Log.d("carhauge", "TOKEN_EXPIRES_AT: " + token.expiresIn)
                authListener?.onSuccess()
            } catch (e: ApiException) {
                e.message?.let { authListener?.onFailure(it) }
            } catch (e: NoInternetException) {
                e.message?.let { authListener?.onFailure(it) }
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

    //fun getEmployees(): LiveData<PagedList<EmployeesData>> = employeesLiveData

/*
    fun getEmployees(offset: Int = 0) {
        viewModelScope.launch(Dispatchers.IO) {
            employees.postValue(employeesRepository.getEmployees(offset))
        }
    }
*/
}