package com.misterjedu.pokemonapp.viewmodels

import android.util.Log
import android.widget.ImageView
import android.widget.TextView
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.misterjedu.pokemonapp.models.Abilities
import com.misterjedu.pokemonapp.models.Moves
import com.misterjedu.pokemonapp.models.Stats
import com.misterjedu.pokemonapp.models.Types
import com.misterjedu.pokemonapp.requests.PokemonApi
import com.misterjedu.pokemonapp.requests.ServiceGenerator
import com.misterjedu.pokemonapp.requests.response.PokemanResponse
import com.squareup.picasso.Picasso
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.IOException
import java.util.*
import kotlin.collections.ArrayList

class PokemonDetailViewModel : ViewModel() {

    private val _name = MutableLiveData<String>()
    val name: LiveData<String>
        get() = _name

    private val _order = MutableLiveData<String>()
    val order: LiveData<String>
        get() = _order

    private val _height = MutableLiveData<String>()
    val height: LiveData<String>
        get() = _height

    private val _weight = MutableLiveData<String>()
    val weight: LiveData<String>
        get() = _weight


    private val _detailsText = MutableLiveData<String>()
    val detailsText: LiveData<String>
        get() = _detailsText

    private val _listGroup = MutableLiveData<MutableList<String>>()
    val listGroup: LiveData<MutableList<String>>
        get() = _listGroup

    private val _listItem = MutableLiveData<MutableMap<String, MutableList<String>>>()
    val listItem: LiveData<MutableMap<String, MutableList<String>>>
        get() = _listItem

    //Connectivity Status
    private val _connectivity = MutableLiveData<String>()
    val connectivity: LiveData<String>
        get() = _connectivity


  fun requestPokemonRetrofit(id: String) {
      _connectivity.value = "Connecting"
        //Get the Service Generator Interface
        val pokemonApi: PokemonApi = ServiceGenerator.getPokemonApi()

        //Make the request call the specific pokemon URL using the Id
        val call: Call<PokemanResponse> = pokemonApi.getIndividualPokemon(id)

        call.enqueue(object : Callback<PokemanResponse> {
            override fun onResponse(
                call: Call<PokemanResponse>,
                response: Response<PokemanResponse>
            ) {
                if (response.code() == 200) {
                    _connectivity.value = "Connected"

                    /**
                     * Get all single values from the Response Object and set the views
                     */
                    _name.value = response.body()?.getName()?.capitalize(Locale.ROOT)
                    _order.value = response.body()?.getOrder().toString()
                    _height.value = response.body()?.getHeight().toString()
                    _weight.value = response.body()?.getWeight().toString()
                   _detailsText.value = "${
                        response.body()?.getName()?.capitalize(Locale.ROOT)
                    } " + "Details"


                    /**
                     * Get Arrays of Objects select details
                     */
                    val abilities: ArrayList<Abilities>? = response.body()?.getAbilities()
                    val moves: ArrayList<Moves>? = response.body()?.getMoves()
                    val stats: ArrayList<Stats>? = response.body()?.getStats()
                    val types: ArrayList<Types>? = response.body()?.getTypes()

                    /**
                     *  Extract the string values of the objects
                     */
                    //Extract Abilities string
                    val abilitiesList = mutableListOf<String>()
                    if (abilities != null) {
                        for (index in abilities) {
                            abilitiesList.add(index.ability.name)
                        }
                    }

                    //Extract Moves String
                    val movesList = mutableListOf<String>()
                    if (moves != null) {
                        for ((index, move) in moves.withIndex()) {
                            movesList.add(move.move.name)
                            if (index >= 10) {
                                break
                            }
                        }
                    }

                    //Extract Stats String
                    val statsList = mutableListOf<String>()
                    if (stats != null) {
                        for (index in stats) {
                            statsList.add(
                                "${index.stat.name} -> " +
                                        "${index.base_stat} "
                            )
                        }
                    }


                    //Extract Types String
                    val typesList = mutableListOf<String>()
                    if (types != null) {
                        for (index in types) {
                            typesList.add(index.type.name)
                        }
                    }

                    val pokemonTitles = mutableListOf("Abilities", "Moves", "Stats", "Types")

                    val pokemonDetails = mutableListOf(
                        abilitiesList, movesList, statsList, typesList
                    )


                    /**
                     *  After extracting all the titles and details
                     *  pass them into the the init function for the
                     *  expandable adapter's consumption.
                     */
                    initListData(pokemonTitles, pokemonDetails)

                } else {
                    try {
                        Log.i("Server Response Body", response.errorBody().toString())
//                        loadOffline()
                    } catch (e: IOException) {
                        e.printStackTrace()
                    }
                }
            }

            override fun onFailure(call: Call<PokemanResponse>, t: Throwable) {

                 _connectivity.value = "Not Connected"
            }
        })


    }

    private fun initListData(pokemonTitles: MutableList<String>, pokemonDetails: MutableList<MutableList<String>>) {
        //Set List Group title
        _listGroup.value = pokemonTitles

        val newlistGroup = mutableMapOf<String, MutableList<String>>()

        newlistGroup[pokemonTitles[0]] = pokemonDetails[0]
        newlistGroup[pokemonTitles[1]] = pokemonDetails[1]
        newlistGroup[pokemonTitles[2]] = pokemonDetails[2]
        newlistGroup[pokemonTitles[3]] = pokemonDetails[3]

        //Set Group Items
        _listItem.value = newlistGroup

    }


}

