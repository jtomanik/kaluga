package com.splendo.kaluga.example.collectionView

import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.splendo.kaluga.example.R
import kotlinx.android.synthetic.main.cell_collection_view.view.*

class CollectionViewAdapter(private val listener: CollectionViewAdapterListener?, private val items: List<CollectionViewItem>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    interface CollectionViewAdapterListener {
        fun onItemSelected(selectedScreenIndex: Int)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return ItemViewHolder(inflater.inflate(R.layout.cell_collection_view, parent, false))
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val viewHolder = holder as ItemViewHolder
        val item = items[position]
        viewHolder.titleLabel.text = item.title
        viewHolder.view.setOnClickListener {
            listener?.onItemSelected(position)
        }
    }

    override fun getItemCount(): Int = items.count()

    inner class ItemViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
        val titleLabel: TextView = view.titleLabel
    }
}
