package com.example.a7minworkoutapp

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.a7minworkoutapp.databinding.ActivityExcersieBinding
import com.example.a7minworkoutapp.databinding.RecycleviewItemBinding

class ExerciseAdapter(val item:ArrayList<ExerciseModel>):RecyclerView.Adapter<ExerciseAdapter.ExerciseViewHolder>() {
    inner class ExerciseViewHolder(binding:RecycleviewItemBinding):
        RecyclerView.ViewHolder(binding.root){
            val tvItem = binding.tvItem
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExerciseViewHolder {
        return ExerciseViewHolder(RecycleviewItemBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun onBindViewHolder(holder: ExerciseViewHolder, position: Int) {
        val item = item[position]
        holder.tvItem.text = item.getId().toString()
    }

    override fun getItemCount(): Int {
        return item.size
    }
}