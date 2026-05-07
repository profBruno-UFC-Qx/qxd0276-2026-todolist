package br.com.brunomateus.todolist.ui.todo

import br.com.brunomateus.todolist.model.Task
import br.com.brunomateus.todolist.model.tasks

data class TodoUiState(
    val todos: List<Task> = tasks,
    val selectedCategories: Set<String> = emptySet(),
    val isDialogVisible: Boolean = false
) {
    // Lógica de filtro calculada a partir do estado atual
    val filteredTodos: List<Task>
        get() = if (selectedCategories.isEmpty()) {
            todos
        } else {
            todos.filter { it.category.name in selectedCategories }
        }
}