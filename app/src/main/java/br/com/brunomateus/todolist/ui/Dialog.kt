package br.com.brunomateus.todolist.ui

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import br.com.brunomateus.todolist.R

@Composable
fun AddTaskDialog(onDismiss: () -> Unit, onConfirm: () -> Unit, modifier: Modifier = Modifier) {
    AlertDialog(
        title = {
            Text(text = stringResource(R.string.add_task_dialog_title))
        },
        onDismissRequest = onDismiss,
        confirmButton = {
            TextButton(
                onClick = onConfirm
            ) {
                Text(text = stringResource(R.string.add_task_confirm))
            }
        },
        modifier = modifier,
        dismissButton = {
            TextButton(
                onClick = onDismiss
            ) {
                Text(text = stringResource(R.string.add_task_cancel))
            }
        },
        text = {

                TextField(
                    value = "",
                    label = {
                        Text(text = stringResource(R.string.add_task_description_label))
                    },
                    placeholder = {
                        Text(stringResource(R.string.add_task_description_placeholder))
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
