package br.com.brunomateus.todolist.ui.todo

import androidx.lifecycle.ViewModel
import br.com.brunomateus.todolist.model.Category
import br.com.brunomateus.todolist.model.Task
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

/**
 * ViewModel que gerencia o estado da UI utilizando StateFlow.
 */
class TodoViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(TodoUiState())
    val uiState = _uiState.asStateFlow()

    fun addTask(task: Task) {
        _uiState.update { currentState ->
            currentState.copy(
                todos = currentState.todos + task,
                isDialogVisible = false
            )
        }
    }

    fun toggleCategory(category: Category) {
        _uiState.update { currentState ->
            val newCategories = if (currentState.selectedCategories.contains(category.name)) {
                currentState.selectedCategories - category.name
            } else {
                currentState.selectedCategories + category.name
            }
            currentState.copy(selectedCategories = newCategories)
        }
    }

    fun showDialog() {
        _uiState.update { it.copy(isDialogVisible = true) }
    }

    fun hideDialog() {
        _uiState.update { it.copy(isDialogVisible = false) }
    }

    fun toggleTaskDone(task: Task) {
        _uiState.update { currentState ->
            val updatedTodos = currentState.todos.map {
                if (it.id == task.id) it.copy(done = !it.done) else it
            }
            currentState.copy(todos = updatedTodos)
        }
    }
}
