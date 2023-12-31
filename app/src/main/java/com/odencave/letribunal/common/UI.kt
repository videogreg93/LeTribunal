package com.odencave.letribunal.common

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp

@Composable
fun Title(text: String) {
    Text(
        text = text,
        modifier = Modifier.padding(10.dp),
        style = TextStyle(
            color = Color.Black,
            fontSize = TextUnit(value = 32.0F, type = TextUnitType.Sp)
        ),
        fontWeight = FontWeight.Black
    )
}