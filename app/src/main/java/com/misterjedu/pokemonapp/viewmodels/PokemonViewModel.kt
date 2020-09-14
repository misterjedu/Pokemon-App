package com.misterjedu.pokemonapp.viewmodels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.misterjedu.pokemonapp.models.Results
import com.misterjedu.pokemonapp.requests.PokemonApi
import com.misterjedu.pokemonapp.requests.ServiceGenerator
import com.misterjedu.pokemonapp.requests.response.PokemanListResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.IOException

class PokemonViewModel : ViewModel() {

    //Pokemon list live response
    private val _pokemons = MutableLiveData<List<Results>>()
    val pokemons: LiveData<List<Results>>
        get() = _pokemons

    //Connectivity Status
    private val _connectivity = MutableLiveData<String>()
    val connectivity: LiveData<String>
        get() = _connectivity

    //Function to Send an Api Request to the Pokemon server for the list of Pokemons
    fun getApiResult() {
        _connectivity.value = "Connecting"
        val pokemonApi: PokemonApi = ServiceGenerator.getPokemonApi()

        val call: Call<PokemanListResponse> = pokemonApi.getPokemon()

        call.enqueue(object : Callback<PokemanListResponse> {

            //When the response is successful
            override fun onResponse(
                call: Call<PokemanListResponse>,
                response: Response<PokemanListResponse>
            ) {
                Log.i("Server Response", response.toString())
                if (response.code() == 200) {

                    Log.i("Server Response Body", response.body()?.getNext().toString())
                    val results: ArrayList<Results>? = response.body()?.getResult()

                    //If the response code os 200 and the response body is not null
                    if (results != null) {
                        _connectivity.value = "Connected"
                        _pokemons.value = results
                    }
                } else {
                    //If the is an error with the Response body
                    try {
                        Log.i("Server Response Body", response.errorBody().toString())
                    } catch (e: IOException) {
                        e.printStackTrace()
                    }
                }
            }

            //When the response fails
            override fun onFailure(call: Call<PokemanListResponse>, t: Throwable) {
                _connectivity.value = "Not Connected"
                Log.i("Server Response Body", t.message.toString())
            }

        })
    }


}