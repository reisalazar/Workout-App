package com.example.workoutapp.ui

import TreinoAdapter
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.workoutapp.R
import com.example.workoutapp.databinding.FragmentHomeBinding
import com.example.workoutapp.model.Treino
import com.google.firebase.database.*


class HomeFragment : Fragment() {

    val database: FirebaseDatabase = FirebaseDatabase.getInstance()
    val myReference: DatabaseReference = database.reference.child("MyWorkouts")

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private val treinoList = mutableListOf<Treino>()
    lateinit var treinoAdapter: TreinoAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val view = binding.root
        return view

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

//        initRecyclerView()
        initListeners()
        retrieveDataFromDatabase()
    }

//    private fun initRecyclerView() {
//        binding.rvWorkout.layoutManager = LinearLayoutManager(requireContext())
//        binding.rvWorkout.adapter = TreinoAdapter(treinoList)
//    }

    private fun initListeners() {
        binding.fabAdd.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_addTreinoFragment)
        }
    }

    private fun retrieveDataFromDatabase() {
        myReference.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {

                if (snapshot.exists()) {
                treinoList.clear()

                    for (eachTreino in snapshot.children) {
                        val treino = eachTreino.getValue(Treino::class.java) as Treino

                    treinoList.add(treino)
                }

                    binding.rvWorkout.layoutManager = LinearLayoutManager(requireContext())
                    treinoAdapter = TreinoAdapter(requireContext(), treinoList)
                    binding.rvWorkout.adapter = treinoAdapter

                }

            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(
                    requireContext(),
                    "Failed to retrieve data from database",
                    Toast.LENGTH_SHORT
                ).show()
            }
        })
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}