package com.kyawzinlinn.waterme.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.kyawzinlinn.waterme.databinding.ListItemBinding
import com.kyawzinlinn.waterme.model.Plant

class PlantAdapter(private val longClickListener: PlantListener): ListAdapter<Plant, PlantAdapter.PlantViewHolder>(DiffCallBack) {

    class PlantViewHolder(private val binding: ListItemBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(
            longClickListener: PlantListener,
            plant: Plant
        ){
            binding.plant = plant
            binding.longClickListner = longClickListener
            binding.executePendingBindings()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlantViewHolder {
        return PlantViewHolder(ListItemBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun onBindViewHolder(holder: PlantViewHolder, position: Int) {
        holder.bind(longClickListener,getItem(position))
    }

    companion object DiffCallBack: DiffUtil.ItemCallback<Plant>(){
        override fun areItemsTheSame(oldItem: Plant, newItem: Plant): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: Plant, newItem: Plant): Boolean {
            return oldItem.name == newItem.name
        }

    }
}

class PlantListener(val longClickListener: (plant: Plant) -> Boolean){
    fun onLongClick(plant: Plant) = longClickListener(plant)
}