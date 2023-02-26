package dev.cisnux.myrecyclerview

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import dev.cisnux.myrecyclerview.databinding.ItemRowHeroBinding

// item listener on click
typealias OnItemClickCallback<T> = (data: T) -> Unit

class ListHeroAdapter(
    private val listHero: ArrayList<Hero>,
    private inline val onItemClickCallback: OnItemClickCallback<Hero>
) :
    RecyclerView.Adapter<ListHeroAdapter.ListViewHolder>() {

    class ListViewHolder(val binding: ItemRowHeroBinding) : RecyclerView.ViewHolder(binding.root)

    //  1. create item view and bind to view holder
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val binding =
            ItemRowHeroBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ListViewHolder(binding)
    }

    //  2. bind item view
    override fun onBindViewHolder(holder: ListViewHolder, position: Int) = with(holder) {
        val (name, description, photo) = listHero[position]
        Glide.with(binding.root)
            .load(photo) // url of a picture
            .into(binding.imgItemPhoto) // ImageView
        binding.tvItemName.text = name
        binding.tvItemDescription.text = description
        itemView.setOnClickListener {
            //  use adapterPosition to handle user clicks
            onItemClickCallback(listHero[adapterPosition])
        }
    }

    // 3. set number of list
    override fun getItemCount(): Int = listHero.size
}