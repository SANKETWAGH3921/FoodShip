package com.sanket.foodship.admin

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.google.firebase.database.*
import com.sanket.foodship.R

class ProfileFragment : Fragment() {

    private lateinit var database: DatabaseReference
    private lateinit var tvName: TextView
    private lateinit var tvEmail: TextView
    private lateinit var tvUserId: TextView

    @SuppressLint("MissingInflatedId")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_profile, container, false)

        tvName = view.findViewById(R.id.tvName)
        tvEmail = view.findViewById(R.id.tvEmail)
        tvUserId = view.findViewById(R.id.tvUserId)

        // You can hardcode the Admin UID or fetch it from intent/session
        val adminUserId = "SOJWAL01" // Replace with actual userId if dynamic

        database = FirebaseDatabase.getInstance()
            .getReference("Users/Admin/$adminUserId")

        database.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    val name = snapshot.child("name").value?.toString() ?: ""
                    val email = snapshot.child("email").value?.toString() ?: ""
                    val userId = snapshot.child("userId").value?.toString() ?: ""

                    tvName.text = "Name: $name"
                    tvEmail.text = "Email: $email"
                    tvUserId.text = "User ID: $userId"
                }
            }

            override fun onCancelled(error: DatabaseError) {}
        })

        return view
    }
}
