package br.com.brunomateus.todolist

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
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
                Scaffold(
                    floatingActionButton = { AddTaskFloatingButton() },
                    modifier = Modifier.fillMaxSize()
                ) { innerPadding ->
                    TodoMainScreen(
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

@Composable
fun AddTaskFloatingButton() {
    FloatingActionButton(
        onClick = { },
    ) {
        Icon(Icons.Filled.Add, "Floating action button.")
    }
}


@Composable
fun TodoMainScreen(modifier: Modifier = Modifier) {
    val todos = remember { mutableStateListOf(*tasks.toTypedArray()) }
    var showDialog by remember { mutableStateOf(false) }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
    ) {

        Button(onClick = {
            showDialog = true
        }) {
            Text(
                text = "Add",
                fontSize = 24.sp
            )
        }


        LazyColumn(
            horizontalAlignment = Alignment.Start,
            modifier = Modifier
                .fillMaxSize()
                .padding(10.dp)
        ) {
            items(todos, key = { it.description }) { todo ->
                TodoItem(todo, modifier = Modifier.animateItem())
            }
        }
    }

    AnimatedVisibility(visible = showDialog) {
        AddTaskDialog(onDismiss = { showDialog = false }, onConfirm = { showDialog = false })
    }
}

@Composable
fun TodoItem(task: Task, modifier: Modifier = Modifier) {
    var checked by remember { mutableStateOf(task.done) }
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
    ) {
        Checkbox(checked = checked, onCheckedChange = { checked = it })
        Text(
            text = task.description,
            fontSize = 24.sp
        )
    }
}

@Preview
@Composable
fun PreviewTodoItem() {
    TodoItem(Task("Não sei", true))
}

@Preview(showBackground = true)
@Composable
fun TodoMainScreenPreview() {
    TodoListTheme {
        TodoMainScreen()
    }
}