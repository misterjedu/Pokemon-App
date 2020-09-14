package com.misterjedu.pokemonapp.helpers

import android.opengl.Visibility
import android.view.View

class PageLoaders{

        fun loadOnline(online: View, offline: View, loading: View){
            online.visibility = View.VISIBLE
            offline.visibility = View.GONE
            loading.visibility = View.GONE
        }

        fun loadOffline(online: View, offline: View, loading: View){
            online.visibility = View.GONE
            offline.visibility = View.VISIBLE
            loading.visibility = View.GONE
        }

        fun loadloading(online: View, offline: View, loading: View){
            online.visibility = View.GONE
            offline.visibility = View.GONE
            loading.visibility = View.VISIBLE
        }

}