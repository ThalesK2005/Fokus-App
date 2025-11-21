package com.thales.fokus.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Lock
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.thales.fokus.ui.theme.PurplePrimary
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(onNavigateToNext: (String) -> Unit) {

    LaunchedEffect(Unit) {
        delay(2000) // Espera 2 segundos (2000ms)


        if (Firebase.auth.currentUser != null) {
            onNavigateToNext("home")
        } else {
            onNavigateToNext("login")
        }
    }


    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(PurplePrimary),
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Icon(
                imageVector = Icons.Outlined.Lock,
                contentDescription = "Logo",
                tint = Color.White,
                modifier = Modifier.size(80.dp)
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = "Fokus",
                color = Color.White,
                fontSize = 32.sp,
                fontWeight = FontWeight.Bold
            )
        }
    }
}