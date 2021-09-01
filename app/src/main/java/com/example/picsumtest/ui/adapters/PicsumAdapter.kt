package com.example.picsumtest.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ListAdapter
import android.widget.TextView
import androidx.core.net.toUri
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.picsumtest.R
import com.example.picsumtest.data.model.Picsum

class PicsumAdapter(private val onClick: (Picsum) -> Unit) :
    androidx.recyclerview.widget.ListAdapter<Picsum, PicsumAdapter.PicsumViewHolder>(PicsumDiffCallback) {


    class PicsumViewHolder(itemView: View, val onClick: (Picsum) -> Unit) :
        RecyclerView.ViewHolder(itemView) {
        private val picsumImageView: ImageView = itemView.findViewById(R.id.picsum_image)
        private val picsumAuthor: TextView = itemView.findViewById(R.id.picsum_author)
        private var currentPicsum: Picsum? = null

        init {
            itemView.setOnClickListener {
                currentPicsum?.let {
                    onClick(it)
                }
            }
        }

        fun bind(picsum: Picsum) {
            currentPicsum = picsum

            picsumAuthor.text = picsum.author
            picsumImageView.setImageURI(picsum.url.toUri())
        }


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PicsumViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.picsum_item,parent,false)
        return PicsumViewHolder(view,onClick)
    }

    override fun onBindViewHolder(holder: PicsumViewHolder, position: Int) {
        val picsum = getItem(position)
        holder.bind(picsum)
    }

    //override fun onCre

}

object PicsumDiffCallback:DiffUtil.ItemCallback<Picsum>() {
    override fun areItemsTheSame(oldItem: Picsum, newItem: Picsum): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: Picsum, newItem: Picsum): Boolean {
        return oldItem.id==newItem.id
    }


}
