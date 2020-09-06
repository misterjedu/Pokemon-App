package com.misterjedu.pokemonapp

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ExpandableListView
import kotlinx.android.synthetic.main.fragment_pokemon_detail.*


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


}