package hpandro.java.infosec.md5.network

import com.google.gson.GsonBuilder
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

object APIClient {

    var gson = GsonBuilder()
        .setLenient()
        .create()

    private var retrofit = Retrofit.Builder()
        .baseUrl("http://hpandro.raviramesh.info")
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .addConverterFactory(GsonConverterFactory.create())
//        .addConverterFactory(GsonConverterFactory.create(gson))
//        .client(client)
        .build()

    fun getAPIClient(): APIInterface {
        return retrofit.create<APIInterface>(APIInterface::class.java)
    }
}