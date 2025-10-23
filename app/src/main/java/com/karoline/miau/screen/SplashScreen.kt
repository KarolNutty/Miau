package com.karoline.miau.screen


import android.os.Handler
import android.os.Looper
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.karoline.miau.R

@Composable
fun SplashScreen(
    onTimeout: () -> Unit,
    backgroundColor: Color = Color(0xFFF6D3D9)
) {
    LaunchedEffect(Unit) {
        Handler(Looper.getMainLooper()).postDelayed({
            onTimeout()
        }, 3000)  // Timeout de 3 segundos
    }

    Box(
        modifier = Modifier
            .background(backgroundColor)
            .fillMaxSize()
            .systemBarsPadding()
            .padding(8.dp),


        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {

            Image(
                painter = painterResource(id = R.drawable.miau2),  // A imagem estática
                contentDescription = "Splash Image",
                modifier = Modifier.size(250.dp)  // Defina o tamanho desejado para a imagem
            )
        }
    }
}

@Preview
@Composable
fun SplashScreenPreview() {
    SplashScreen(onTimeout = {})
}

