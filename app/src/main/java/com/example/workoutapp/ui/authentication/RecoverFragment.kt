package com.example.workoutapp.ui.authentication

import android.content.ContentValues
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import com.example.workoutapp.databinding.FragmentRecoverBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class RecoverFragment : Fragment() {

    private var _binding: FragmentRecoverBinding? = null
    private val binding get() = _binding!!

    private lateinit var auth: FirebaseAuth

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRecoverBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        auth = Firebase.auth

        initListeners()
    }
    private fun initListeners() {
        binding.btnSend.setOnClickListener { validateData() }
    }

    private fun validateData() {
        val email = binding.etEmailRecover.text.toString()

        if (email.isNotEmpty()) {
            binding.progressBar.isVisible = true
            recoverAccount(email)
        } else {
            Toast.makeText(requireContext(), "Insert a email", Toast.LENGTH_SHORT).show()

        }
    }

    private fun recoverAccount(email: String) {
        auth.sendPasswordResetEmail(email)
            .addOnCompleteListener(requireActivity()) { task ->
                if (task.isSuccessful) {
                    binding.progressBar.isVisible = false
                    Toast.makeText(requireContext(), "A reset password link has been sent to your email", Toast.LENGTH_LONG).show()
                } else {
                    binding.progressBar.isVisible = false
                    Toast.makeText(requireContext(), "Account recovery failed", Toast.LENGTH_SHORT).show()
                    Log.w(ContentValues.TAG, "Recover:failure", task.exception)
                }
            }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}