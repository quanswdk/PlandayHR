package dk.quan.plandayhr.data.models

import com.google.gson.annotations.SerializedName

data class Employee(
    @SerializedName("id")
    var id: Int,
    @SerializedName("hiredFrom")
    var hiredFrom: String,
    @SerializedName("deactivationDate")
    var deactivationDate: String,
    @SerializedName("salaryIdentifier")
    var salaryIdentifier: String,
    @SerializedName("gender")
    var gender: String,
    @SerializedName("jobtitle")
    var jobtitle: String,
    @SerializedName("employeeTypeId")
    var employeeTypeId: Int,
    @SerializedName("primaryDepartmentId")
    var primaryDepartmentId: Int,
    @SerializedName("dateTimeCreated")
    var dateTimeCreated: String,
    @SerializedName("dateTimeModified")
    var dateTimeModified: String,
    @SerializedName("firstName")
    var firstName: String,
    @SerializedName("lastName")
    var lastName: String,
    @SerializedName("userName")
    var userName: String,
    @SerializedName("cellPhone")
    var cellPhone: String,
    @SerializedName("cellPhoneCountryCode")
    var cellPhoneCountryCode: String,
    @SerializedName("street1")
    var street1: String,
    @SerializedName("zip")
    var zip: String,
    @SerializedName("phone")
    var phone: String,
    @SerializedName("phoneCountryCode")
    var phoneCountryCode: String,
    @SerializedName("city")
    var city: String,
    @SerializedName("email")
    var email: String,
    @SerializedName("departments")
    var departments: List<Int>,
    @SerializedName("employeeGroups")
    var employeeGroups: List<Int>
)