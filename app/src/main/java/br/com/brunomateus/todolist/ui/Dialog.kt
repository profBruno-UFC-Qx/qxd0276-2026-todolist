package br.com.brunomateus.todolist.ui

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun AddTaskDialog(onDismiss: () -> Unit, onConfirm: () -> Unit, modifier: Modifier = Modifier) {
    AlertDialog(
        title = {
            Text(text = "Adicionar tarefa")
        },
        onDismissRequest = onDismiss,
        confirmButton = {
            TextButton(
                onClick = onConfirm
            ) {
                Text(text = "Adicionar")
            }
        },
        modifier = modifier,
        dismissButton = {
            TextButton(
                onClick = onDismiss
            ) {
                Text(text = "Cancelar")
            }
        },
        text = {

                TextField(
                    value = "",
                    label = {
                        Text(text = "Descrição:")
                    },
                    placeholder = {
                        Text("Digite a sua tarefa")
                    },
                    onValueChange = {}
                )
        },
    )
}

@Preview
@Composable
fun PreviewDialog() {
    AddTaskDialog(onDismiss = {}, onConfirm = {})
}