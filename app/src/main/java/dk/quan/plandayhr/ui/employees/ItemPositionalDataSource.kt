package dk.quan.plandayhr.ui.employees

import android.util.Log
import androidx.paging.PositionalDataSource
import dk.quan.plandayhr.data.models.Employees
import dk.quan.plandayhr.data.models.EmployeesData
import dk.quan.plandayhr.data.repositories.EmployeesRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

class ItemPositionalDataSource(
    coroutineContext: CoroutineContext,
    employeesRepository: EmployeesRepository
) : PositionalDataSource<EmployeesData>() {

    val repo = employeesRepository
    private val job = Job()
    private val scope = CoroutineScope(coroutineContext + job)

    override fun loadInitial(
        params: LoadInitialParams,
        callback: LoadInitialCallback<EmployeesData>
    ) {
        scope.launch {
            try {
                val response = repo.getEmployees(params.requestedLoadSize)

                response.paging.limit = params.requestedLoadSize
                response.paging.offset = params.requestedStartPosition

                val count = response.paging.total
                val data = response.data

                callback.onResult(data, params.requestedStartPosition, count)
            } catch (exception: Exception) {
                Log.e("ItemDataSource", "Failed to fetch data! : " + exception.message)
            }
        }
    }

    override fun loadRange(params: LoadRangeParams, callback: LoadRangeCallback<EmployeesData>) {
        scope.launch {
            val response = repo.getEmployees(params.startPosition)

            response.paging.limit = params.loadSize
            response.paging.offset = params.startPosition

            val data = response.data

            callback.onResult(data)
        }
    }
}