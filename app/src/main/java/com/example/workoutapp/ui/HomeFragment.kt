package com.example.workoutapp.ui

import com.example.workoutapp.adapter.TreinoAdapter
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.workoutapp.R
import com.example.workoutapp.databinding.FragmentHomeBinding
import com.example.workoutapp.model.Treino
import com.google.firebase.database.*


class HomeFragment : Fragment() {

    val database: FirebaseDatabase = FirebaseDatabase.getInstance()
    val myReference: DatabaseReference = database.reference.child("MyWorkouts")

    private lateinit var binding: FragmentHomeBinding

    private val treinoList = mutableListOf<Treino>()
    lateinit var treinoAdapter: TreinoAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        val view = binding.root
        return view

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        initListeners()
        retrieveDataFromDatabase()
        deleteTreinoOnSwipe()

    }


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

                    treinoAdapter = TreinoAdapter(requireContext(), treinoList)
                    binding.rvWorkout.layoutManager = LinearLayoutManager(requireContext())
                    binding.rvWorkout.setHasFixedSize(true)
                    binding.rvWorkout.adapter = treinoAdapter

                }
                treinoEmpty()
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
    private fun treinoEmpty() {
        binding.tvEmpty.text = if (treinoList.isEmpty()) {
            getText(R.string.tv_treino_empty)
        } else {
            ""
        }
    }

    private fun deleteTreinoOnSwipe() {
        ItemTouchHelper(object :
            ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                TODO("Not yet implemented")
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {

                val id = treinoAdapter.getTreinoId(viewHolder.adapterPosition)
                myReference.child(id).removeValue()

                Toast.makeText(requireContext(), "The user was deleted", Toast.LENGTH_SHORT).show()
            }
        }).attachToRecyclerView(binding.rvWorkout)
    }

}