package com.example.movieui.module.account.presentation

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.movieui.R // Adjust the import based on your project structure
import com.example.movieui.core.route.AppRouteName
import com.example.movieui.core.theme.Yellow
import com.example.movieui.module.user.UserViewModel

@Composable
fun AccountCreationScreen(navController: NavHostController, userViewModel: UserViewModel) {
    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }
    var creationError by remember { mutableStateOf<String?>(null) }

    Scaffold(
        content = { padding ->
            Box(
                modifier = Modifier
                    .padding(padding)
                    .fillMaxSize()
                    .background(Color.White),
                contentAlignment = Alignment.Center
            ) {
                Image(
                    painter = painterResource(id = R.drawable.b1), // Replace with your image resource
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxSize()
                )
                Column(
                    modifier = Modifier
                        .padding(16.dp)
                        .background(Color(0x80000000), RoundedCornerShape(16.dp)) // Semi-transparent black background
                        .padding(24.dp)
                        .fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    TextField(
                        value = username,
                        onValueChange = { username = it },
                        label = { Text("Username") },
                        colors = TextFieldDefaults.textFieldColors(
                            backgroundColor = Color.White,
                            focusedIndicatorColor = Yellow,
                            unfocusedIndicatorColor = Color.Gray,
                            cursorColor = Yellow
                        ),
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(8.dp))
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    TextField(
                        value = password,
                        onValueChange = { password = it },
                        label = { Text("Password") },
                        visualTransformation = PasswordVisualTransformation(),
                        colors = TextFieldDefaults.textFieldColors(
                            backgroundColor = Color.White,
                            focusedIndicatorColor = Yellow,
                            unfocusedIndicatorColor = Color.Gray,
                            cursorColor = Yellow
                        ),
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(8.dp))
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    TextField(
                        value = confirmPassword,
                        onValueChange = { confirmPassword = it },
                        label = { Text("Confirm Password") },
                        visualTransformation = PasswordVisualTransformation(),
                        colors = TextFieldDefaults.textFieldColors(
                            backgroundColor = Color.White,
                            focusedIndicatorColor = Yellow,
                            unfocusedIndicatorColor = Color.Gray,
                            cursorColor = Yellow
                        ),
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(8.dp))
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    Button(
                        onClick = {
                            if (password == confirmPassword) {
                                userViewModel.createUser(username, password) { success ->
                                    if (success) {
                                        navController.navigate(AppRouteName.Login)
                                    } else {
                                        creationError = "Error creating account. Try again."
                                    }
                                }
                            } else {
                                creationError = "Passwords do not match"
                            }
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(50.dp),
                        colors = ButtonDefaults.buttonColors(
                            backgroundColor = Yellow,
                            contentColor = Color.White
                        ),
                        shape = RoundedCornerShape(32.dp)
                    ) {
                        Text("Create Account")
                    }
                    if (creationError != null) {
                        Text(
                            text = creationError!!,
                            color = MaterialTheme.colors.error,
                            modifier = Modifier.padding(top = 8.dp)
                        )
                    }
                }
            }
        }
    )
}
