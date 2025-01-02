package com.example.uikit.loginUiKit

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.Card
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.uikit.theme.BrushShadow
import com.example.uikit.theme.ColorBottomLogging
import com.example.uikit.theme.ColorMainGreen
import com.example.uikit.theme.MontserratMedium
import com.example.uikit.theme.MontserratRegular
import com.example.uikit.theme.TextColorLogging


@Composable
fun Logo(
    modifier: Modifier = Modifier,
    resourceId: Int,
    size: Dp = 100.dp,
) {
    Box(
        modifier = modifier
            .border(
                width = 1.dp,
                brush = BrushShadow,
                shape = CircleShape
            )
            .shadow(10.dp, CircleShape, spotColor = Color.Black, ambientColor = Color.Black)

            .clip(CircleShape)


    ) {
        Image(
            modifier = Modifier
                .size(size)

                .shadow(20.dp, CircleShape, spotColor = Color.Black, ambientColor = Color.Black),
            painter = painterResource(id = resourceId),
            contentDescription = null,
            contentScale = ContentScale.Fit,
        )
    }
}




@Composable
 fun LoggingTextField(
    modifier: Modifier = Modifier,
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    trailingIcon: @Composable (() -> Unit)? = null,
    leadingIcon: @Composable (() -> Unit),
) {

    OutlinedTextField(
        modifier = modifier
            .fillMaxWidth(),
        value = value,
        onValueChange = onValueChange,
        visualTransformation = visualTransformation,
        trailingIcon = trailingIcon,
        leadingIcon = leadingIcon,
        placeholder = {
            Text(
                text = label,
                color = TextColorLogging,
                fontSize = 17.sp,
                fontFamily = MontserratRegular
            )
        },
        colors = TextFieldDefaults.colors(
            cursorColor = ColorMainGreen,
            focusedIndicatorColor = Color.White,
            unfocusedIndicatorColor = Color.White.copy(0.3f),
            focusedContainerColor = Color.White,
            unfocusedContainerColor = Color.White,
            focusedLabelColor = ColorMainGreen,
            unfocusedLabelColor = ColorMainGreen,
            focusedTrailingIconColor = ColorMainGreen,
            unfocusedTrailingIconColor = ColorMainGreen,
            focusedTextColor = ColorMainGreen,
            unfocusedTextColor = ColorMainGreen,
            errorCursorColor = ColorMainGreen,
            errorIndicatorColor = ColorMainGreen,
            errorLabelColor = TextColorLogging,
            errorLeadingIconColor = TextColorLogging,
            errorTrailingIconColor = TextColorLogging,
            errorTextColor = TextColorLogging,
            disabledContainerColor = Color.White,
            disabledIndicatorColor = Color.White,
            disabledLabelColor = Color.White,
            disabledLeadingIconColor = Color.White,
            disabledTrailingIconColor = ColorMainGreen,
            disabledTextColor = Color.White,
        ),
        singleLine = true,
        textStyle = TextStyle(
            fontSize = 17.sp,
            fontFamily = MontserratRegular
        )
    )


}

@Composable
 fun LoggingBottoms(
    modifier: Modifier = Modifier,
    collBack: () -> Unit,
) {
    Button(
        modifier = modifier,
        onClick = collBack,
        colors = ButtonColors(
            containerColor = Color.Transparent,
            contentColor = Color.White,
            disabledContainerColor = ColorBottomLogging,
            disabledContentColor = Color.White
        ),
        border = BorderStroke(width = 1.dp, color = Color.White),
        shape = RoundedCornerShape(10.dp)
    ) {
        Text(text = "Loding", color = Color.White, fontSize = 20.sp, fontFamily = MontserratMedium)
    }

}



