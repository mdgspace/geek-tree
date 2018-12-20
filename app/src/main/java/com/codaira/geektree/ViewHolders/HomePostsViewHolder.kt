package com.codaira.geektree.ViewHolders

//ViewHolder for posts shown on homeScreen

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.codaira.geektree.Models.Posts
import kotlinx.android.synthetic.main.post_layout.view.*

class HomePostsViewHolder(val customView: View, var posts: Posts? = null) : RecyclerView.ViewHolder(customView) {
    fun bind(posts: Posts) {
        customView.post_date?.text = posts.date
        customView.post_time?.text = posts.time
        customView.post_description?.text = posts.posttext
        customView.post_user_name?.text = posts.userid

    }
}
