package com.misterjedu.pokemonapp

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ExpandableListView
import com.misterjedu.pokemonapp.models.Abilities
import com.misterjedu.pokemonapp.models.Results
import com.misterjedu.pokemonapp.requests.PokemonApi
import com.misterjedu.pokemonapp.requests.ServiceGenerator
import com.misterjedu.pokemonapp.requests.response.PokemanListResponse
import com.misterjedu.pokemonapp.requests.response.PokemanResponse
import kotlinx.android.synthetic.main.fragment_pokemon_detail.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.IOException


class PokemonDetail : Fragment() {

    private lateinit var expandableListView : ExpandableListView
    private lateinit var listGroup : MutableList<String>
    private lateinit var listItem : MutableMap<String, MutableList<String>>
    private lateinit var adapter: CustomExpandibleAdapter


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
       expandableListView = expandable_list
        listGroup = mutableListOf()
        listItem = mutableMapOf()

        adapter = CustomExpandibleAdapter(requireContext(), listGroup, listItem)
        expandableListView.setAdapter(adapter)

        filter_button.setOnClickListener{
            testRetrofitRequest()
        }

        call_pokemon.setOnClickListener{
            callPokemonRequest()
        }
        initListData()
    }

    private fun initListData(){

        //Set the list group
        listGroup.add(getString(R.string.group1))
        listGroup.add(getString(R.string.group2))
        listGroup.add(getString(R.string.group3))
        listGroup.add(getString(R.string.group4))
        listGroup.add(getString(R.string.group5))

        var array : Array<String>

        val list1 = mutableListOf<String>()
        array = resources.getStringArray(R.array.group1)
        for (item in array){
            list1.add(item)
        }

        val list2 = mutableListOf<String>()
        array = resources.getStringArray(R.array.group2)
        for (item in array){
            list2.add(item)
        }

        val list3 = mutableListOf<String>()
        array = resources.getStringArray(R.array.group3)
        for (item in array){
            list3.add(item)
        }

        val list4 = mutableListOf<String>()
        array = resources.getStringArray(R.array.group4)
        for (item in array){
            list4.add(item)
        }

        val list5 = mutableListOf<String>()
        array = resources.getStringArray(R.array.group5)
        for (item in array){
            list5.add(item)
        }

        //Set the List Item
        listItem[listGroup[0]] = list1
        listItem[listGroup[1]] = list2
        listItem[listGroup[2]] = list3
        listItem[listGroup[3]] = list4
        listItem[listGroup[4]] = list5

    }

    private fun testRetrofitRequest(){
        val pokemonApi: PokemonApi = ServiceGenerator.getPokemonApi()

       val call: Call<PokemanResponse> =  pokemonApi.getIndividualPokemon("5")

        call.enqueue(object : Callback<PokemanResponse> {
            override fun onResponse(
                call: Call<PokemanResponse>,
                response: Response<PokemanResponse>
            ) {

                Log.i("Server Response", response.toString())

                if(response.code()  == 200 ){
                    Log.i("Server Response Body", response.body()?.getName().toString())
                    val abilities: ArrayList<Abilities>? = response.body()?.getAbilities()
                    if (abilities != null) {
                        for (index in abilities){
                            Log.i("Server Response Body", index.ability.name)
                        }
                    }
                }else{
                    try{
                        Log.i("Server Response Body", response.errorBody().toString())
                    }catch (e: IOException ){
                        e.printStackTrace()
                    }
                }
            }

            override fun onFailure(call: Call<PokemanResponse>, t: Throwable) {
                Log.i("Server Response", t.message!!)
            }

        })
    }

    private fun callPokemonRequest(){
        val pokemonApi: PokemonApi = ServiceGenerator.getPokemonApi()

        val call: Call<PokemanListResponse> =  pokemonApi.getPokemon()

        call.enqueue(object : Callback<PokemanListResponse>{
            override fun onResponse(
                call: Call<PokemanListResponse>,
                response: Response<PokemanListResponse>
            ) {
                Log.i("Server Response", response.toString())
                if(response.code()  == 200 ){
                    Log.i("Server Response Body", response.body()?.getNext().toString())
                    val results: ArrayList<Results>? = response.body()?.getResult()
                    if (results != null) {
                        for (index in results){
                            Log.i("Server Response Body", index.name)
                        }
                    }
                }else{
                    try{
                        Log.i("Server Response Body", response.errorBody().toString())
                    }catch (e: IOException ){
                        e.printStackTrace()
                    }
                }
            }

            override fun onFailure(call: Call<PokemanListResponse>, t: Throwable) {
                Log.i("Server Response", t.message!!)
            }

        })
    }

}