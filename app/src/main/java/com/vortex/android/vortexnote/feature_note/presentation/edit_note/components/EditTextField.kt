package com.vortex.android.vortexnote.feature_note.presentation.edit_note.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusState
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import com.vortex.android.vortexnote.R
import androidx.compose.ui.graphics.Color

@Composable
fun TransparentHintEditTextField(
    text: String,
    hintInt: Int,
    modifier: Modifier = Modifier,
    isHintVisible: Boolean = true,
    textStyle: TextStyle = TextStyle(),
    singleLine: Boolean = false,
    onValueChange: (String) -> Unit,
    onFocusChange: (FocusState) -> Unit
) {
    Box(
        modifier = modifier
    ) {
        BasicTextField(
            value = text,
            onValueChange = onValueChange,
            singleLine = singleLine,
            textStyle = textStyle,
            modifier = Modifier
                .fillMaxWidth()
                .onFocusChanged {
                    onFocusChange(it)
                }
        )
        if (isHintVisible){
            Text(
                text = stringResource(hintInt),
                style = textStyle,
                color = Color.DarkGray
            )
        }
    }
}

@Preview
@Composable
fun TransparentHintEditTextFieldPreview() {
    TransparentHintEditTextField(
        text = "Test Text",
        hintInt = R.string.invalid_note_message_hint,
        onFocusChange = { },
        onValueChange = { }
    )
}