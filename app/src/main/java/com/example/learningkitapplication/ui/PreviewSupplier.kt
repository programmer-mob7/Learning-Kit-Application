package com.example.learningkitapplication.ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.example.learningkitapplication.SupplierScreen
import com.example.learningkitapplication.suppliers
import com.example.learningkitapplication.ui.theme.LearningKitApplicationTheme

@Preview(showBackground = true)
@Composable
fun PreviewSupplierScreen() {
    LearningKitApplicationTheme {
        SupplierScreen()
    }
}