package com.misterjedu.pokemonapp.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.misterjedu.pokemonapp.R
import com.misterjedu.pokemonapp.models.Results
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.single_pokemon.view.*
import java.util.*
import kotlin.collections.ArrayList

class HomeFragmentAdapter(private var clickListener: OnResultClickListener,
                          private var results: ArrayList<Results>?):
    RecyclerView.Adapter<HomeFragmentAdapter.HomeFragmentViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeFragmentViewHolder {
        val pokemanListView = LayoutInflater.from(parent.context)
            .inflate(R.layout.single_pokemon, parent, false)
        return HomeFragmentViewHolder(pokemanListView)
    }

    override fun onBindViewHolder(holder: HomeFragmentViewHolder, position: Int) {

        holder.initialize(results!![position], clickListener)

        //Get the Id from the result url
        val currentPokeman = results!![position]
        val url = currentPokeman.url.split("/")
        val id = url[url.size-2]

        /**
         * Set Images using Picasso.
         */
        Picasso.get()
            .load("https://pokeres.bastionbot.org/images/pokemon/${id}.png")
            .into(holder.pokemonImage)
    }

    override fun getItemCount() = results!!.size

    //View Holder Class
    class HomeFragmentViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        var pokemonName : TextView = itemView.single_pokemon_name
        var pokemonImage : ImageView = itemView.single_pokemon_image

        fun initialize(item: Results, action: OnResultClickListener) {
            pokemonName.text = item.name.capitalize(Locale.ROOT)
            itemView.setOnClickListener{
                action.onItemClick(item,adapterPosition)
            }
        }
    }

    //OnClick Listener InterfaceR
    interface OnResultClickListener {
        fun onItemClick(item: Results, position: Int)
    }
}