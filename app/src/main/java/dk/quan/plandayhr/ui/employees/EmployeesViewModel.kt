package dk.quan.plandayhr.ui.employees

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dk.quan.plandayhr.data.preferences.PreferenceProvider
import dk.quan.plandayhr.data.preferences.PreferenceProvider.Companion.TOKEN
import dk.quan.plandayhr.data.preferences.PreferenceProvider.Companion.TOKEN_EXPIRES_AT
import dk.quan.plandayhr.data.repositories.AuthRepository
import dk.quan.plandayhr.util.ApiException
import dk.quan.plandayhr.util.NoInternetException
import kotlinx.coroutines.launch
import java.util.*

class EmployeesViewModel(
    private val prefs: PreferenceProvider,
    private val authRepository: AuthRepository
) : ViewModel() {

    init {
        if (!isTokenValid()) authenticate()
    }

    var authListener: AuthListener? = null

    fun isTokenValid(): Boolean {
        return prefs.get(TOKEN, null) != null && prefs.get(
            TOKEN_EXPIRES_AT,
            0L
        ) < Calendar.getInstance().timeInMillis
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
}