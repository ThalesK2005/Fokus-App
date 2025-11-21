package com.thales.fokus.ui.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.thales.fokus.data.FokusDatabase
import com.thales.fokus.model.Task
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class TaskViewModel(application: Application) : AndroidViewModel(application) {
    private val dao = FokusDatabase.getDatabase(application).taskDao()
    private val auth = Firebase.auth

    private val _tasks = MutableStateFlow<List<Task>>(emptyList())
    val tasks: StateFlow<List<Task>> = _tasks

    init {
        loadTasks()
    }

    fun loadTasks() {
        val currentUserId = auth.currentUser?.uid
        if (currentUserId != null) {
            viewModelScope.launch {
                dao.getTasksForUser(currentUserId).collect { taskList ->
                    _tasks.value = taskList
                }
            }
        } else {
            _tasks.value = emptyList()
        }
    }


    suspend fun getTaskById(id: Int): Task? {
        return dao.getTaskById(id)
    }


    fun addTask(title: String, date: String, time: String, category: String) {
        val currentUserId = auth.currentUser?.uid ?: return
        val newTask = Task(
            title = title, description = "", date = date, time = time, category = category, userId = currentUserId
        )
        viewModelScope.launch { dao.insertTask(newTask) }
    }


    fun updateTask(task: Task) {
        viewModelScope.launch { dao.updateTask(task) }
    }

    fun deleteTask(task: Task) {
        viewModelScope.launch { dao.deleteTask(task) }
    }
}