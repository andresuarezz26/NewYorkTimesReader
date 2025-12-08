package com.newyorktimesreader.presentation.common.widget

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NYTTopBar(onBack: (() -> Unit)? = null) {
  TopAppBar(
    title = {
      Text("Top Stories",
        style = TextStyle(
          fontSize = 20.sp,
          fontWeight = FontWeight.Bold,
          fontFamily = FontFamily.Serif
        ))
    },
    navigationIcon = {
      onBack?.let {
        IconButton(onClick = { it() }) {
          Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
        }
      }
    },
  )
}

@Preview
@Composable
fun NYTTopBarPreview() {
  NYTTopBar(onBack = {})
}
