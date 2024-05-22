package com.compose.samples.replicas.crane.base

import androidx.annotation.DrawableRes
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.compose.samples.replicas.crane.R
import com.compose.samples.replicas.crane.ui.theme.CraneTheme
import com.compose.samples.replicas.crane.ui.theme.captionTextStyle

@Composable
fun SimpleUserInput(
    text: String? = null,
    caption: String? = null,
    @DrawableRes vectorImageId: Int? = null
) {
    CraneUserInput(
        caption = if(text == null) caption else null,
        text = text ?: "",
        vectorImageId = vectorImageId
    )
}

@Composable
fun CraneUserInput(
    text: String,
    modifier: Modifier = Modifier,
    onClick: () -> Unit = { },
    caption: String? = null,
    @DrawableRes vectorImageId: Int? = null,
    tint: Color = LocalContentColor.current
) {
    CraneBaseUserInput(
        modifier = modifier,
        onClick = onClick,
        caption = caption,
        vectorImageId = vectorImageId,
        tintIcon = { text.isNotEmpty() },
        tint = tint
    ) {
        Text(
            text = text,
            style = MaterialTheme.typography.bodyMedium.copy(color = tint)
        )
    }
}

//@Preview(showSystemUi = true)
@Composable
fun CraneUserInputPreview() {
    CraneTheme {
        SimpleUserInput(
            text = "Crane User Input",
            caption = "Hey",
            vectorImageId = R.drawable.ic_hotel
        )
    }
}


@Composable
fun CraneEditableUserInput(
    hint: String,
    caption: String? = null,
    @DrawableRes vectorImageId: Int? = null,
    onInputChanged: (String) -> Unit
) {

    var textFieldState by rememberSaveable(stateSaver = TextFieldValue.Companion.Saver) {
        mutableStateOf(
            TextFieldValue()
        )
    }

    CraneBaseUserInput(
        caption = caption,
        tintIcon = {
            textFieldState.text.isNotEmpty()
        },
        showCaption = {
            textFieldState.text.isNotEmpty()
        },
        vectorImageId = vectorImageId
    ) {
        BasicTextField(
            value = textFieldState,
            onValueChange = {
                textFieldState = it
                onInputChanged(textFieldState.text)
            },
            textStyle = MaterialTheme.typography.bodySmall.copy(color = LocalContentColor.current),
            cursorBrush = SolidColor(LocalContentColor.current),
            decorationBox = { innerTextField ->
                if(hint.isNotEmpty() && textFieldState.text.isEmpty()) {
                    Text(
                        text = hint,
                        style = captionTextStyle.copy(color = LocalContentColor.current)
                    )
                }
                innerTextField()
            }
        )
    }
}

//@Preview(showSystemUi = true)
@Composable
fun CraneEditableUserInputPreview() {
    CraneTheme(
        dynamicColor = false
    ) {
        CraneEditableUserInput(
            vectorImageId = R.drawable.ic_crane_drawer,
            hint = "Hint",
            caption = "Capt",
            onInputChanged = {  }
        )
    }
}

@Composable
fun CraneBaseUserInput(
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {},
    caption: String? = null,
    @DrawableRes vectorImageId: Int? = null,
    showCaption: () -> Boolean = { true },
    tintIcon: () -> Boolean = { false },
    tint: Color = LocalContentColor.current,
    content: @Composable () -> Unit
) {
    Surface(
        modifier = modifier,
        onClick = onClick,
        color = MaterialTheme.colorScheme.primaryContainer
    ) {
        Row(
            modifier = Modifier.padding(all = 12.dp)
        ) {
            if(vectorImageId != null) {
                Icon(
                    modifier = Modifier.size(24.dp, 24.dp),
                    painter = painterResource(id = vectorImageId),
                    tint = if(tintIcon()) tint else Color(0x80FFFFFF),
                    contentDescription = null
                )
                Spacer(modifier = Modifier.width(8.dp))
            }
            if(caption != null && showCaption()) {
                Text(
                    modifier = Modifier.align(Alignment.CenterVertically),
                    text = caption,
                    style = (captionTextStyle).copy(color = tint)
                )
                Spacer(modifier = Modifier.width(8.dp))
            }
            Row(
                modifier = Modifier
                    .weight(1f)
                    .align(Alignment.CenterVertically)
            ) {
                content()
            }
        }
    }
}

//@Preview(showSystemUi = true)
@Composable
fun CraneBaseUserInputPreview() {
    CraneTheme(
        dynamicColor = false
    ) {
        Surface {
            CraneBaseUserInput(
                vectorImageId = R.drawable.ic_crane_drawer,
                caption = "Hello"
            ) {
                Text(text = "text", style = MaterialTheme.typography.bodySmall)
            }
        }
    }
}