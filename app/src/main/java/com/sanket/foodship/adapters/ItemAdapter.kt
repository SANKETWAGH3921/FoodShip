package com.sanket.foodship.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.sanket.foodship.R
import com.sanket.foodship.models.ItemModel

class ItemAdapter(private val itemList: List<ItemModel>) :
    RecyclerView.Adapter<ItemAdapter.ItemViewHolder>() {

    inner class ItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvItemName: TextView = itemView.findViewById(R.id.tvItemName)
        val tvPrice: TextView = itemView.findViewById(R.id.tvPrice)
        val tvDescription: TextView = itemView.findViewById(R.id.tvDescription)
        val tvDate: TextView = itemView.findViewById(R.id.tvDate)
        val tvCategory: TextView = itemView.findViewById(R.id.tvCategory)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_row, parent, false)
        return ItemViewHolder(view)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val item = itemList[position]
        holder.tvItemName.text = "Name: ${item.itemName}"
        holder.tvPrice.text = "Price: ₹${item.price}"
        holder.tvDescription.text = "Desc: ${item.description}"
        holder.tvDate.text = "Date: ${item.itemDate}" // ✅ This line now works
        holder.tvCategory.text = "Category: ${item.category}"
    }

    override fun getItemCount(): Int = itemList.size
}
