package com.thales.fokus.ui.screens

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material.icons.outlined.Lock
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.thales.fokus.ui.theme.InputBackground
import com.thales.fokus.ui.theme.PurplePrimary
import com.thales.fokus.ui.theme.TextGray
import com.thales.fokus.ui.viewmodel.AuthState
import com.thales.fokus.ui.viewmodel.AuthViewModel

@Composable
fun LoginScreen(
    viewModel: AuthViewModel,
    onLoginSuccess: () -> Unit,
    onNavigateToRegister: () -> Unit
) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var passwordVisible by remember { mutableStateOf(false) }

    // Estado para controlar a janelinha de recuperação de senha
    var showResetDialog by remember { mutableStateOf(false) }
    var resetEmail by remember { mutableStateOf("") }

    val authState by viewModel.authState.collectAsState()
    val context = LocalContext.current

    LaunchedEffect(authState) {
        when (authState) {
            is AuthState.Success -> {
                viewModel.resetState()
                onLoginSuccess()
            }
            is AuthState.Error -> {
                Toast.makeText(context, (authState as AuthState.Error).message, Toast.LENGTH_LONG).show()
                viewModel.resetState()
            }
            else -> {}
        }
    }


    if (showResetDialog) {
        AlertDialog(
            onDismissRequest = { showResetDialog = false },
            title = { Text("Recuperar Senha") },
            text = {
                Column {
                    Text("Digite seu e-mail para receber o link de redefinição:")
                    Spacer(modifier = Modifier.height(8.dp))
                    OutlinedTextField(
                        value = resetEmail,
                        onValueChange = { resetEmail = it },
                        label = { Text("E-mail") },
                        singleLine = true,
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            },
            confirmButton = {
                TextButton(onClick = {
                    viewModel.resetPassword(resetEmail)
                    showResetDialog = false
                }) {
                    Text("Enviar", color = PurplePrimary)
                }
            },
            dismissButton = {
                TextButton(onClick = { showResetDialog = false }) {
                    Text("Cancelar", color = TextGray)
                }
            },
            containerColor = Color.White
        )
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Icon(Icons.Outlined.Lock, contentDescription = "Logo", tint = Color.Black, modifier = Modifier.size(48.dp))
        Spacer(modifier = Modifier.height(24.dp))
        Text("Bem-vindo de volta", fontSize = 24.sp, fontWeight = FontWeight.Bold, color = Color.Black)
        Text("Entre na sua conta para continuar", fontSize = 14.sp, color = TextGray, modifier = Modifier.padding(top = 8.dp))
        Spacer(modifier = Modifier.height(32.dp))

        Text("Email", fontSize = 14.sp, fontWeight = FontWeight.SemiBold, modifier = Modifier.fillMaxWidth().padding(bottom = 4.dp))
        OutlinedTextField(
            value = email, onValueChange = { email = it }, placeholder = { Text("Digite seu email", color = TextGray) },
            modifier = Modifier.fillMaxWidth(), shape = RoundedCornerShape(12.dp), singleLine = true,
            colors = OutlinedTextFieldDefaults.colors(unfocusedContainerColor = InputBackground, focusedContainerColor = Color.White, unfocusedBorderColor = Color.Transparent, focusedBorderColor = PurplePrimary)
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text("Senha", fontSize = 14.sp, fontWeight = FontWeight.SemiBold, modifier = Modifier.fillMaxWidth().padding(bottom = 4.dp))
        OutlinedTextField(
            value = password, onValueChange = { password = it }, placeholder = { Text("********", color = TextGray) },
            modifier = Modifier.fillMaxWidth(), shape = RoundedCornerShape(12.dp), singleLine = true,
            visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            trailingIcon = {
                val image = if (passwordVisible) Icons.Filled.Visibility else Icons.Filled.VisibilityOff
                IconButton(onClick = { passwordVisible = !passwordVisible }) {
                    Icon(imageVector = image, contentDescription = null, tint = TextGray)
                }
            },
            colors = OutlinedTextFieldDefaults.colors(unfocusedContainerColor = InputBackground, focusedContainerColor = Color.White, unfocusedBorderColor = Color.Transparent, focusedBorderColor = PurplePrimary)
        )


        Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.CenterEnd) {
            TextButton(onClick = {
                resetEmail = email
                showResetDialog = true
            }) {
                Text("Esqueceu a senha?", color = Color.Black, fontSize = 12.sp, fontWeight = FontWeight.Bold)
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        Button(
            onClick = { viewModel.login(email, password) },
            modifier = Modifier.fillMaxWidth().height(50.dp),
            shape = RoundedCornerShape(12.dp), colors = ButtonDefaults.buttonColors(containerColor = PurplePrimary),
            enabled = authState !is AuthState.Loading
        ) {
            if (authState is AuthState.Loading) CircularProgressIndicator(color = Color.White, modifier = Modifier.size(24.dp))
            else Text("Entrar", fontSize = 16.sp, fontWeight = FontWeight.Bold, color = Color.White)
        }

        Spacer(modifier = Modifier.weight(1f))

        Row(verticalAlignment = Alignment.CenterVertically) {
            Text("Não tem uma conta? ", color = TextGray, fontSize = 14.sp)
            TextButton(onClick = onNavigateToRegister) {
                Text("Criar conta", color = Color.Black, fontWeight = FontWeight.Bold)
            }
        }
    }
}