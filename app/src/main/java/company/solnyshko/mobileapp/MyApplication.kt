package company.solnyshko.mobileapp

import android.app.Application
import io.realm.Realm
import io.realm.RealmConfiguration

class MyApplication: Application() {
    override fun onCreate() {
        super.onCreate()
        Realm.init(this)
        val mRealmConfiguration = RealmConfiguration.Builder()
                .name("myDB.realm")
                .schemaVersion(1) // skip if you are not managing
                .deleteRealmIfMigrationNeeded()
                .build()
        Realm.getInstance(mRealmConfiguration)
        Realm.setDefaultConfiguration(mRealmConfiguration)
    }
}