package hpandro.java.infosec.md5.activity

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.view.GravityCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.MobileAds
import com.google.android.material.navigation.NavigationView
import hpandro.java.infosec.md5.MainApp
import hpandro.java.infosec.md5.R
import hpandro.java.infosec.md5.base.BaseActivity
import hpandro.java.infosec.md5.fragment.CTFSubmitFragment
import hpandro.java.infosec.md5.fragment.InfoFragment
import kotlinx.android.synthetic.main.activity_home.*
import kotlinx.android.synthetic.main.app_bar_main.*
import kotlinx.android.synthetic.main.content_main.*

class HomeActivity : BaseActivity(), NavigationView.OnNavigationItemSelectedListener {

    var fragmentManager: FragmentManager? = null
    var fragmentTransaction: FragmentTransaction? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        initBannerAds()
        init()
    }

    private fun initBannerAds() {
        MobileAds.initialize(this) {}
        val adRequest = AdRequest.Builder().build()
        adView.loadAd(adRequest)
    }

    private fun init() {
        setSupportActionBar(toolbar)
        title = getString(R.string.app_name)

        val toggle = ActionBarDrawerToggle(
            this, drawer_layout, toolbar, R.string.navigation_drawer_open,
            R.string.navigation_drawer_close
        )

        drawer_layout.addDrawerListener(toggle)
        toggle.syncState()
        navigationView.setNavigationItemSelectedListener(this)

        replaceFragment(
            InfoFragment(),
            false
        )
        initHintAds(resources.getString(R.string.ads_unit_award))
    }

    override fun onNavigationItemSelected(menuItem: MenuItem): Boolean {
        when (menuItem.itemId) {
            R.id.nav_menu_info -> {
                title = getString(R.string.app_name)
                replaceFragment(
                    InfoFragment(),
                    false
                )
            }
            R.id.nav_menu_hint -> {
                if (MainApp.sharedPreferencesApp.getBoolean("isDemo", MainApp.isDemoAvail)) {
                    if (mRewardedVideoAd!!.isLoaded)
                        mRewardedVideoAd!!.show()
                    else
                        initHintAds(resources.getString(R.string.ads_unit_award))
                } else {
                    Toast.makeText(
                        this, "Hint is not available yet.", Toast.LENGTH_SHORT
                    ).show()
                }
            }
            R.id.nav_menu_task -> {
                val mainIntent = Intent(this, TaskActivity::class.java)
                startActivity(mainIntent)
                overridePendingTransition(0, 0)
            }
            R.id.nav_menu_other_apps -> {
                val intent = Intent(Intent.ACTION_VIEW)
                intent.data = Uri.parse("https://play.google.com/store/apps/developer?id=hpAndro")
                startActivity(intent)
            }
            R.id.nav_menu_verify -> {
                title = "Verify Flag"
                replaceFragment(
                    CTFSubmitFragment(),
                    false
                )
            }
        }
        drawer_layout.closeDrawer(GravityCompat.START)
        return true
    }

    private fun replaceFragment(
        fragment: Fragment?, isBackStackEnable: Boolean,
    ) {
        if (fragment != null) {
            fragmentManager = supportFragmentManager
            fragmentTransaction = fragmentManager!!.beginTransaction()
            fragmentTransaction!!.replace(R.id.content_frame, fragment)
            if (isBackStackEnable) {
                fragmentTransaction!!.addToBackStack(null)
            }
            fragmentTransaction!!.commit()
            if (supportActionBar != null) supportActionBar!!.title = title
        }
    }

    override fun onBackPressed() {
        if (drawer_layout.isDrawerOpen(GravityCompat.START)) {
            drawer_layout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }
}