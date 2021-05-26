package hpandro.java.infosec.md5

import android.app.Activity
import android.content.Context
import android.net.ConnectivityManager
import android.os.Build
import android.widget.Toast
import androidx.annotation.RequiresApi
import java.math.BigInteger
import java.security.MessageDigest
import java.security.NoSuchAlgorithmException

class AppUtils {

    companion object {

        fun isOnline(activity: Activity?): Boolean {
            val cm =
                activity!!.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            val isMetered = cm.isActiveNetworkMetered
            val netInfo = cm.activeNetworkInfo
            return netInfo != null && netInfo.isConnected
//            return isMetered
        }

        fun showToast(context: Context, msg: String) {
            Toast.makeText(context, msg, Toast.LENGTH_SHORT).show()
//            val toast: Toast = Toast.makeText(context, msg, Toast.LENGTH_LONG)
//            val toastView: View? = toast.view
//            val toastMessage = toastView!!.findViewById<View>(R.id.message) as TextView
//            toastMessage.setTextColor(Color.WHITE)
//            toastMessage.gravity = Gravity.CENTER
//            toastView.setBackgroundColor(Color.BLACK)
//            toast.show()
        }

        fun htmlHeader(pass: String): MutableMap<String, String> {
            var pass = pass
            var password: String? = null
            val mdEnc: MessageDigest
            try {
                mdEnc = MessageDigest.getInstance("MD5")
                mdEnc.update(pass.toByteArray(), 0, pass.length)
                pass = BigInteger(1, mdEnc.digest()).toString(16)
                while (pass.length < 32) {
                    pass = "0$pass"
                }
                password = pass
            } catch (e1: NoSuchAlgorithmException) {
                e1.printStackTrace()
            }

            val extraHeaders: MutableMap<String, String> = HashMap()
            extraHeaders["CRED"] = password.toString()

            return extraHeaders
        }

        @RequiresApi(Build.VERSION_CODES.KITKAT)
        fun hexToAscii(data: String): String {
            val output = StringBuilder("")
            var i = 0
            while (i < data.length) {
                val str: String = data.substring(i, i + 2)
                output.append(str.toInt(16).toChar())
                i += 2
            }
            return output.toString()
        }
    }
}