package com.itech.androidassessment.utils

import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider

import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.itech.androidassessment.data.database.DatabaseUser
import com.itech.androidassessment.data.database.getDatabase
import com.itech.androidassessment.data.repository.UserRepository
import com.itech.androidassessment.databinding.BottomSheetFragmentLayoutBinding
import com.itech.androidassessment.presentation.fragments.profile.ProfileViewModel

class SignUpBottomSheetDialog() : BottomSheetDialogFragment() {
    private lateinit var _binding: BottomSheetFragmentLayoutBinding
    private val binding get() = _binding!!
    private val viewModel by lazy {
        ViewModelProvider(this).get(ProfileViewModel::class.java)
    }


    companion object {
        @JvmStatic
        fun  newInstance() = SignUpBottomSheetDialog()

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = BottomSheetFragmentLayoutBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnAdd.setOnClickListener {
            saveDataToDb()
        }

        binding.btnClose.setOnClickListener {
            this.dismiss()
        }

    }

    private fun saveDataToDb() {
        if(binding.evFullname.editText?.text.toString().isNullOrEmpty()){
            Toast.makeText(context,"Add Fullname",Toast.LENGTH_SHORT).show()
        }
        else if(binding.evUsername.editText?.text.toString().isNullOrEmpty()){
            Toast.makeText(context,"Add Username",Toast.LENGTH_SHORT).show()
        }
        else if(binding.evEmail.editText?.text.toString().isNullOrEmpty()){
            Toast.makeText(context,"Add Email",Toast.LENGTH_SHORT).show()
        }else{
            val user = DatabaseUser(
                id = null,
                name = binding.evFullname.editText?.text.toString(),
                username = binding.evUsername.editText?.text.toString(),
                email = binding.evEmail.editText?.text.toString(),
                phone = binding.evPhoneNumber.editText?.text.toString(),
                website = binding.evWebsite.editText?.text.toString(),
                thumbnail = null
            )

            viewModel.saveUser(user)
            Toast.makeText(context,viewModel.message.value,Toast.LENGTH_SHORT).show()
            this.dismiss()
        }
    }

}