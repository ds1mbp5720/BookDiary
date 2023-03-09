package com.lee.bookdiary.dialog

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import com.lee.bookdiary.R
import com.lee.bookdiary.BR
import com.lee.bookdiary.base.BaseDialogFragment
import com.lee.bookdiary.databinding.DialogMessageBinding


class DialogMessage(
    private val msg: String,
    private val rightBtn: String,
    private val leftBtn: String = "",
    private val isCancel : Boolean = true,
    private val withClose : Boolean = false,
    private val title : String = ""
) :
    BaseDialogFragment<DialogMessageBinding, DialogMessageViewModel>() {
    override val layoutId: Int = R.layout.dialog_message
    override val viewModel: DialogMessageViewModel by viewModels()
    private var leftClickAction = {}
    private var rightClickAction = {}

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        dataBinding.setVariable(BR.msg, msg)
        dataBinding.setVariable(BR.title , title)
        dataBinding.txtDialogTitle.isVisible = !title.isNullOrEmpty()
        dataBinding.txtDialogRight.text = rightBtn
        if (leftBtn.isNotEmpty()) {
            dataBinding.txtDialogLeft.text = leftBtn
            dataBinding.groupBtnOne.isVisible = true
        }
        if(!isCancel) isCancelable = isCancel
        dataBinding.ivClose.isVisible = withClose
    }

    override fun initObserve() {
        viewModel.leftClick.observe(this) {
            leftClickAction.invoke()
            dismiss()
        }
        viewModel.rightClick.observe(this) {
            rightClickAction.invoke()
            dismiss()
        }
        viewModel.closeClick.observe(this) {
            dismiss()
        }
    }

    fun onLeftBtn(action: () -> (Unit) = {}): DialogMessage {
        leftClickAction = action
        return this
    }

    fun onRightBtn(action: () -> (Unit) = {}): DialogMessage {
        rightClickAction = action
        return this
    }

}