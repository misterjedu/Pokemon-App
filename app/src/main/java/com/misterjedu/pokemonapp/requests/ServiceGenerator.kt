package com.misterjedu.pokemonapp.requests

import com.misterjedu.pokemonapp.util.Constants
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ServiceGenerator {
     companion object{
         private var retrofitBuilder = Retrofit.Builder()
             .baseUrl(Constants.BASE_URL)
             .addConverterFactory( GsonConverterFactory.create())

         private var retrofit = retrofitBuilder.build()

         private var pokemonApi = retrofit.create(PokemonApi::class.java)

         fun getPokemonApi() : PokemonApi{
             return pokemonApi
         }
     }
}