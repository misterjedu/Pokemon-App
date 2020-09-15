package com.misterjedu.pokemonapp.ui

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.misterjedu.pokemonapp.R
import com.misterjedu.pokemonapp.helpers.PageLoaders
import com.misterjedu.pokemonapp.models.*
import com.misterjedu.pokemonapp.requests.PokemonApi
import com.misterjedu.pokemonapp.requests.ServiceGenerator
import com.misterjedu.pokemonapp.requests.response.PokemanResponse
import com.misterjedu.pokemonapp.ui.adapter.CustomExpandibleAdapter
import com.misterjedu.pokemonapp.viewmodels.NetWorkConnection
import com.misterjedu.pokemonapp.viewmodels.PokemonDetailViewModel
import com.misterjedu.pokemonapp.viewmodels.PokemonViewModel
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.fragment_home.*
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
    private var isFragmentVisible = true
    private lateinit var viewModel: PokemonDetailViewModel
    private lateinit var onLinePage: ConstraintLayout
    private lateinit var offLine: LinearLayout
    private lateinit var loading: LinearLayout
    private lateinit var pageLoader: PageLoaders


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        isFragmentVisible = true

        //Instantiate the View Model Class
        viewModel = ViewModelProvider(this).get(PokemonDetailViewModel::class.java)
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

        //Reference Page Loader
        pageLoader = PageLoaders()

        //Connectivity Mode Pages
        onLinePage = pokemon_detail_online_page
        offLine = pokemon_detail_offline_page
        loading = fragment_detail_loading_page

        //Change Page based on internet connection
        val networkConnection = NetWorkConnection(activity?.applicationContext!!)
        networkConnection.observe(requireActivity(), {
            if (isFragmentVisible) {
                if (it) {
                    pageLoader.loadOnline(onLinePage, offLine, loading)
                    viewModel.requestPokemonRetrofit(pokemonId)
                } else {
                    pageLoader.loadOffline(onLinePage, offLine, loading)
                }
            }
        })

        //Observe the Retrofit response
        viewModel.connectivity.observe(viewLifecycleOwner, {
            if (it == "Connected") {
                pageLoader.loadOnline(onLinePage, offLine, loading)
            } else if (it == "Connecting") {
                pageLoader.loadloading(onLinePage, offLine, loading)
            } else {
                pageLoader.loadOffline(onLinePage, offLine, loading)
            }
        })

        //Make Retrofit Request
//        viewModel.requestPokemonRetrofit(pokemonId)

        expandableListView = expandable_list

        /**
         * Observe to all Live Data from view models and set views
         */
        viewModel.listGroup.observe(viewLifecycleOwner, {
            listGroup = it
        })

        viewModel.listItem.observe(viewLifecycleOwner, {
            listItem = it
            //Set Custom Expandable Adapter
            adapter = CustomExpandibleAdapter(requireContext(), listGroup, listItem)
            expandableListView.setAdapter(adapter)
        })

        viewModel.name.observe(viewLifecycleOwner, {
            pokemon_name_value.text = it
        })

        viewModel.height.observe(viewLifecycleOwner, {
            pokemon_height_value.text = it
        })

        viewModel.order.observe(viewLifecycleOwner, {
            pokemon_order_value.text = it
        })

        viewModel.weight.observe(viewLifecycleOwner, {
            pokemon_weight_value.text = it
        })

        viewModel.detailsText.observe(viewLifecycleOwner, {
            pokemon_fragmet_details_text.text = it
        })

        //Get Pokemon Image and set the image view
        Picasso.get()
            .load("https://pokeres.bastionbot.org/images/pokemon/${pokemonId}.png")
            .into(pokemon_detail_image)

    }

    //override OnStop LifeCylce to set fragment visibility to false
    override fun onStop() {
        super.onStop()
        isFragmentVisible = false
    }

}