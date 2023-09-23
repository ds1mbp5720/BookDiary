package com.lee.bookdiary.dialog

import android.os.Bundle
import android.view.View
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
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
) : BaseDialogFragment<DialogMessageBinding, DialogMessageViewModel>() {
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

@Composable
fun FirstComposeTestDialog() {
    AlertDialog(
        onDismissRequest = {/*todo*/},
        confirmButton = {
            Button(onClick = {/*todo*/}){
                Text(text = "확인")
            }
        },
        dismissButton = {
            Button(onClick = {/*todo*/}){
                Text(text = "취소")
            }
        },
        title = {
            Text(text = "테스트 다이얼로그 제목")
        },
        text = {
            Text(text = "내용")
        }
    )
}

@Preview
@Composable
fun testDialog(){
    FirstComposeTestDialog()
}
