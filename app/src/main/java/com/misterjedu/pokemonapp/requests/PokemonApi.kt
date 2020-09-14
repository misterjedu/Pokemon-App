package com.misterjedu.pokemonapp.requests

import com.misterjedu.pokemonapp.requests.response.PokemanListResponse
import com.misterjedu.pokemonapp.requests.response.PokemanResponse
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Url


interface PokemonApi {

    //Get first 20
    @GET("pokemon")
    fun getPokemon(): Call<PokemanListResponse>

    //Get Individual detail
    @GET("pokemon/{id}")
    fun getIndividualPokemon(
        @Path("id") id: String,
    ): Call<PokemanResponse>

    //Get Next Url
    @GET
    fun getNextList(
        @Url url: String?
    ): Call<ResponseBody?>?

}