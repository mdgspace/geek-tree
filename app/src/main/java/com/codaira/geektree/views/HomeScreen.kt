package com.codaira.geektree.views

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.codaira.geektree.models.Posts
import com.codaira.geektree.R
import com.codaira.geektree.viewHolders.HomePostsViewHolder
import com.firebase.ui.database.FirebaseRecyclerAdapter
import com.firebase.ui.database.FirebaseRecyclerOptions
import com.google.firebase.database.FirebaseDatabase
import com.squareup.picasso.Picasso

import kotlinx.android.synthetic.main.post_layout.view.*

class HomeScreen : Fragment() {
    var givenInterest: String = ""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home_screen, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        view.findViewById<RecyclerView>(R.id.homescreen_recycler).layoutManager =
                LinearLayoutManager(activity, RecyclerView.VERTICAL, false) // adds recycler in vertical orientation

        MainActivity.user?.interests?.interests?.forEach {


            val query = FirebaseDatabase.getInstance().reference.child("Interests")
                .child(it) //to check if interest of a user matches the interest a post is under
            val options =
                FirebaseRecyclerOptions.Builder<Posts>().setQuery(query, Posts::class.java).setLifecycleOwner(activity)
                    .build()



            val adapter = object : FirebaseRecyclerAdapter<Posts, HomePostsViewHolder>(options) {
                override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomePostsViewHolder {
                    return HomePostsViewHolder(
                        LayoutInflater.from(parent.context).inflate(
                            R.layout.post_layout,
                            parent,
                            false
                        )
                    )
                }

                override fun onBindViewHolder(p0: HomePostsViewHolder, p1: Int, p2: Posts) { //binds data to objects
                    p0.bind(p2)
                    val img = p0.customView.post_image
                    if (p2.image!!.isEmpty()) {
                        //do nothing
                    } else {
                        Picasso.with(p0.customView.context).load(p2.image).into(img)//loads image into imageholder
                    }
                }
            }

            view.findViewById<RecyclerView>(R.id.homescreen_recycler).adapter = adapter
        }
    }
}