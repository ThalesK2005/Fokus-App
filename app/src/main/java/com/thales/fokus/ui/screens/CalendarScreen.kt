package com.thales.fokus.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.thales.fokus.model.Task
import com.thales.fokus.ui.theme.*
import com.thales.fokus.ui.viewmodel.TaskViewModel
import java.time.LocalDate
import java.time.YearMonth
import java.time.format.DateTimeFormatter
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CalendarScreen(
    viewModel: TaskViewModel,
    onBack: () -> Unit
) {
    val tasks by viewModel.tasks.collectAsState()


    var currentMonth by remember { mutableStateOf(YearMonth.now()) }

    Scaffold(
        topBar = {
            Column {

                TopAppBar(
                    title = {
                        Row(verticalAlignment = Alignment.CenterVertically) {

                            Text(
                                text = currentMonth.format(DateTimeFormatter.ofPattern("MMMM yyyy", Locale("pt", "BR")))
                                    .replaceFirstChar { it.uppercase() },
                                fontWeight = FontWeight.Bold,
                                fontSize = 20.sp
                            )
                            Spacer(modifier = Modifier.width(8.dp))

                            IconButton(onClick = { currentMonth = currentMonth.minusMonths(1) }) {
                                Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Mês Anterior", modifier = Modifier.size(20.dp))
                            }
                            IconButton(onClick = { currentMonth = currentMonth.plusMonths(1) }) {
                                Icon(Icons.AutoMirrored.Filled.ArrowForward, contentDescription = "Próximo Mês", modifier = Modifier.size(20.dp))
                            }
                        }
                    },
                    navigationIcon = {
                        IconButton(onClick = onBack) {
                            Icon(Icons.AutoMirrored.Filled.KeyboardArrowLeft, contentDescription = "Voltar")
                        }
                    },
                    actions = {

                        Row(
                            modifier = Modifier
                                .padding(end = 16.dp)
                                .border(1.dp, Color.LightGray, RoundedCornerShape(8.dp))
                        ) {
                            TextButton(onClick = {}) { Text("Mês", color = TextBlack, fontWeight = FontWeight.Bold) }

                        }
                    },
                    colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.White)
                )

                Row(modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp)) {
                    val daysOfWeek = listOf("Dom", "Seg", "Ter", "Qua", "Qui", "Sex", "Sáb")
                    daysOfWeek.forEach { day ->
                        Text(
                            text = day,
                            modifier = Modifier.weight(1f),
                            textAlign = TextAlign.Center,
                            color = TextGray,
                            fontSize = 14.sp
                        )
                    }
                }
                Divider(color = Color.LightGray.copy(alpha = 0.5f))
            }
        }
    ) { padding ->

        CalendarGrid(
            modifier = Modifier.padding(padding),
            yearMonth = currentMonth,
            tasks = tasks
        )
    }
}

@Composable
fun CalendarGrid(
    modifier: Modifier = Modifier,
    yearMonth: YearMonth,
    tasks: List<Task>
) {

    val daysInMonth = yearMonth.lengthOfMonth()
    val firstDayOfMonth = yearMonth.atDay(1).dayOfWeek.value % 7
    val totalSlots = daysInMonth + firstDayOfMonth

    LazyVerticalGrid(
        columns = GridCells.Fixed(7),
        modifier = modifier.fillMaxSize().background(Color.White)
    ) {

        items(firstDayOfMonth) {
            Box(modifier = Modifier
                .height(100.dp)
                .border(0.5.dp, Color.LightGray.copy(alpha = 0.3f)))
        }


        items(daysInMonth) { dayOffset ->
            val day = dayOffset + 1
            val dateStr = yearMonth.atDay(day).format(DateTimeFormatter.ofPattern("dd/MM/yyyy"))


            val tasksForDay = tasks.filter { it.date == dateStr }

            DayCell(day = day, tasks = tasksForDay)
        }
    }
}

@Composable
fun DayCell(day: Int, tasks: List<Task>) {
    Column(
        modifier = Modifier
            .height(100.dp)
            .border(0.5.dp, Color.LightGray.copy(alpha = 0.5f))
            .padding(4.dp)
    ) {
        // Número do Dia
        Text(
            text = day.toString(),
            fontSize = 14.sp,
            fontWeight = FontWeight.SemiBold,
            color = TextBlack,
            modifier = Modifier.padding(bottom = 4.dp)
        )


        tasks.take(3).forEach { task ->
            val color = when (task.category) {
                "Trabalho" -> CategoryWork
                "Pessoal" -> CategoryPersonal
                "Reuniões" -> CategoryMeeting
                "Planos" -> CategoryPlanning
                else -> PurplePrimary
            }

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 1.dp)
                    .background(color, RoundedCornerShape(4.dp))
                    .padding(horizontal = 4.dp, vertical = 2.dp)
            ) {
                Text(
                    text = task.title,
                    color = Color.White,
                    fontSize = 8.sp,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }
        }
    }
}