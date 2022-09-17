package com.itech.androidassessment.presentation.fragments.profile

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.FileProvider
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavArgs
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.itech.androidassessment.R
import com.itech.androidassessment.data.database.DatabaseUser
import com.itech.androidassessment.databinding.FragmentProfileBinding
import com.itech.androidassessment.utils.REQUEST_ID
import java.io.File
import java.io.IOException


class ProfileFragment : Fragment() {

    private lateinit var _binding:FragmentProfileBinding
    private val binding get() = _binding!!
    private val args:ProfileFragmentArgs by navArgs()
    private lateinit var currentUser: DatabaseUser
    private lateinit var photoPath: String

    private val viewModel by lazy {
        ViewModelProvider(this).get(ProfileViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentProfileBinding.inflate(inflater, container, false)

        val uId = args.id

         viewModel.getUserDB(uId)
        viewModel.user.observe(viewLifecycleOwner) {
            currentUser = it!!
            Log.d("USER", "============>>>>>>>>> ${it}")
            binding.tvFullname.text = it?.name
            binding.tvEmail.text = it?.email
            binding.tvUsername.text = it?.username
            binding.tvPhone.text = it?.phone
            binding.tvWebsite.text = it?.website
            Glide
                .with(this)
                .load(it?.thumbnail)
                .centerCrop()
                .placeholder(R.drawable.ic_profile_img_placeholder)
                .into(binding.profileImage)
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.profileImage.setOnClickListener {
            var intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)

            if (intent.resolveActivity(requireActivity().packageManager) != null){
                var photoFile: File? = null

                try {
                    photoFile = createImgFile()
                }catch (e:IOException){

                }
                if (photoFile != null){
                    val photoUri = FileProvider.getUriForFile(
                        requireContext(),
                        "com.example.android.fileprovider",
                        photoFile
                    )
                    intent.putExtra(MediaStore.EXTRA_OUTPUT,photoUri)
                    startActivityForResult(intent, REQUEST_ID)
                }
            }

        }
    }

    private fun createImgFile(): File? {
        val filename = "mypicture"
        val storageDir = requireActivity().getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        val image = File.createTempFile(
            filename,
            ".jpg",
            storageDir
        )
        photoPath= image.absolutePath
        return image
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        //super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == REQUEST_ID && resultCode == Activity.RESULT_OK) {
            //var bmp = data?.extras?.get("data") as Bitmap
            Glide
                .with(this)
                .load(Uri.parse(photoPath))
                .centerCrop()
                .placeholder(R.drawable.ic_profile_img_placeholder)
                .into(binding.profileImage)

            currentUser.thumbnail = photoPath
            viewModel.updateUser(currentUser)
            Toast.makeText(context,viewModel.message.value,Toast.LENGTH_SHORT).show()
        }
    }

}