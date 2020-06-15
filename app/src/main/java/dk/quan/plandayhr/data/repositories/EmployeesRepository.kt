package dk.quan.plandayhr.data.repositories

import dk.quan.plandayhr.data.PlandayApi
import dk.quan.plandayhr.data.SaveApiRequest
import dk.quan.plandayhr.data.models.Employees
import dk.quan.plandayhr.util.Constants.CLIENT_ID

class EmployeesRepository(
    private val api: PlandayApi
) : SaveApiRequest() {

    suspend fun getEmployees(offset: Int): Employees {
        return apiRequest {
            api.getEmployees(CLIENT_ID, offset)
        }
    }
}