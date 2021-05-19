package com.example.allamvizsga_2020_21.main.Menu.Profile.addPerson

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import com.example.allamvizsga_2020_21.R
import com.example.allamvizsga_2020_21.Utils.ConnectionChecker
import com.example.allamvizsga_2020_21.Utils.LoadingSwitch
import com.google.android.material.textfield.TextInputEditText
import kotlinx.coroutines.Dispatchers

class AddPersonFragment : Fragment(), AddPersonContract.View {

    private val presenter: AddPersonContract.Presenter = AddPersonPresenter(this)

    private lateinit var loadingLayout: ConstraintLayout
    private lateinit var currentLayout: ConstraintLayout

    private lateinit var feedbackTextView: TextView
    private lateinit var selectButton: Button
    private lateinit var selectedImage: ImageView
    private lateinit var personName: TextInputEditText
    private lateinit var uploadButton: Button

    private val PICTURE_REQUEST_CODE = 100
    private var selectedPicture: Uri = Uri.EMPTY

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_add_person, container, false)
    }

    override fun onResume() {
        super.onResume()

        loadingLayout = requireActivity().findViewById(R.id.addPersonLoadingLayout)
        currentLayout = requireActivity().findViewById(R.id.addPersonLayout)

        feedbackTextView = requireActivity().findViewById(R.id.addPersonProcessTextView)
        selectButton = requireActivity().findViewById(R.id.selectImageFromGalleryButton)
        selectedImage = requireActivity().findViewById(R.id.chosenImageImageView)
        personName = requireActivity().findViewById(R.id.selectedPersonNameTextInputEdittext)
        uploadButton = requireActivity().findViewById(R.id.uploadButton)

        selectButton.setOnClickListener {
            openGalleryForImage()
        }
        uploadButton.setOnClickListener {
            if (ConnectionChecker(requireContext()).isConnected()) {
                Dispatchers.Main.run {
                    LoadingSwitch().showLoading(currentLayout, loadingLayout)
                }

                Dispatchers.IO.run {
                    presenter.addPerson(
                        selectedPicture,
                        personName.text.toString()
                    )
                }
            } else {
                presenter.connectionError()
            }
        }
    }

    private fun openGalleryForImage() {
        val intent = Intent()
        intent.action = Intent.ACTION_PICK
        intent.type = "image/*"
        startActivityForResult(intent, PICTURE_REQUEST_CODE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == PICTURE_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            selectedPicture = data!!.data!!
            selectedImage.setImageURI(selectedPicture)
        }
    }

    override fun uploadSuccess() {
        feedbackTextView.text = ""
        LoadingSwitch().stopLoading(loadingLayout, currentLayout)
    }

    override fun uploadFail(error: String) {
        feedbackTextView.text = error
        LoadingSwitch().stopLoading(loadingLayout, currentLayout)
    }
}