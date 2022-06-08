package com.restauran.utils.dialogs

import android.content.DialogInterface
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.*
import androidx.fragment.app.DialogFragment
import com.restauran.R
import com.restauran.databinding.FragmentCustomDialogBinding
import com.restauran.databinding.FragmentCustomFloatDialogBinding


class CustomFloatDialog : DialogFragment() {
    private var title: String? = null
    private var msg: String? = null
    private var okMsg: String? = null
    private var cancelMsg: String? = null
    private var isCancel = true
    private var listener: OnClickListener? = null
    private var onCloseClick: OnCloseClick? = null
    private lateinit var binding: FragmentCustomFloatDialogBinding

    companion object {
        fun newInstance(
            title: String?,
            msg: String?,
            okMsg: String?,
            cancelMsg: String?,
            isCancelable: Boolean?
        ): CustomFloatDialog {
            var isCancelable = isCancelable
            val fragment = CustomFloatDialog()
            val args = Bundle()
            args.putString("title", title)
            args.putString("msg", msg)
            args.putString("okMsg", okMsg)
            args.putString("cancelMsg", cancelMsg)
            if (isCancelable == null) isCancelable = true
            args.putBoolean("isCancelable", isCancelable)
            fragment.arguments = args
            return fragment
        }

        fun newInstance(
            title: String?,
            msg: String?,
            okMsg: String?,
            cancelMsg: String?
        ): CustomFloatDialog {
            val fragment = CustomFloatDialog()
            val args = Bundle()
            args.putString("title", title)
            args.putString("msg", msg)
            args.putString("okMsg", okMsg)
            args.putString("cancelMsg", cancelMsg)
            args.putBoolean("isCancelable", TextUtils.isEmpty(cancelMsg))
            fragment.arguments = args
            return fragment
        }
    }

    private fun getArgumentsData() {
        title = arguments?.getString("title")
        msg = arguments?.getString("msg")
        okMsg = arguments?.getString("okMsg")
        cancelMsg = arguments?.getString("cancelMsg")
        isCancel = arguments?.getBoolean("isCancelable") == true
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NORMAL, R.style.BottomSheetDialog)

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCustomFloatDialogBinding.inflate(layoutInflater)
        val view: View = binding.root
        getArgumentsData()
        isCancelable = isCancel
        if (TextUtils.isEmpty(title)) {
            binding.tvTitle.visibility = View.GONE
        } else binding.tvTitle.visibility = View.VISIBLE

        if (TextUtils.isEmpty(okMsg)) {
            binding.btnOk.visibility = View.GONE
        }
        if (TextUtils.isEmpty(cancelMsg)) {
            binding.btnCancel.visibility = View.GONE
        }

        binding.tvTitle.text = title
        binding.btnOk.text = okMsg
        binding.btnCancel.text = cancelMsg
        binding.tvMsg.text = msg

        binding.btnOk.setOnClickListener {
            dismiss()
            if (listener != null) listener?.onOkClick() else if (onCloseClick != null) onCloseClick?.onCloseClick()
        }

        binding.btnCancel.setOnClickListener {
            dismiss()
            listener?.onCancelClick()
        }
        Log.e("onCreateView", title.toString())
        Log.e("onCreateView", msg.toString())
        Log.e("onCreateView", okMsg.toString())
        Log.e("onCreateView", cancelMsg.toString())
        Log.e("onCreateView", isCancel.toString())
        return view
    }

    override fun onResume() {
        val params: ViewGroup.LayoutParams? = dialog?.window?.attributes
        params?.height = WindowManager.LayoutParams.WRAP_CONTENT
        dialog?.window?.attributes = params as WindowManager.LayoutParams
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        isCancelable = false
        super.onResume()
        dialog?.setOnKeyListener { _: DialogInterface?, keyCode: Int, _: KeyEvent? ->
            if (keyCode == KeyEvent.KEYCODE_BACK) {
                dismiss()
                return@setOnKeyListener true
            } else return@setOnKeyListener false
        }
    }


    interface OnClickListener {
        fun onOkClick()
        fun onCancelClick()
    }

    interface OnCloseClick {
        fun onCloseClick()
    }

    fun setListener(listener: OnClickListener) {
        this.listener = listener
    }

    fun setCloseListener(listener: OnCloseClick?) {
        onCloseClick = listener
    }
}