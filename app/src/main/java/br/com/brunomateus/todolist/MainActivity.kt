package br.com.brunomateus.todolist

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MultiChoiceSegmentedButtonRow
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SegmentedButton
import androidx.compose.material3.SegmentedButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import br.com.brunomateus.todolist.model.Category
import br.com.brunomateus.todolist.model.Task
import br.com.brunomateus.todolist.ui.AddTaskDialog
import br.com.brunomateus.todolist.ui.TodoItem
import br.com.brunomateus.todolist.ui.TodoList
import br.com.brunomateus.todolist.ui.todo.TodoUiState
import br.com.brunomateus.todolist.ui.theme.TodoListTheme
import br.com.brunomateus.todolist.ui.todo.TodoViewModel


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            TodoListTheme {
                TodoListScaffold()
            }
        }
    }
}


@Composable
fun TodoListScaffold(
    viewModel: TodoViewModel = viewModel()
) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()

    Scaffold(
        floatingActionButton = { 
            AddTaskFloatingButton(onOpenDialog = { viewModel.showDialog() }) 
        },
        modifier = Modifier.fillMaxSize()
    ) { innerPadding ->
        TodoMainScreen(
            state = state,
            onToggleCategory = { viewModel.toggleCategory(it) },
            onToggleTaskDone = { viewModel.toggleTaskDone(it) },
            onHideDialog = { viewModel.hideDialog() },
            onAddTask = { viewModel.addTask(it) },
            modifier = Modifier.padding(innerPadding)
        )
    }
}


@Composable
fun AddTaskFloatingButton(onOpenDialog: () -> Unit) {
    FloatingActionButton(
        onClick = onOpenDialog,
    ) {
        Icon(
            imageVector = Icons.Filled.Add,
            contentDescription = stringResource(R.string.add_task_fab_content_description)
        )
    }
}

@Composable
fun TodoMainScreen(
    state: TodoUiState,
    onToggleCategory: (Category) -> Unit,
    onToggleTaskDone: (Task) -> Unit,
    onHideDialog: () -> Unit,
    onAddTask: (Task) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
    ) {
        CategoryFilter(
            selectedCategories = state.selectedCategories,
            onToggleCategory = onToggleCategory
        )
        TodoList(
            items = state.filteredTodos,
            onTaskDone = onToggleTaskDone
        )
    }

    AnimatedVisibility(visible = state.isDialogVisible) {
        AddTaskDialog(
            onDismiss = onHideDialog,
            onConfirm = onAddTask
        )
    }
}


@Composable
fun CategoryFilter(
    selectedCategories: Set<String>,
    onToggleCategory: (Category) -> Unit,
    modifier: Modifier = Modifier
) {
    val sizeOfCategories = Category.entries.size
    MultiChoiceSegmentedButtonRow(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 4.dp)
    ) {
        Category.entries.forEachIndexed { index, category ->
            val selected = selectedCategories.contains(category.name)
            SegmentedButton(
                shape = SegmentedButtonDefaults.itemShape(
                    index = index,
                    count = sizeOfCategories
                ),
                checked = selected,
                onCheckedChange = { onToggleCategory(category) },
                label = {
                    Text(text = category.label)
                },
                icon = { SegmentedButtonDefaults.Icon(selected) },
            )
        }
    }
}


@Preview
@Composable
fun PreviewTodoItem() {
    TodoItem(
        task = Task(
            description = stringResource(R.string.preview_task_description),
            category = Category.SAUDE,
            done = true
        ),
        onCheckedChange = {}
    )
}

@Preview(showBackground = true)
@Composable
fun TodoMainScreenPreview() {
    TodoListTheme {
        TodoMainScreen(
            state = TodoUiState(),
            onToggleCategory = {},
            onToggleTaskDone = {},
            onHideDialog = {},
            onAddTask = {}
        )
    }
}
