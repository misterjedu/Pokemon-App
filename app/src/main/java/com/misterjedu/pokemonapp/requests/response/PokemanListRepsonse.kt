package com.misterjedu.pokemonapp.requests.response

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import com.misterjedu.pokemonapp.models.Results

class PokemanListResponse {

    @SerializedName("next")
    @Expose
    private var next: String? = null
    fun getNext(): String? {
        return next
    }

    @SerializedName("results")
    @Expose
    private var results: ArrayList<Results>? = null
    fun getResult(): ArrayList<Results>? {
        return results
    }


}