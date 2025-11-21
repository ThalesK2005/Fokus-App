package com.thales.fokus.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.thales.fokus.ui.theme.*
import com.thales.fokus.ui.viewmodel.TaskViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddTaskScreen(
    viewModel: TaskViewModel,
    taskId: Int = -1,
    onNavigateBack: () -> Unit
) {
    // Estados do formulário
    var title by remember { mutableStateOf("") }
    var date by remember { mutableStateOf("") }
    var time by remember { mutableStateOf("") }
    var selectedCategory by remember { mutableStateOf("Trabalho") }


    var originalTask by remember { mutableStateOf<com.thales.fokus.model.Task?>(null) }


    LaunchedEffect(taskId) {
        if (taskId != -1) {
            val task = viewModel.getTaskById(taskId)
            if (task != null) {
                originalTask = task
                title = task.title
                date = task.date
                time = task.time
                selectedCategory = task.category
            }
        }
    }

    val categories = listOf(
        "Trabalho" to CategoryWork,
        "Pessoal" to CategoryPersonal,
        "Reuniões" to CategoryMeeting,
        "Planos" to CategoryPlanning
    )

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = if (taskId == -1) "Nova Tarefa" else "Editar Tarefa",
                        fontWeight = FontWeight.Bold
                    )
                },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Voltar")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.White)
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .padding(16.dp)
                .fillMaxSize()
                .background(Color.White)
        ) {
            Text("Título", fontWeight = FontWeight.SemiBold)
            OutlinedTextField(
                value = title,
                onValueChange = { title = it },
                modifier = Modifier.fillMaxWidth(),
                placeholder = { Text("Ex: Reunião de equipe") },
                shape = RoundedCornerShape(12.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    unfocusedContainerColor = InputBackground,
                    focusedContainerColor = Color.White,
                    unfocusedBorderColor = Color.Transparent
                )
            )

            Spacer(modifier = Modifier.height(16.dp))

            Text("Categoria", fontWeight = FontWeight.SemiBold)
            Spacer(modifier = Modifier.height(8.dp))
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                categories.forEach { (name, color) ->
                    val isSelected = selectedCategory == name
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(8.dp))
                            .background(if (isSelected) color.copy(alpha = 0.15f) else InputBackground)
                            .border(if (isSelected) 2.dp else 0.dp, if (isSelected) color else Color.Transparent, RoundedCornerShape(8.dp))
                            .clickable { selectedCategory = name }
                            .padding(horizontal = 12.dp, vertical = 8.dp)
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Box(modifier = Modifier.size(12.dp).clip(CircleShape).background(color))
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(name, fontSize = 12.sp, fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal, color = if (isSelected) color else TextGray)
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            Text("Data", fontWeight = FontWeight.SemiBold)
            OutlinedTextField(
                value = date,
                onValueChange = { date = it },
                modifier = Modifier.fillMaxWidth(),
                placeholder = { Text("Ex: 20/10/2025") },
                shape = RoundedCornerShape(12.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    unfocusedContainerColor = InputBackground, focusedContainerColor = Color.White, unfocusedBorderColor = Color.Transparent
                )
            )

            Spacer(modifier = Modifier.height(16.dp))

            Text("Hora", fontWeight = FontWeight.SemiBold)
            OutlinedTextField(
                value = time,
                onValueChange = { time = it },
                modifier = Modifier.fillMaxWidth(),
                placeholder = { Text("Ex: 14:00") },
                shape = RoundedCornerShape(12.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    unfocusedContainerColor = InputBackground, focusedContainerColor = Color.White, unfocusedBorderColor = Color.Transparent
                )
            )

            Spacer(modifier = Modifier.height(24.dp))

            Button(
                onClick = {
                    if (title.isNotEmpty()) {
                        if (taskId == -1) {

                            viewModel.addTask(title, date, time, selectedCategory)
                        } else {

                            originalTask?.let { task ->
                                val updatedTask = task.copy(
                                    title = title,
                                    date = date,
                                    time = time,
                                    category = selectedCategory
                                )
                                viewModel.updateTask(updatedTask)
                            }
                        }
                        onNavigateBack()
                    }
                },
                modifier = Modifier.fillMaxWidth().height(50.dp),
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.buttonColors(containerColor = PurplePrimary)
            ) {
                Text(
                    text = if (taskId == -1) "Salvar Tarefa" else "Salvar Alterações",
                    fontSize = 16.sp, fontWeight = FontWeight.Bold
                )
            }
        }
    }
}