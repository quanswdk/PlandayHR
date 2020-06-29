package dk.quan.plandayhr.ui.employees

interface AuthListener {
    fun onAuthStarted()
    fun onAuthSuccess()
    fun onAuthFailure(message: String)
}