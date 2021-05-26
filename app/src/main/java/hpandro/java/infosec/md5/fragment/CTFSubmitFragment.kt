package hpandro.java.infosec.md5.fragment

import android.graphics.Bitmap
import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebChromeClient
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.RelativeLayout
import com.bumptech.glide.Glide
import hpandro.java.infosec.md5.AppUtils
import hpandro.java.infosec.md5.R

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [CTFSubmitFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class CTFSubmitFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    private var relInternet: RelativeLayout? = null
//    private var mRewardedVideoAd: RewardedVideoAd? = null

    private var ivNoInternet: ImageView? = null
    private var mProgreeBar: ProgressBar? = null
    private var mWebView: WebView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_info, container, false)
        init(root)
        return root
    }

    private fun init(root: View) {
        relInternet = root.findViewById(R.id.relInternet) as RelativeLayout
        ivNoInternet = root.findViewById(R.id.ivNoInternet) as ImageView
        mProgreeBar = root.findViewById(R.id.mProgressBar) as ProgressBar
        mWebView = root.findViewById(R.id.mWebView) as WebView

        webViewSetting()

        mWebView!!.webViewClient = object : WebViewClient() {
            override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
                super.onPageStarted(view, url, favicon)
                mProgreeBar!!.visibility = View.VISIBLE
            }

            override fun shouldOverrideUrlLoading(view: WebView?, url: String?): Boolean {
                view?.loadUrl(url!!)
                return true
            }

            override fun onPageFinished(view: WebView?, url: String?) {
                super.onPageFinished(view, url)
                mProgreeBar!!.visibility = View.GONE
            }
        }
        mWebView!!.webChromeClient = object : WebChromeClient() {
            override fun onProgressChanged(view: WebView, newProgress: Int) {
                super.onProgressChanged(view, newProgress)
                // Your custom code.
            }
        }

        if (AppUtils.isOnline(activity)) {
            mWebView!!.loadUrl("http://ctf.hpandro.raviramesh.info/")
        } else {
            relInternet!!.visibility = View.VISIBLE
            Glide.with(this)
                .load(R.raw.no_internet)
                .into(ivNoInternet!!)
        }
    }

    private fun webViewSetting() {
        val webSettings = mWebView!!.settings
        webSettings.javaScriptEnabled = true
        webSettings.loadWithOverviewMode = true
        webSettings.useWideViewPort = false
        webSettings.setSupportZoom(false)
        webSettings.builtInZoomControls = true
        webSettings.layoutAlgorithm = WebSettings.LayoutAlgorithm.SINGLE_COLUMN
        webSettings.cacheMode = WebSettings.LOAD_NO_CACHE
        webSettings.domStorageEnabled = true

        if (Build.VERSION.SDK_INT >= 19) {
            mWebView!!.setLayerType(View.LAYER_TYPE_HARDWARE, null)
        } else {
            mWebView!!.setLayerType(View.LAYER_TYPE_SOFTWARE, null)
        }
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment CTFSubmitFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            CTFSubmitFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}