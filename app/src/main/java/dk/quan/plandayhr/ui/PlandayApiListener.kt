package dk.quan.plandayhr.ui

interface PlandayApiListener {
    fun onPlandayApiStarted()
    fun onPlandayApiSuccess()
    fun onPlandayApiFailure(message: String)
}