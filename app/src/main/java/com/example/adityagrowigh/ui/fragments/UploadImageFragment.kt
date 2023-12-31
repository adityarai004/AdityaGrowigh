package com.example.adityagrowigh.ui.fragments

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import android.widget.ImageView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import com.example.adityagrowigh.R
import com.example.adityagrowigh.ui.WelcomeActivity

class UploadImageFragment : Fragment() {
    lateinit var selectImageButton: Button
    lateinit var selectedImage: ImageView
    lateinit var backButton: ImageButton
    private val pickImageLauncher =
        registerForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
            if (uri != null) {
                selectedImage.setImageURI(uri)
            }
        }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_upload_image, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        selectedImage = view.findViewById(R.id.imageView)
        selectImageButton = view.findViewById(R.id.select_image_btn)
        backButton = view.findViewById(R.id.back_btn)
        selectImageButton.setOnClickListener {
            openGallery()
        }
        backButton.setOnClickListener {
            val i = Intent(activity, WelcomeActivity::class.java)
            startActivity(i)
            requireActivity().finish()
        }
    }

    private fun openGallery() {
        pickImageLauncher.launch("image/*")
    }

}