package dk.quan.plandayhr.ui.employees

interface AuthListener {
    fun onStarted()
    fun onSuccess()
    fun onFailure(message: String)
}