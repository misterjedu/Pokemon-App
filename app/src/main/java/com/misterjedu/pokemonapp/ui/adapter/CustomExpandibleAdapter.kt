package com.misterjedu.pokemonapp.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseExpandableListAdapter
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.misterjedu.pokemonapp.R

class CustomExpandibleAdapter(
    var context : Context,
    private var listGroup: MutableList<String>,
    private var listItem: MutableMap<String, MutableList<String>>
) : BaseExpandableListAdapter() {

    override fun getGroupCount(): Int {
       return   listGroup.size
    }

    override fun getChildrenCount(groupPosition: Int): Int {
      return listItem[listGroup[groupPosition]]!!.size
    }

    override fun getGroup(groupPosition: Int): Any {
      return listGroup[groupPosition]
    }

    override fun getChild(groupPosition: Int, childPosition: Int): Any {
       return listItem[listGroup[groupPosition]]!![childPosition]
    }

    override fun getGroupId(groupPosition: Int): Long {
        return groupPosition.toLong()
    }

    override fun getChildId(groupPosition: Int, childPosition: Int): Long {
        return childPosition.toLong()
    }

    override fun hasStableIds(): Boolean {
       return false
    }

    override fun getGroupView(groupPosition: Int, isExpanded: Boolean, convertView: View?, parent: ViewGroup?): View? {
        val group = getGroup(groupPosition)
        var convertView = convertView
        var holder: RecyclerView.ViewHolder
        if(convertView == null){
            val layoutInflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            convertView = layoutInflater.inflate(R.layout.list_group, null)
        }
        val textView: TextView? = convertView?.findViewById(R.id.list_parent)
        textView?.text = group as CharSequence?
        return convertView
    }

    override fun getChildView(groupPosition: Int, childPosition: Int, isLastChild: Boolean, convertView: View?, parent: ViewGroup?): View? {
        val child = getChild(groupPosition, childPosition)
        var convertView = convertView
        if(convertView == null){
            val layoutInflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            convertView = layoutInflater.inflate(R.layout.list_child, null)
        }

        val textView: TextView? = convertView?.findViewById(R.id.list_child)
        textView?.text = child as CharSequence?
        return  convertView
    }

    override fun isChildSelectable(p0: Int, p1: Int): Boolean {
       return true
    }
}