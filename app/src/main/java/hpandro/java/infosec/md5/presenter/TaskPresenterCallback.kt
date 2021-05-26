package hpandro.java.infosec.md5.presenter

import com.google.gson.JsonObject

interface TaskPresenterCallback {
    fun onGetLogs(response: JsonObject)
    fun onError(t: Throwable)
}