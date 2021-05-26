package hpandro.java.infosec.md5.base

import android.content.Intent
import android.content.SharedPreferences
import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.MobileAds
import com.google.android.gms.ads.reward.RewardItem
import com.google.android.gms.ads.reward.RewardedVideoAd
import com.google.android.gms.ads.reward.RewardedVideoAdListener
import hpandro.java.infosec.md5.MainApp

open class BaseActivity : AppCompatActivity() {
    private var type: String? = ""
    var isRewarded = false
    lateinit var sharedPreferences: SharedPreferences
    var mRewardedVideoAd: RewardedVideoAd? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sharedPreferences = MainApp.sharedPreferencesApp
    }

    fun initHintAds(adUnit: String) {
        mRewardedVideoAd = MobileAds.getRewardedVideoAdInstance(this)
        mRewardedVideoAd!!.loadAd(
            adUnit, AdRequest.Builder().build()
        )

        mRewardedVideoAd!!.rewardedVideoAdListener = object : RewardedVideoAdListener {
            override fun onRewardedVideoAdLoaded() {
//                Toast.makeText(
//                    this@BaseActivity,
//                    "Ad loaded.", Toast.LENGTH_SHORT
//                ).show()
//                println("++++++++++++ onRewardedVideoAdLoaded")
//                mRewardedVideoAd!!.show()
            }

            override fun onRewardedVideoAdOpened() {
//                Toast.makeText(
//                    this@BaseActivity,
//                    "Ad opened.", Toast.LENGTH_SHORT
//                ).show()
//                println("++++++++++++ onRewardedVideoAdOpened")
            }

            override fun onRewardedVideoStarted() {
//                Toast.makeText(
//                    this@BaseActivity,
//                    "Ad started.", Toast.LENGTH_SHORT
//                ).show()
//                println("++++++++++++ onRewardedVideoStarted")
            }

            override fun onRewardedVideoAdClosed() {
//                Toast.makeText(
//                    this@BaseActivity,
//                    "Ad closed.", Toast.LENGTH_SHORT
//                ).show()
//                println("++++++++++++ onRewardedVideoAdClosed")
//                loadAds()
                if (isRewarded && type!!.isNotEmpty()) {
                    isRewarded = false
                    val intent = Intent(Intent.ACTION_VIEW)
                    intent.data = Uri.parse(type)
                    intent.setPackage("com.google.android.youtube")
                    startActivity(intent)
                    mRewardedVideoAd!!.destroy()
                    initHintAds(resources.getString(hpandro.java.infosec.md5.R.string.ads_unit_award))
                }
                //TODO if rewarded play video
            }

            override fun onRewarded(rewardItem: RewardItem?) {
//                Toast.makeText(
//                    this@BaseActivity,
//                    "Ad triggered reward.", Toast.LENGTH_SHORT
//                ).show()
//                println("++++++++++++ onRewarded")
                isRewarded = true
                //TODO get URL here
//                println("+++++++++++++++ rewardItem!!.amount: " + rewardItem!!.amount)
//                println("+++++++++++++++ rewardItem!!.type: " + rewardItem!!.type)
                type = rewardItem!!.type
            }

            override fun onRewardedVideoAdLeftApplication() {
//                Toast.makeText(
//                    this@BaseActivity,
//                    "Ad left application.", Toast.LENGTH_SHORT
//                ).show()
//                println("++++++++++++ onRewardedVideoAdLeftApplication")
            }

            override fun onRewardedVideoAdFailedToLoad(i: Int) {
//                Toast.makeText(
//                    this@BaseActivity,
//                    "Ad failed to load.", Toast.LENGTH_SHORT
//                ).show()
//                println("++++++++++++ onRewardedVideoAdFailedToLoad")
            }

            override fun onRewardedVideoCompleted() {
//                Toast.makeText(
//                    this@BaseActivity,
//                    "Ad completed.", Toast.LENGTH_SHORT
//                ).show()
//                println("++++++++++++ onRewardedVideoCompleted")
                //TODO get completed
//                if (isRewarded) {
//                    isRewarded = false
//                    val intent = Intent(Intent.ACTION_VIEW)
//                    intent.data = Uri.parse(type)
//                    intent.setPackage("com.google.android.youtube")
//                    startActivity(intent)
//                    mRewardedVideoAd!!.destroy()
//                }
//                initHintAds(resources.getString(hpandro.java.infosec.des.R.string.ads_unit_award))
            }
        }
    }

    override fun onResume() {
        mRewardedVideoAd?.resume(this)
        super.onResume()
    }

    override fun onPause() {
        mRewardedVideoAd?.pause(this)
        super.onPause()
    }

    override fun onDestroy() {
        mRewardedVideoAd?.destroy(this)
        super.onDestroy()
    }
}