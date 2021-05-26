package hpandro.java.infosec.md5.data

import com.google.gson.annotations.SerializedName

class GetStatus {
    @SerializedName("OTP")
    var oTP: String? = ""

    @SerializedName("VALID")
    var vALID: String? = ""
}