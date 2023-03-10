package com.example.workoutapp.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.workoutapp.databinding.ListItemTreinoBinding
import com.example.workoutapp.model.Treino

class TreinoAdapter(
    var context: Context,
    private val treinoList: List<Treino>
) : RecyclerView.Adapter<TreinoAdapter.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        return ViewHolder(
            ListItemTreinoBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val treino = treinoList[position]

        holder.binding.tvName.text = treino.nome.toString()
        holder.binding.tvDescription.text = treino.descricao
        holder.binding.tvData.text = treino.data

        holder.binding.cardView.setOnClickListener {

        }
    }

    override fun getItemCount(): Int {
        return treinoList.size
    }

    fun getTreinoId(position: Int):String {
        return treinoList[position].id
    }

    inner class ViewHolder(val binding: ListItemTreinoBinding) :
        RecyclerView.ViewHolder(binding.root)

}