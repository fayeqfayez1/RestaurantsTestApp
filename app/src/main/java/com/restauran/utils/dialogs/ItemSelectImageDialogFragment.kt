package com.restauran.utils.dialogs

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.restauran.R
import com.restauran.databinding.FragmentItemListDialogBinding

class ItemSelectImageDialogFragment : BottomSheetDialogFragment(), View.OnClickListener {
    companion object {
        private val ARG_DIALOG_TITLE = "title"
        private val ARG_DIALOG_TYPE = "type"

        fun newInstance(title: String, type: Int): ItemSelectImageDialogFragment {
            val fragment = ItemSelectImageDialogFragment()
            val args = Bundle()
            args.putString(ARG_DIALOG_TITLE, title)
            args.putInt(ARG_DIALOG_TYPE, type)
            fragment.arguments = args
            return fragment
        }
    }

    private var mListener: Listener? = null
    private lateinit var binding: FragmentItemListDialogBinding
    fun setListener(listener: Listener) {
        mListener = listener
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,

        savedInstanceState: Bundle?
    ): View {
        binding = FragmentItemListDialogBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.selectImageCamera.setOnClickListener(this)
        binding.selectImageGallery.setOnClickListener(this)
        val type = arguments?.getInt(ARG_DIALOG_TYPE)
        when (type) {
            1 -> {
                binding.selectImageGallery.visibility = View.VISIBLE
                binding.selectImageCamera.visibility = View.GONE
            }
            2 -> {
                binding.selectImageCamera.visibility = View.VISIBLE
                binding.selectImageGallery.visibility = View.VISIBLE
            }
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        val parent = parentFragment
        mListener = if (parent != null) parent as Listener
        else context as Listener
    }

    override fun onDetach() {
        super.onDetach()
        mListener = null
    }

    override fun onClick(v: View?) {
        when (view?.id) {
            R.id.select_image_camera -> if (mListener != null) {
                dismiss()
                mListener?.onCameraClicked()
            }
            R.id.select_image_gallery -> if (mListener != null) {
                dismiss()
                mListener?.onGalleryClicked()
            }
        }
    }

    interface Listener {
        fun onGalleryClicked()
        fun onCameraClicked()
        fun onVideoClicked()
    }
}