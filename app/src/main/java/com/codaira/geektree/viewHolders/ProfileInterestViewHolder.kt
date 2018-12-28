package com.codaira.geektree.viewHolders

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.codaira.geektree.R

class ProfileInterestViewHolder(val customView: View) : RecyclerView.ViewHolder(customView) {

    fun setInterestString(interest: String) {
        var a =customView.findViewById<TextView>(R.id.text_profile_interest)
        a.setText(interest)
    }
}