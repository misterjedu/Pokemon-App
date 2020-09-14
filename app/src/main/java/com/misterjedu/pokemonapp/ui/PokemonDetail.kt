package com.misterjedu.pokemonapp.ui

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
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
    private lateinit var pokemonDetailstext: TextView
    private var isFragmentVisible = true
    private lateinit var refreshButton: Button


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        isFragmentVisible = true

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


        //Get refresh Button
        refreshButton = fragment_detail_button
        refreshButton.setOnClickListener {
            requestPokemonRetrofit()
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
        requestPokemonRetrofit()

    }

    //If there's no network connection, show the offline page and hide the online page
    private fun loadOffline() {
        pokemon_detail_online_page.visibility = View.GONE
        pokemon_detail_offline_page.visibility = View.VISIBLE
        fragment_detail_loading_page.visibility = View.GONE
    }

    //If there's no network connection, show the offline page and hide the online page
    private fun loadOnline() {
        pokemon_detail_online_page.visibility = View.VISIBLE
        pokemon_detail_offline_page.visibility = View.GONE
        fragment_detail_loading_page.visibility = View.GONE
    }

    private fun loadLoading() {
        pokemon_detail_online_page.visibility = View.GONE
        pokemon_detail_offline_page.visibility = View.GONE
        fragment_detail_loading_page.visibility = View.VISIBLE
    }

    //override Onstop Lifecylce to set fragment visibility to false
    override fun onStop() {
        super.onStop()
        isFragmentVisible = false
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




}