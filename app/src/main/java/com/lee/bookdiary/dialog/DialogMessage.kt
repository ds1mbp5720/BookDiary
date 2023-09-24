package com.lee.bookdiary.dialog

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.compose.BackHandler
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.core.view.isVisible
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import com.lee.bookdiary.BR
import com.lee.bookdiary.R
import com.lee.bookdiary.base.BaseDialogFragment
import com.lee.bookdiary.databinding.DialogMessageBinding
import com.lee.compose.resource.theme.*

data class DialogContent( // 다이얼로그 기본 구성요소 data class
    val title : String = "",
    val msg: String = "",
    val rightBtn: String = "",
    val leftBtn: String = "",
    val isCancel : Boolean = true,
    val withClose : Boolean = false,
    val rightClickAction: () -> Unit = {},
    val leftClickAction: () -> Unit = {}
)

class DialogMessage(
    private val msg: String = "",
    private val rightBtn: String,
    private val leftBtn: String = "",
    private val isCancel : Boolean = true,
    private val withClose : Boolean = false,
    private val title : String = "",
    private var rightClickAction: () -> Unit = {},
    private var leftClickAction: () -> Unit = {}
) : DialogFragment(){
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return ComposeView(requireContext()).apply {
            setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed)
            setContent {
                OpenDialog(content = DialogContent(title, msg, rightBtn, leftBtn, isCancel, withClose, rightClickAction, leftClickAction))
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }
    /*BaseDialogFragment<DialogMessageBinding, DialogMessageViewModel>() {
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
    }*/
}

@Composable
fun OpenDialog(content: DialogContent){
    //todo 다이얼로그 사라짐, 배경은 뿌연게 유지됨
    val showDialog = remember { mutableStateOf(true) }
    if(showDialog.value){
        BaseDialogMessage(
            content = content,
            onDismissRequest = {showDialog.value = false }
        )
    }
    /*BackHandler {
        showDialog.value = false
    }*/
}

//todo ui 조정 필요
@Composable
fun BaseDialogMessage(
    content: DialogContent,
    onDismissRequest: () -> Unit
) {
    AlertDialog(
        onDismissRequest = { onDismissRequest.invoke() },
        confirmButton = {
            Button(
                onClick = { content.rightClickAction.invoke() },
                colors = ButtonDefaults.buttonColors(
                    containerColor = androidx.compose.ui.graphics.Color.Cyan,
                    contentColor = androidx.compose.ui.graphics.Color.Black
                )
            ){
                Text(text = content.rightBtn)
            }
        },
        dismissButton = {
            if(content.leftBtn.isNotEmpty()) {
                Button(
                    onClick = {onDismissRequest.invoke()}
                ){
                    Text(text = content.leftBtn)
                }
            } else null
        },
        title = {
            Text(text = content.title)
        },
        text = {
            Text(text = content.msg)
        }
    )
}
