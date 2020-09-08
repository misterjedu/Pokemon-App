package com.misterjedu.pokemonapp

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import com.misterjedu.pokemonapp.models.Results
import com.misterjedu.pokemonapp.requests.response.PokemanListResponse
import com.misterjedu.pokemonapp.ui.adapter.HomeFragmentAdapter
import kotlinx.android.synthetic.main.fragment_home.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST
import java.io.IOException


class ClassWork : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_class_work, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)


    }


    object RetrofitClient {
        private var retrofit: Retrofit? = null
        fun getClient(baseUrl: String?): APIService? {
            if (retrofit == null) {
                retrofit = Retrofit.Builder()
                    .baseUrl(baseUrl)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
            }
            return retrofit?.create(APIService::class.java)
        }

    }

    interface APIService {
        @POST()
        @FormUrlEncoded
        fun savePost(
            @Field("first_name") first_name: String?,
            @Field("last_name") last_name: String?,
            @Field("email") email: String?,
            @Field("password") pass_word:String?
        ): Call<ClassSerialized?>
    }



    fun getApiResult(){
        val apiService: APIService? = RetrofitClient.getClient("https://risepodcast.herokuapp.com/api/v1/users/register")

        val call : Call<ClassSerialized?> = apiService!!.savePost(
            "Oladapo", "Oladokun","Oladapo@gmail.com", "O345+8")
    }
}