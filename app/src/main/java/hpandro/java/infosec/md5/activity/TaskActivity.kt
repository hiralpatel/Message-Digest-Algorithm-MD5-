package hpandro.java.infosec.md5.activity

import android.annotation.SuppressLint
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.ads.nativetemplates.NativeTemplateStyle
import com.google.android.gms.ads.*
import com.google.android.gms.ads.formats.UnifiedNativeAd
import com.google.gson.JsonObject
import hpandro.java.infosec.md5.R
import hpandro.java.infosec.md5.presenter.TaskPresenter
import hpandro.java.infosec.md5.presenter.TaskPresenterCallback
import hpandro.java.infosec.md5.util.DataBaseHandler
import hpandro.java.infosec.md5.util.TABLEMD5NAME
import kotlinx.android.synthetic.main.activity_task.*
import java.security.*

class TaskActivity : AppCompatActivity(), TaskPresenterCallback {

    private lateinit var presenter: TaskPresenter
    private var adLoader: AdLoader? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_task)

        init()
        loadAds()
    }

    private fun loadAds() {
        MobileAds.initialize(this) { }
        adLoader = AdLoader.Builder(this, getString(R.string.ads_native))
            .forNativeAd {
            }
            .forUnifiedNativeAd(object : UnifiedNativeAd.OnUnifiedNativeAdLoadedListener {
                private val background: ColorDrawable? = null
                override fun onUnifiedNativeAdLoaded(unifiedNativeAd: UnifiedNativeAd?) {
                    val styles =
                        NativeTemplateStyle.Builder().withMainBackgroundColor(background).build()
                    nativeTemplateView.setStyles(styles)
                    nativeTemplateView.setNativeAd(unifiedNativeAd)

                    nativeTemplateView.visibility = View.VISIBLE
                }
            }).build()
        val adRequest: AdRequest = AdRequest.Builder().build()
        adLoader!!.loadAd(adRequest)
    }

    private fun init() {
        toolbarTask.title = getString(R.string.app_name) + " Task"
        toolbarTask.setNavigationOnClickListener { finish() }

        presenter = TaskPresenter(this@TaskActivity)

        btnCheckFlag.setOnClickListener {
            progress.visibility = View.VISIBLE
            presenter.getHashFlag("md5")
        }
    }

    @SuppressLint("NewApi", "SetTextI18n")
    override fun onGetLogs(response: JsonObject) {
        progress.visibility = View.GONE
        tvFlag.text = "Task is to deHash hash and create flag from the SQLite Database \n\n" + response.asJsonObject.get("flag").asString.trim()
        val db = DataBaseHandler(this@TaskActivity)
        db.insertHashData(
            TABLEMD5NAME,
            response.asJsonObject.get("HASH1").asString,
            response.asJsonObject.get("HASH2").asString,
            response.asJsonObject.get("HASH3").asString,
            response.asJsonObject.get("HASH4").asString,
            response.asJsonObject.get("HASH5").asString,
            response.asJsonObject.get("HASH6").asString,
            response.asJsonObject.get("HASH7").asString,
            response.asJsonObject.get("HASH8").asString
        )
        Toast.makeText(
            this@TaskActivity,
            "MD5 Hash received successfully.",
            Toast.LENGTH_LONG
        ).show()
    }

    override fun onError(t: Throwable) {
        progress.visibility = View.GONE
        Toast.makeText(
            this@TaskActivity,
            "Something went wrong!",
            Toast.LENGTH_LONG
        ).show()
    }
}