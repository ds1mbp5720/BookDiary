package com.lee.bookdiary.dialog

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.border
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
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
    private val title: String = "",
    private val rightBtn: String,
    private val leftBtn: String = "",
    private val isCancel : Boolean = true,
    private val withClose : Boolean = false,
    private val msg : String = "",
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
                OpenDialog(
                    content = DialogContent(title, msg, rightBtn, leftBtn, isCancel, withClose, rightClickAction, leftClickAction),
                    dismissAction = {this@DialogMessage.dismiss()}
                    )
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }
}

@Composable
fun OpenDialog(content: DialogContent, dismissAction: ()-> Unit){
    //todo 다이얼로그 사라짐, 배경은 뿌연게 유지됨
    val showDialog = remember { mutableStateOf(true) }
    if(showDialog.value){
        BaseDialogMessage(
            content = content,
            onDismissRequest = {showDialog.value = false }
        )
    } else dismissAction.invoke() // 기존 fragment에서 제거하려면 해당 fragment class 를 dismiss하도록 해야함
}

//todo ui 조정 필요
@Composable
fun BaseDialogMessage(
    content: DialogContent,
    onDismissRequest: () -> Unit
) {
    AlertDialog(
        modifier = Modifier.border(
            width = 3.dp,
            color = Teal700,
            shape = RectangleShape
        ),
        onDismissRequest = { onDismissRequest.invoke() },
        confirmButton = {
            Button(
                modifier = Modifier.border(
                    width = 3.dp,
                    color = Teal700,
                    shape = CircleShape
                ),
                onClick = {
                    content.rightClickAction.invoke()
                    onDismissRequest.invoke()
                          },
                colors = ButtonDefaults.buttonColors(
                    containerColor = White,
                    contentColor = Black
                )
            ){
                Text(
                    text = content.rightBtn,
                    fontSize = 20.sp
                )
            }
        },
        dismissButton = {
            if(content.leftBtn.isNotEmpty()) {
                Button(
                    modifier = Modifier.border(
                        width = 3.dp,
                        color = Teal700,
                        shape = CircleShape
                    ),
                    onClick = {onDismissRequest.invoke()},
                    colors = ButtonDefaults.buttonColors(
                        containerColor = White,
                        contentColor = Black
                    )
                ){
                    Text(
                        text = content.leftBtn,
                        fontSize = 20.sp
                    )
                }
            } else null
        },
        title = {
            Text(
                text = content.title,
                fontSize = 24.sp,
                fontStyle = FontStyle.Italic
            )
        },
        text = {
            Text(
                text = content.msg,
                fontSize = 20.sp,
                fontStyle = FontStyle.Normal
            )
        },
        containerColor = White
    )
}
