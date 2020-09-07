package com.misterjedu.pokemonapp.models

import com.google.gson.annotations.SerializedName

class Abilities(val ability: Ability, val is_hidden: Boolean, val slot: Int) {

}

class Ability(val name: String, val url: String) {

}

class Moves(val move: Move, val version_group_details: List<Version_group_details>) {

}

class Move(val name: String, val url: String){

}

class Version_group_details(val level_learned_at: Int, val move_learn_method: Move_learn_method,
                            val version_group: Version_group){

}

class Version_group(val name: String, val url: String){

}

class Move_learn_method (val name : String, val url : String){

}

class Species (val name : String, val url : String){

}
 class Types (val slot : Int, val type : Type){

 }

class Type (val name : String, val url : String){

}

 class Stats (val base_stat : Int, val effort : Int, val stat : Stat){

 }

 class Stat (val name : String, val url : String){

 }