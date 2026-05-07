package br.com.brunomateus.todolist.ui

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import br.com.brunomateus.todolist.model.Category
import br.com.brunomateus.todolist.model.Task


@Composable
fun TodoList(items: List<Task>, onTaskDone: (Task) -> Unit, modifier: Modifier = Modifier) {
    LazyColumn(
        horizontalAlignment = Alignment.Start,
        modifier = modifier
            .fillMaxSize()
            .padding(10.dp)
    ) {
        items(
            items = items,
            key = { it.id }
        ) { todo ->
            TodoItem(
                task = todo,
                onCheckedChange = { onTaskDone(todo) },
                modifier = Modifier.animateItem()
            )
        }
    }
}

@Composable
fun TodoItem(
    task: Task,
    onCheckedChange: (Boolean) -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
    ) {
        val color =
            Checkbox(
                checked = task.done,
                onCheckedChange = onCheckedChange,
                colors = CheckboxDefaults.colors(
                    uncheckedColor = task.category.color,
                    checkedColor = task.category.color
                )
            )
        Text(
            text = task.description,
            style = MaterialTheme.typography.bodyMedium,
        )
    }
}

val Category.color
    get() = when (this) {
        Category.SAUDE -> Color.Green
        Category.ESTUDO -> Color.Blue
        Category.ESPORTE -> Color.Cyan
        Category.LAZER -> Color.Magenta
    }