package ru.binnyatoff.map.screens.history

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import ru.binnyatoff.map.R
import ru.binnyatoff.map.room.MapModel

class HistoryAdapter : RecyclerView.Adapter<HistoryAdapter.ViewHolder>() {
    var history_list: List<MapModel> = emptyList()

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val layer_name: TextView = view.findViewById(R.id.layer_name)
        val layer_text: TextView = view.findViewById(R.id.layer_text)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.history_items, parent, false)
        return ViewHolder(view)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.layer_name.text = "$position полигон"
        holder.layer_text.text = "${history_list[position].distance} м "
    }

    override fun getItemCount(): Int = history_list.size

    @SuppressLint("NotifyDataSetChanged")
    fun setData(new_history: List<MapModel>) {
        history_list = new_history
        notifyDataSetChanged()
    }
}