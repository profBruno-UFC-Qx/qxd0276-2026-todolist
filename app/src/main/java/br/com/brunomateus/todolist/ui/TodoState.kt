package br.com.brunomateus.todolist.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.mutableStateSetOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import br.com.brunomateus.todolist.model.Category
import br.com.brunomateus.todolist.model.Task
import br.com.brunomateus.todolist.model.tasks

/**
 * State Holder simples para gerenciar a lógica da lista de tarefas.
 * Esta classe centraliza o estado e as ações, separando a lógica da UI.
 */
class TodoState {
    // 1. Estados (Dados)
    private val todos = mutableStateListOf(*tasks.toTypedArray())

    private val _selectedCategories = mutableStateSetOf<String>()
    val selectedCategories: Set<String> get() = _selectedCategories

    var isDialogVisible by mutableStateOf(false)
        private set

    // 2. Estado Derivado (Lógica de Filtro)
    val filteredTodos by derivedStateOf {
        if (_selectedCategories.isEmpty()) {
            todos.toList()
        } else {
            todos.filter { it.category.name in _selectedCategories }
        }
    }

    // 3. Ações (Eventos)
    fun addTask(task: Task) {
        todos.add(task)
        hideDialog()
    }

    fun toggleCategory(category: Category) {
        val name = category.name
        if (_selectedCategories.contains(name)) {
            _selectedCategories.remove(name)
        } else {
            _selectedCategories.add(name)
        }
    }

    fun showDialog() {
        isDialogVisible = true
    }

    fun hideDialog() {
        isDialogVisible = false
    }

    fun toggleTaskDone(task: Task) {
        val index = todos.indexOfFirst { it.id == task.id }
        if (index != -1) {
            todos[index].done = !todos[index].done
        }
    }
}

/**
 * Função auxiliar para instanciar e "lembrar" o estado entre recomposições.
 */
@Composable
fun rememberTodoState(): TodoState {
    return remember { TodoState() }
}
