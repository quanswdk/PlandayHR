package dk.quan.plandayhr.data.repositories

import dk.quan.plandayhr.data.PlandayApi
import dk.quan.plandayhr.data.SaveApiRequest
import dk.quan.plandayhr.data.models.Employee
import dk.quan.plandayhr.data.models.EmployeeData
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

    suspend fun getEmployee(id: Int): Employee {
        return apiRequest {
            api.getEmployee(CLIENT_ID, id)
        }
    }

    suspend fun putEmployee(id: Int, employeeData: EmployeeData) {
        return apiResponse {
            api.putEmployee(CLIENT_ID, id, employeeData)
        }
    }
}