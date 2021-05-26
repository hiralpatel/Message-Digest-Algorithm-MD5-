package hpandro.java.infosec.md5

import android.content.Context
import android.content.SharedPreferences
import androidx.multidex.MultiDexApplication
import hpandro.java.infosec.md5.util.SharedPrefsConst

class MainApp : MultiDexApplication() {

    companion object {
        val CONFIG_KEY_IS_DEMO_AVAILABLE = "HAS_MD5_DEMO"
        var isDemoAvail = false

        lateinit var ctx: Context
        lateinit var sharedPreferencesApp: SharedPreferences
    }

    override fun onCreate() {
        super.onCreate()
        ctx = applicationContext
        sharedPreferencesApp = this.getSharedPreferences(
            SharedPrefsConst.SHARED_PREFERENCE_FILE_NAME,
            Context.MODE_PRIVATE
        )
    }
}