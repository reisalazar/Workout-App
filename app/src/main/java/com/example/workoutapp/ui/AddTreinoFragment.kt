package com.example.workoutapp.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.example.workoutapp.R
import com.example.workoutapp.databinding.FragmentAddTreinoBinding
import com.example.workoutapp.model.Treino
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import java.util.*


class AddTreinoFragment : Fragment() {

    val database: FirebaseDatabase = FirebaseDatabase.getInstance()
    val myReference: DatabaseReference = database.reference.child("MyWorkouts")

    private var _binding: FragmentAddTreinoBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAddTreinoBinding.inflate(inflater, container, false)
        val view = binding.root
        return view

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initListeners()
    }

    private fun initListeners() {
        binding.btnAdd.setOnClickListener {
            addWorkoutToDatabase()
        }
    }

    private fun addWorkoutToDatabase() {
        val name = binding.etName.text.toString().toInt()
        val description = binding.etDescription.text.toString()
        val date = binding.etDate.text.toString()
        val id: String = myReference.push().key.toString()

        val treino = Treino(id, name, description, date)

        myReference.child(id).setValue(treino).addOnCompleteListener { task ->

            if (task.isSuccessful) {
                Toast.makeText(context, "New Workout added", Toast.LENGTH_SHORT).show()
                requireActivity().onBackPressed()
            } else {
                Toast.makeText(context, task.exception.toString(), Toast.LENGTH_SHORT).show()
                requireActivity().onBackPressed()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}