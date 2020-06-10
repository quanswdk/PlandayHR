package dk.quan.plandayhr

import android.app.Application
import dk.quan.plandayhr.data.AuthInterceptor
import dk.quan.plandayhr.data.NetworkConnectionInterceptor
import dk.quan.plandayhr.data.PlandayAuthApi
import dk.quan.plandayhr.data.preferences.PreferenceProvider
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.androidXModule
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.singleton

class PlandayHrApp() : Application(), KodeinAware {
    override val kodein = Kodein.lazy {
        import(androidXModule(this@PlandayHrApp))

        bind() from singleton { PreferenceProvider(instance()) }
        bind() from singleton { NetworkConnectionInterceptor(instance()) }
        bind() from singleton { PlandayAuthApi(instance()) }
        bind() from singleton { AuthInterceptor(instance()) }
    }
}