package com.misterjedu.pokemonapp.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.LinearLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.misterjedu.pokemonapp.R
import com.misterjedu.pokemonapp.helpers.PageLoaders
import com.misterjedu.pokemonapp.viewmodels.PokemonViewModel
import com.misterjedu.pokemonapp.models.Results
import com.misterjedu.pokemonapp.ui.adapter.HomeFragmentAdapter
import com.misterjedu.pokemonapp.viewmodels.NetWorkConnection
import kotlinx.android.synthetic.main.fragment_home.*


class HomeFragment : Fragment(), HomeFragmentAdapter.OnResultClickListener {

    private lateinit var viewModel: PokemonViewModel
    private var adapter = HomeFragmentAdapter(this)
    private var isFragmentVisible = true
    private lateinit var onLinePage: RecyclerView
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

        //If the fragment is in view, set fragment visibility to true
        isFragmentVisible = true

        // Inflate the layout for this fragment
        viewModel = ViewModelProvider(this).get(PokemonViewModel::class.java)
        //Instantiate the View Model Class
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        //Reference Page Loader
        pageLoader = PageLoaders()

        //Get all the 3 Page Mode layouts
        onLinePage = home_fragment_recycler
        offLine = offline_page
        loading = loading_page

        //Change Page based on internet connection
        val networkConnection = NetWorkConnection(activity?.applicationContext!!)
        networkConnection.observe(requireActivity(), {
            if (isFragmentVisible) {
                if (it) {
                    pageLoader.loadOnline(onLinePage, offLine, loading)
                    viewModel.getApiResult()
                } else {
                    pageLoader.loadOffline(onLinePage, offLine, loading)
                    offline_page.visibility = View.VISIBLE
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

        //Initialize the home fragment adapter
        home_fragment_recycler.adapter = adapter

        //Get the Pokemons life detail
        viewModel.pokemons.observe(viewLifecycleOwner, {
            adapter.setResult(it)
        })

        //Set Layout Grid Manager
        home_fragment_recycler.layoutManager = GridLayoutManager(requireContext(), 2)

    }

    //OnClick Listener Interface for each Pokemon and pass the URL in the bundle
    override fun onItemClick(item: Results, position: Int) {
        val fragment = PokemonDetail()
        val bundle = Bundle()
        bundle.putString("url", item.url)
        fragment.arguments = bundle
        loadDetailFragment(fragment)
    }

    //Load the Pokemon detail fragment
    private fun loadDetailFragment(fragment: Fragment) {
        val fragmentManager: FragmentManager = activity!!.supportFragmentManager
        val fragmentTransaction: FragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.pokemon_detail_frame, fragment, "tag")
            .addToBackStack(null).commit()
    }


    override fun onDestroy() {
        super.onDestroy()
        //If the fragment is in view, set fragment visibility to false
        isFragmentVisible = false
    }
}