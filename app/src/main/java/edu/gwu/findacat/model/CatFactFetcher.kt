package edu.gwu.trivia

import android.content.Context
import android.content.res.Configuration
import android.net.Uri
import android.util.Log
import android.widget.ImageView
import android.widget.TextView
import edu.gwu.findacat.model.CatfactResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

class CatFactFetcher(private val context: Context, private val textView: TextView) {

    private val TAG = "CatFactFetcher"
    var catFactFetcherCompletionListener: CatFactFetherCompletionListener? = null

    interface CatFactFetherCompletionListener {
        fun CatFactLoaded(fact:String){

        }
        fun CatFactNotLoaded()
    }

    interface ApiEndpointInterface {
        @GET("/fact")
        fun getBingResponse():Call<CatfactResponse>
    }

    fun searchFacts() {
        val retrofit = Retrofit.Builder()
                .baseUrl("https://catfact.ninja/")
                .addConverterFactory(MoshiConverterFactory.create())
                .build()

        val apiEndpoint = retrofit.create(ApiEndpointInterface::class.java)

        apiEndpoint.getBingResponse().enqueue(object: Callback<CatfactResponse> {
            override fun onFailure(call: Call<CatfactResponse>, t: Throwable) {
                Log.d(TAG, "API call failed!")
                catFactFetcherCompletionListener?.CatFactNotLoaded()
            }

            override fun onResponse(call: Call<CatfactResponse>, response: Response<CatfactResponse>) {
                val bingResponseBody = response.body()

                if (bingResponseBody != null) {
                    Log.d(TAG, "")
                    catFactFetcherCompletionListener?.CatFactLoaded(bingResponseBody.fact)

                } else {
                    catFactFetcherCompletionListener?.CatFactNotLoaded()
                }

            }
        })
    }
}