package com.misterjedu.pokemonapp.ui

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import androidx.recyclerview.widget.GridLayoutManager
import com.misterjedu.pokemonapp.R
import com.misterjedu.pokemonapp.models.Results
import com.misterjedu.pokemonapp.requests.PokemonApi
import com.misterjedu.pokemonapp.requests.ServiceGenerator
import com.misterjedu.pokemonapp.requests.response.PokemanListResponse
import com.misterjedu.pokemonapp.ui.adapter.HomeFragmentAdapter
import kotlinx.android.synthetic.main.fragment_home.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.IOException


class HomeFragment : Fragment(), HomeFragmentAdapter.OnResultClickListener {
    private lateinit var adapter: HomeFragmentAdapter
//    private

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
       getApiResult()
    }

    private fun getApiResult(){
        val pokemonApi: PokemonApi = ServiceGenerator.getPokemonApi()

        val call: Call<PokemanListResponse> =  pokemonApi.getPokemon()

        call.enqueue(object : Callback<PokemanListResponse> {
            override fun onResponse(
                call: Call<PokemanListResponse>,
                response: Response<PokemanListResponse>
            ) {
                Log.i("Server Response", response.toString())
                if(response.code()  == 200 ){
                    Log.i("Server Response Body", response.body()?.getNext().toString())
                    val results: ArrayList<Results>? = response.body()?.getResult()

                    if (results != null) {
                        //Set the results from the Api into the Adapter
                        adapter = HomeFragmentAdapter(this@HomeFragment,results)
                        home_fragment_recycler.adapter = adapter
                        home_fragment_recycler.layoutManager = GridLayoutManager(requireContext(), 2)
                    }
                }else{
                    try{
                        Log.i("Server Response Body", response.errorBody().toString())
                    }catch (e: IOException){
                        e.printStackTrace()
                    }
                }
            }

            override fun onFailure(call: Call<PokemanListResponse>, t: Throwable) {
                Log.i("Server Response", t.message!!)
            }

        })
    }

    //OnClick Listener Interface for each Pokemon
    override fun onItemClick(item: Results, position: Int) {
       val fragment = PokemonDetail()
        val bundle = Bundle()
        bundle.putString("url", item.url)
        fragment.arguments = bundle
        loadDetailFragment(fragment)
    }

    //Load the Pokemon detail fragment
    private fun loadDetailFragment(fragment: Fragment){
        val fragmentManager: FragmentManager = activity!!.supportFragmentManager
        val fragmentTransaction: FragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.pokemon_detail_frame, fragment, "tag")
            .addToBackStack(null).commit()
    }
}