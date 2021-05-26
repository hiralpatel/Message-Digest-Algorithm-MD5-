package hpandro.java.infosec.md5.network

import com.google.gson.JsonObject
import io.reactivex.Observable
import retrofit2.http.*

interface APIInterface {
    @Headers("Content-Type: application/json")
    @GET("/flaghash.php")
    fun getHashFlag(@Query("flag") flag: String): Observable<JsonObject>
}