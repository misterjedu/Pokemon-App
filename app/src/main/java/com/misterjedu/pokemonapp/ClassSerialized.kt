package com.misterjedu.pokemonapp

import com.google.gson.annotations.Expose

import com.google.gson.annotations.SerializedName


class ClassSerialized{
    @SerializedName("first_name")
    @Expose
    var firstName: String? = null

    @SerializedName("last_name")
    @Expose
    var lastName: String? = null

    @SerializedName("email")
    @Expose
    var email: String? = null

    @SerializedName("password")
    @Expose
    var password: String? = null
}