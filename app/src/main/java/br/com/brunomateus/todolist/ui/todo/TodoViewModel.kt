package br.com.brunomateus.todolist.ui.todo

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import br.com.brunomateus.todolist.model.Category
import br.com.brunomateus.todolist.model.Task

/**
 * Data class que representa o estado imutável da tela de Todo.
 * Seguir esta prática facilita o rastreamento de mudanças e depuração.
 */


/**
 * ViewModel que gerencia o estado da UI.
 * Ele sobrevive a mudanças de configuração e centraliza a lógica de negócio.
 */
class TodoViewModel : ViewModel() {
    
    // O estado é privado para escrita, mas público para leitura
    var uiState by mutableStateOf(TodoUiState())
        private set

    fun addTask(task: Task) {
        uiState = uiState.copy(
            todos = uiState.todos + task,
            isDialogVisible = false
        )
    }

    fun toggleCategory(category: Category) {
        val currentCategories = uiState.selectedCategories.toMutableSet()
        if (currentCategories.contains(category.name)) {
            currentCategories.remove(category.name)
        } else {
            currentCategories.add(category.name)
        }
        uiState = uiState.copy(selectedCategories = currentCategories)
    }

    fun showDialog() {
        uiState = uiState.copy(isDialogVisible = true)
    }

    fun hideDialog() {
        uiState = uiState.copy(isDialogVisible = false)
    }

    fun toggleTaskDone(task: Task) {
        // Como Task agora é imutável (val), criamos uma nova lista com a tarefa atualizada
        val updatedTodos = uiState.todos.map {
            if (it.id == task.id) it.copy(done = !it.done) else it
        }
        uiState = uiState.copy(todos = updatedTodos)
    }
}
