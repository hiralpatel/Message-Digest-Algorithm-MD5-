package hpandro.java.infosec.md5.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.widget.Toast
import com.google.android.gms.ads.MobileAds
import com.google.firebase.FirebaseApp
import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import com.google.firebase.remoteconfig.FirebaseRemoteConfigSettings
import hpandro.java.infosec.md5.MainApp
import hpandro.java.infosec.md5.R

class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        checkConfig()
        MobileAds.initialize(
            applicationContext,
            getString(R.string.app_id)
        )
        Handler().postDelayed(
            {
                val i = Intent(this@SplashActivity, HomeActivity::class.java)
                startActivity(i)
                finish()
            }, 3 * 1000
        )
    }

    private fun checkConfig() {
        FirebaseApp.initializeApp(this@SplashActivity)
        var remoteConfig = FirebaseRemoteConfig.getInstance()
        FirebaseRemoteConfig.getInstance().apply {
            val configSettings = FirebaseRemoteConfigSettings.Builder()
                .setMinimumFetchIntervalInSeconds(0)
                .build()
            setConfigSettingsAsync(configSettings)

            setDefaultsAsync(R.xml.remote_config_defaults)
            fetchAndActivate().addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    MainApp.isDemoAvail = remoteConfig.getBoolean(MainApp.CONFIG_KEY_IS_DEMO_AVAILABLE)
//                    println("+++++++++++++ AppController.isDemoAvail: " + MainApp.isDemoAvail)
                    MainApp.sharedPreferencesApp.edit().putBoolean("isDemo",MainApp.isDemoAvail).apply()
                } else {
                    Toast.makeText(this@SplashActivity, "Something went wrong.", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}