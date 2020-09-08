package com.misterjedu.pokemonapp.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.FragmentTransaction
import com.misterjedu.pokemonapp.R

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val fragment = HomeFragment()
        val fragmentManager = supportFragmentManager;
        val fragmentTransaction: FragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.add(R.id.pokemon_detail_frame,fragment, "tag")
            .addToBackStack(null).commit()
    }
}