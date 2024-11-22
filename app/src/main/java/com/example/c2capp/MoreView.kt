package com.example.c2capp

// BibleView.kt
// Created by mac on 01.05.24

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun MoreView() {
    Text(text = "Hello, World!")
}

@Preview(showBackground = true)
@Composable
fun PreviewMoreView() {
    MoreView()
}
