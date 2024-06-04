package com.example.movieui.module.moviepass

import android.graphics.Bitmap
import android.graphics.Color
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.movieui.R // Adjust the import based on your project structure
import com.example.movieui.core.route.AppRouteName
import com.example.movieui.core.theme.LightGray
import com.example.movieui.core.theme.Yellow
import com.google.zxing.BarcodeFormat
import com.google.zxing.qrcode.QRCodeWriter
import java.time.LocalDate

@Composable
fun MoviePassScreen(
    navController: NavController,
    seatNumbers: List<String>,
    selectedDate: LocalDate?,
    selectedTime: String?
) {
    Scaffold(
        backgroundColor = LightGray
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()

        ) {
            Row(
                modifier = Modifier.padding(
                    horizontal = 16.dp, vertical = 8.dp
                ),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                IconButton(onClick = {
                    navController.popBackStack()
                }) {
                    Icon(Icons.Default.ArrowBack, contentDescription = "Back Button")
                }
                Spacer(modifier = Modifier.width(16.dp))
                Text(text = "Movie Pass", style = MaterialTheme.typography.h6)
            }
            Spacer(modifier = Modifier.height(24.dp))

            Box(
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(16.dp)
                    .background(color = androidx.compose.ui.graphics.Color.White, shape = RoundedCornerShape(16.dp))
                    .fillMaxWidth(0.8f)
                    .aspectRatio(1.5f),
                contentAlignment = Alignment.Center,
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                     Spacer(modifier = Modifier.height(8.dp))
                    Text("Seat Numbers: ${seatNumbers.joinToString(", ")}", style = MaterialTheme.typography.body1)
                    Text("Date: $selectedDate", style = MaterialTheme.typography.body1)
                    Text("Time: $selectedTime", style = MaterialTheme.typography.body1)
                    Spacer(modifier = Modifier.height(16.dp))

                    val qrCodeBitmap = generateQRCodeBitmap("Seat Numbers: $seatNumbers, Date: $selectedDate, Time: $selectedTime")
                    qrCodeBitmap?.let {
                        Image(
                            bitmap = it.asImageBitmap(),
                            contentDescription = "QR Code",
                            modifier = Modifier.size(150.dp)
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(24.dp))
            Button(
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(16.dp)
                    .fillMaxWidth(0.6f)
                    .height(56.dp),
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = Yellow,
                ),
                shape = RoundedCornerShape(32.dp),
                onClick = {
                    navController.navigate(AppRouteName.Home) {
                        popUpTo(AppRouteName.Home) { inclusive = true }
                    }
                },
            ) {
                Text("Home")
            }
        }
    }
}

fun generateQRCodeBitmap(text: String): Bitmap? {
    return try {
        val writer = QRCodeWriter()
        val bitMatrix = writer.encode(text, BarcodeFormat.QR_CODE, 512, 512)
        val width = bitMatrix.width
        val height = bitMatrix.height
        val bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.RGB_565)
        for (y in 0 until height) {
            for (x in 0 until width) {
                bitmap.setPixel(x, y, if (bitMatrix[x, y]) Color.BLACK else Color.WHITE)
            }
        }
        bitmap
    } catch (e: Exception) {
        e.printStackTrace()
        null
    }
}
