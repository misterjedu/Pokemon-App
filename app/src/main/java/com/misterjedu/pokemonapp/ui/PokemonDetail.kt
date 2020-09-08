package com.misterjedu.pokemonapp.ui

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ExpandableListView
import android.widget.ImageView
import android.widget.TextView
import com.misterjedu.pokemonapp.R
import com.misterjedu.pokemonapp.models.*
import com.misterjedu.pokemonapp.requests.PokemonApi
import com.misterjedu.pokemonapp.requests.ServiceGenerator
import com.misterjedu.pokemonapp.requests.response.PokemanResponse
import com.misterjedu.pokemonapp.ui.adapter.CustomExpandibleAdapter
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.fragment_pokemon_detail.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.IOException
import java.util.*
import kotlin.collections.ArrayList


class PokemonDetail : Fragment() {

    //Initialize Global Variables
    private lateinit var expandableListView: ExpandableListView
    private lateinit var listGroup: MutableList<String>
    private lateinit var listItem: MutableMap<String, MutableList<String>>
    private lateinit var adapter: CustomExpandibleAdapter
    private lateinit var pokemonId: String
    private lateinit var pokemonName: TextView
    private lateinit var pokemonOrder: TextView
    private lateinit var pokemonHeight: TextView
    private lateinit var pokemonWeight: TextView
    private lateinit var pokemonImage: ImageView
    private lateinit var pokemonDetailstext : TextView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_pokemon_detail, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        //Get Pokemon Id from bundle
        val bundle: Bundle? = this.arguments
        if (bundle != null) {
            Log.i("URL", bundle.getString("url")!!)
        }
        /**
         * Get the Passed contact bundle from the Contact list fragment
         * Get the Unique Id from the Url Passed
         */
        if (bundle !== null) {
            val url = bundle.getString("url")!!.split("/")
            pokemonId = url[url.size - 2]
        }

        //Get all Pokemon detail views
        pokemonName = pokemon_name_value
        pokemonOrder = pokemon_order_value
        pokemonHeight = pokemon_height_value
        pokemonWeight = pokemon_weight_value
        pokemonImage = pokemon_detail_image
        pokemonDetailstext = pokemon_fragmet_details_text

        expandableListView = expandable_list
        listGroup = mutableListOf()
        listItem = mutableMapOf()

        //Set Custom Expandable Adapter
        adapter = CustomExpandibleAdapter(requireContext(), listGroup, listItem)
        expandableListView.setAdapter(adapter)

        //Make an Api request to get the details of the pokemon
        requestPokemonRetrofit()
    }

    private fun initListData(
        pokemonTitles: MutableList<String>, pokemonDetails: MutableList<MutableList<String>>
    ) {

        //Set List Group title
        for (title in pokemonTitles) {
            listGroup.add(title)
        }

        //Set List children (items)
        val list1 = pokemonDetails[0]
        val list2 = pokemonDetails[1]
        val list3 = pokemonDetails[2]
        val list4 = pokemonDetails[3]


        //Set the List Item
        listItem[listGroup[0]] = list1
        listItem[listGroup[1]] = list2
        listItem[listGroup[2]] = list3
        listItem[listGroup[3]] = list4
    }


    private fun requestPokemonRetrofit() {
        val pokemonApi: PokemonApi = ServiceGenerator.getPokemonApi()

        val call: Call<PokemanResponse> = pokemonApi.getIndividualPokemon(pokemonId)

        call.enqueue(object : Callback<PokemanResponse> {
            override fun onResponse(
                call: Call<PokemanResponse>,
                response: Response<PokemanResponse>
            ) {
                if (response.code() == 200) {
                    /**
                     * Get all single values from the Response Object and set the views
                     */
                    pokemonName.text = response.body()?.getName()?.capitalize(Locale.ROOT)
                    pokemonOrder.text = response.body()?.getOrder().toString()
                    pokemonHeight.text = response.body()?.getHeight().toString()
                    pokemonWeight.text = response.body()?.getWeight().toString()
                    pokemonDetailstext.text = "${
                        response.body()?.getName()?.capitalize(Locale.ROOT)} " +
                            getString(R.string.details)
                    Picasso.get()
                        .load("https://pokeres.bastionbot.org/images/pokemon/${pokemonId}.png")
                        .into(pokemonImage)

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
                        for( index in abilities){
                            abilitiesList.add(index.ability.name)
                        }
                    }

                    //Extract Moves String
                    val movesList = mutableListOf<String>()
                    if (moves != null) {
                       for ((index, move) in moves.withIndex()) {
                           movesList.add(move.move.name)
                           if(index >= 10){
                               break
                           }
                        }
                    }

                    //Extract Stats String
                    val statsList = mutableListOf<String>()
                    if (stats != null) {
                        for( index in stats){
                            statsList.add("${index.stat.name} -> " +
                                    "${index.base_stat} ")
                        }
                    }


                    //Extract Types String
                    val typesList = mutableListOf<String>()
                    if (types != null) {
                        for( index in types){
                            typesList.add(index.type.name)
                        }
                    }

                    val pokemonTitles = mutableListOf("Abilities", "Moves", "Stats", "Types")

                    val pokemonDetails = mutableListOf(
                        abilitiesList, movesList,statsList,typesList
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
                    } catch (e: IOException) {
                        e.printStackTrace()
                    }
                }
            }

            override fun onFailure(call: Call<PokemanResponse>, t: Throwable) {
                Log.i("Server Response", t.message!!)
            }

        })
    }
}