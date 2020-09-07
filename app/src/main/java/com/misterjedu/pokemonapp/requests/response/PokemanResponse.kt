package com.misterjedu.pokemonapp.requests.response

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import com.misterjedu.pokemonapp.models.*

class PokemanResponse {

    @SerializedName("order")
    @Expose
    private var order: Int? = null
    fun getOrder(): Int? {
        return order
    }

    @SerializedName("name")
    private var name: String? = null
    fun getName(): String? {
        return name
    }

    @SerializedName("next")
    private var next: String? = null
    fun getNext(): String? {
        return next
    }


    @SerializedName("height")
    @Expose
    private var height: Int = 0
    fun getHeight(): Int {
        return height
    }

    @SerializedName("weight")
    @Expose
    private var weight: Int = 0
    fun getWeight(): Int {
        return weight
    }

    @SerializedName("base_experience")
    @Expose
    private var baseExperience: Int = 0
    fun getBaseExperience(): Int {
        return baseExperience
    }

    @SerializedName("abilities")
    @Expose
    private lateinit var abilities: ArrayList<Abilities>
    fun getAbilities(): ArrayList<Abilities> {
        return abilities
    }

    @SerializedName("moves")
    @Expose
    private lateinit var moves: ArrayList<Moves>
    fun getMoves(): ArrayList<Moves> {
        return moves
    }

    @SerializedName("id")
    @Expose
    private var id: Int = 0
    fun getId(): Int {
        return id
    }

    @SerializedName("species")
    @Expose
    private lateinit var species: Species
    fun getSpecies(): Species {
        return species
    }

    @SerializedName("types")
    @Expose
    private lateinit var types: ArrayList<Types>
    fun getTypes(): ArrayList<Types> {
        return types
    }

    @SerializedName("stats")
    @Expose
    private lateinit var stats: ArrayList<Stats>
    fun getStats(): ArrayList<Stats> {
        return stats
    }

//    override fun toString(): String {
//        return "PokemanResponse(order=$order, height=$height, weight=$weight, baseExperience=$baseExperience, abilities=$abilities, moves=$moves, id=$id, species=$species, types=$types, stats=$stats)"
//    }

}