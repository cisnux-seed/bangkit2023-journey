package dev.cisnux.myquote

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import dev.cisnux.myquote.databinding.ItemQuoteBinding

class QuoteAdapter(private val listReview: ArrayList<String>) :
    RecyclerView.Adapter<QuoteAdapter.ViewHolder>() {
    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            ItemQuoteBinding.inflate(LayoutInflater.from(viewGroup.context), viewGroup, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        viewHolder.binding.tvItem.text = listReview[position]
    }

    override fun getItemCount(): Int {
        return listReview.size
    }

    class ViewHolder(val binding: ItemQuoteBinding) : RecyclerView.ViewHolder(binding.root)
}