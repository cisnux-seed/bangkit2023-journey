package dev.cisnux.myrecyclerview

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

// item listener on click
typealias OnItemClickCallback<T> = (data: T) -> Unit

class ListHeroAdapter(
    private val listHero: ArrayList<Hero>,
    private inline val onItemClickCallback: OnItemClickCallback<Hero>
) :
    RecyclerView.Adapter<ListHeroAdapter.ListViewHolder>() {

    class ListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imgPhoto: ImageView = itemView.findViewById(R.id.img_item_photo)
        val tvName: TextView = itemView.findViewById(R.id.tv_item_name)
        val tvDescription: TextView = itemView.findViewById(R.id.tv_item_description)
    }

    //  1. create item view and bind to view holder
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val view: View =
            LayoutInflater.from(parent.context).inflate(R.layout.item_row_hero, parent, false)
        return ListViewHolder(view)
    }

    //  2. bind item view
    override fun onBindViewHolder(holder: ListViewHolder, position: Int) = with(holder) {
        val (name, description, photo) = listHero[position]
        Glide.with(holder.itemView.context)
            .load(photo) // url of a picture
            .into(holder.imgPhoto) // ImageView
        tvName.text = name
        tvDescription.text = description
        itemView.setOnClickListener {
            //  use adapterPosition to handle user clicks
            onItemClickCallback(listHero[adapterPosition])
        }
    }

    // 3. set number of list
    override fun getItemCount(): Int = listHero.size
}