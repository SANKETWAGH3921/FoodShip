package com.sanket.foodship.admin

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.*
import com.sanket.foodship.R
import com.sanket.foodship.activities.AdminActivity
import com.sanket.foodship.adapters.ItemAdapter
import com.sanket.foodship.models.ItemModel

class AdminDashboardFragment : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: ItemAdapter
    private lateinit var databaseReference: DatabaseReference
    private val itemList = mutableListOf<ItemModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_admin_dashboard, container, false)

        val navIcon = view.findViewById<ImageView>(R.id.navimg)
        navIcon.setOnClickListener {
            (activity as? AdminActivity)?.openDrawerFromFragment()
        }

        recyclerView = view.findViewById(R.id.recyclerViewItems)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        adapter = ItemAdapter(itemList)
        recyclerView.adapter = adapter

        loadItemsFromFirebase()

        return view
    }

    private fun loadItemsFromFirebase() {
        databaseReference = FirebaseDatabase.getInstance()
            .getReference("Users/Admin/Items")

        databaseReference.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                itemList.clear()
                for (categorySnapshot in snapshot.children) {
                    for (itemSnapshot in categorySnapshot.children) {
                        val item = itemSnapshot.getValue(ItemModel::class.java)
                        item?.let {
                            itemList.add(it)
                        }
                    }
                }
                itemList.reverse() // Show latest items first
                adapter.notifyDataSetChanged()
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(requireContext(), "Failed to load items", Toast.LENGTH_SHORT).show()
            }
        })
    }
}
