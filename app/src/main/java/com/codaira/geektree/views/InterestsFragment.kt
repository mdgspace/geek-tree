package com.codaira.geektree.views

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.codaira.geektree.data.Interests
import com.codaira.geektree.adapters.AllInterestsRecyclerAdapter
import com.codaira.geektree.R
import com.codaira.geektree.data.UserName
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.fragment_interests.*

class InterestsFragment : Fragment() {
    lateinit var firebasedatabase: DatabaseReference

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        setHasOptionsMenu(true)

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_interests, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        firebasedatabase = FirebaseDatabase.getInstance().reference.child("User")
            .child(FirebaseAuth.getInstance().currentUser?.uid.toString())
            .child("interests")
        //setting recycler
        interest_recycler?.layoutManager = LinearLayoutManager(activity, RecyclerView.VERTICAL, false)
        interest_recycler?.adapter = AllInterestsRecyclerAdapter(Interests.allInterestsArray)


        button_save_interests.setOnClickListener {view->


            if (!AllInterestsRecyclerAdapter.temporaryInterestList.isEmpty()) {
                val interests = Interests(AllInterestsRecyclerAdapter.temporaryInterestList)
                val save = firebasedatabase.setValue(interests)

                val builder = AlertDialog.Builder(activity)
                val progressBar: View = layoutInflater.inflate(R.layout.progress, null)
                builder.setView(progressBar)
                val dialog = builder.create()
                dialog.show()


                AllInterestsRecyclerAdapter.temporaryInterestList.forEach {
                    val userinfo=UserName(MainActivity.user?.username!!,FirebaseAuth.getInstance().currentUser?.uid.toString())
                    FirebaseDatabase.getInstance().reference.child("interests").child(it).child(FirebaseAuth.getInstance().currentUser?.uid.toString()).setValue(userinfo)
                }



                save.addOnCompleteListener {
                    Toast.makeText(activity, "Interests have been saved", Toast.LENGTH_LONG).show()
                    val navController = Navigation.findNavController(view)
                    navController.navigate(R.id.destination_emailVerification)
                    dialog.dismiss()
                }.addOnFailureListener {
                    Toast.makeText(activity, "Interests have NOT been saved", Toast.LENGTH_LONG).show()
                    dialog.dismiss()
                }
            }
            else{
                Toast.makeText(activity, "Please enter your interests", Toast.LENGTH_LONG).show()

            }
        }
    }
    override fun onPrepareOptionsMenu(menu: Menu) {
        super.onPrepareOptionsMenu(menu)
        val menuItem = menu.findItem(R.id.profile)
        menuItem.setVisible(false)
    }
}
