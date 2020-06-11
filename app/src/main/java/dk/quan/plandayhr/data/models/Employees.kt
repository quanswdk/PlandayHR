package dk.quan.plandayhr.data.models

import com.google.gson.annotations.SerializedName

class Paging(
    @SerializedName("offset")
    var offset: Int,
    @SerializedName("limit")
    var limit: Int,
    @SerializedName("total")
    var total: Int
)

class EmployeesData(
    @SerializedName("id")
    var id: Int,
    @SerializedName("dateTimeCreated")
    var dateTimeCreated: String,
    @SerializedName("employeeTypeId")
    var employeeTypeId: Int,
    @SerializedName("salaryIdentifier")
    var salaryIdentifier: String,
    @SerializedName("firstName")
    var firstName: String,
    @SerializedName("lastName")
    var lastName: String,
    @SerializedName("userName")
    var userName: String,
    @SerializedName("cellPhone")
    var cellPhone: String,
    @SerializedName("street1")
    var street1: String,
    @SerializedName("zip")
    var zip: String,
    @SerializedName("phone")
    var phone: String,
    @SerializedName("city")
    var city: String,
    @SerializedName("email")
    var email: String,
    @SerializedName("departments")
    var departments: List<Int>,
    @SerializedName("employeeGroups")
    var employeeGroups: List<Int>
)

data class Employees(
    @SerializedName("paging")
    var paging: Paging,
    @SerializedName("data")
    var data: List<EmployeesData>
)