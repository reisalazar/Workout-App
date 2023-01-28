package com.example.workoutapp.ui

import TreinoAdapter
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.workoutapp.databinding.FragmentHomeBinding
import com.example.workoutapp.model.Treino


class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private val treinoList = mutableListOf<Treino>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val view = binding.root
        return view

        initRecyclerView()
    }

    private fun initRecyclerView() {
        binding.rvWorkout.layoutManager = LinearLayoutManager(requireContext())
        binding.rvWorkout.adapter = TreinoAdapter(treinoList)
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}