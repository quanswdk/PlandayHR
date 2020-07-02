package dk.quan.plandayhr.data.models

import com.google.gson.annotations.SerializedName

class EmployeeData(
    @SerializedName("id")
    var id: Int,
    @SerializedName("gender")
    var gender: String,
    @SerializedName("firstName")
    var firstName: String,
    @SerializedName("lastName")
    var lastName: String
)

data class Employee(
    @SerializedName("data")
    var data: EmployeeData
)
