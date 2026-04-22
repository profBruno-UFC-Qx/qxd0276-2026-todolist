package br.com.brunomateus.todolist

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MultiChoiceSegmentedButtonRow
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SegmentedButton
import androidx.compose.material3.SegmentedButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.mutableStateSetOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import br.com.brunomateus.todolist.model.Category
import br.com.brunomateus.todolist.model.Task
import br.com.brunomateus.todolist.model.tasks
import br.com.brunomateus.todolist.ui.AddTaskDialog
import br.com.brunomateus.todolist.ui.theme.TodoListTheme

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
fun TodoListScaffold() {
    var showDialog by remember { mutableStateOf(false) }
    Scaffold(
        floatingActionButton = { AddTaskFloatingButton({ showDialog = true }) },
        modifier = Modifier.fillMaxSize()
    ) { innerPadding ->
        TodoMainScreen(
            dialogIsVisible = showDialog,
            toggleDialog = { showDialog = !showDialog },
            modifier = Modifier.padding(innerPadding)
        )
    }
}


@Composable
fun AddTaskFloatingButton(openDialog: () -> Unit) {
    FloatingActionButton(
        onClick = openDialog,
    ) {
        Icon(
            imageVector = Icons.Filled.Add,
            contentDescription = stringResource(R.string.add_task_fab_content_description)
        )
    }
}

@Composable
fun TodoMainScreen(dialogIsVisible: Boolean, toggleDialog: () -> Unit, modifier: Modifier = Modifier) {
    val todos = remember { mutableStateListOf(*tasks.toTypedArray()) }
    val selectedCategories = remember { mutableStateSetOf<String>() }

    val filteredTodos by remember {
        derivedStateOf {
            if (selectedCategories.isEmpty()) {
                todos.toList()
            } else {
                todos.filter { it.category.name in selectedCategories }
            }
        }
    }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
    ) {
        val sizeOfCategories = Category.values().size
        MultiChoiceSegmentedButtonRow(
            modifier = Modifier.fillMaxWidth().padding(horizontal = 4.dp)
        ) {
            Category.values().forEachIndexed { index, category ->
                val selected = selectedCategories.contains(category.name)
                SegmentedButton(
                    shape = SegmentedButtonDefaults.itemShape(
                        index = index,
                        count = sizeOfCategories
                    ),
                    checked = selected,
                    onCheckedChange = {
                        val constantName = category.name
                        if (it) selectedCategories.add(constantName) 
                        else selectedCategories.remove(constantName)
                    },
                    label = {
                        Text(text= category.label )
                    },
                    icon = { SegmentedButtonDefaults.Icon(selected) },
                )
            }
        }
        LazyColumn(
            horizontalAlignment = Alignment.Start,
            modifier = Modifier
                .fillMaxSize()
                .padding(10.dp)
        ) {
            items(
                items = filteredTodos,
                key = { it.id }
            ) { todo ->
                TodoItem(todo, modifier = Modifier.animateItem())
            }
        }
    }

    AnimatedVisibility(visible = dialogIsVisible) {
        AddTaskDialog(
            onDismiss = toggleDialog,
            onConfirm = { task ->
                todos.add(task)
                toggleDialog()
            }
        )
    }
}

@Composable
fun TodoItem(task: Task, modifier: Modifier = Modifier) {
    var checked by remember { mutableStateOf(task.done) }
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
    ) {
        val color = when(task.category) {
            Category.SAUDE -> Color.Green
            Category.ESTUDO -> Color.Blue
            Category.ESPORTE -> Color.Cyan
            Category.LAZER -> Color.Magenta
        }
        Checkbox(
            checked = checked,
            onCheckedChange = { checked = it },
            colors = CheckboxDefaults.colors(
                uncheckedColor = color,
                checkedColor = color
            )
        )
        Text(
            text = task.description,
            fontSize = 24.sp
        )
    }
}

@Preview
@Composable
fun PreviewTodoItem() {
    TodoItem(
        Task(
            description = stringResource(R.string.preview_task_description),
            category = Category.SAUDE,
            done = true
        )
    )
}

@Preview(showBackground = true)
@Composable
fun TodoMainScreenPreview() {
    TodoListTheme {
        TodoListScaffold()
    }
}
