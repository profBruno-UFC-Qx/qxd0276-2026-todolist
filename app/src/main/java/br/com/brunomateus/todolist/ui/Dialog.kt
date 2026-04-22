package br.com.brunomateus.todolist.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.tooling.preview.Preview
import br.com.brunomateus.todolist.R
import br.com.brunomateus.todolist.model.Category
import br.com.brunomateus.todolist.model.Task

@Composable
fun AddTaskDialog(onDismiss: () -> Unit, onConfirm: (task: Task) -> Unit, modifier: Modifier = Modifier) {
    var taskDescription by remember { mutableStateOf("") }
    var selectCategory by remember { mutableStateOf("")}

    AlertDialog(
        title = {
            Text(text = stringResource(R.string.add_task_dialog_title))
        },
        onDismissRequest = onDismiss,
        confirmButton = {
            TextButton(
                onClick = { onConfirm(Task(
                    description = taskDescription,
                    category = Category.valueOf(selectCategory)
                )) }
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
            Column(

            ) {
                TextField(
                    value = taskDescription,
                    label = {
                        Text(text = stringResource(R.string.add_task_description_label))
                    },
                    placeholder = {
                        Text(stringResource(R.string.add_task_description_placeholder))
                    },
                    onValueChange = { input -> taskDescription = input }
                )
                Column(modifier = Modifier.selectableGroup()) {
                    for (category in Category.values()) {
                        Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.
                        fillMaxWidth().
                        selectable(
                            selected = selectCategory == category.toString(),
                            onClick = { selectCategory = category.toString()},
                            role = Role.RadioButton
                        )) {
                            RadioButton(selected = selectCategory == category.toString() , onClick = null)
                            Text(text = category.label)
                        }

                    }
                }
            }

        },
    )
}

@Preview
@Composable
fun PreviewDialog() {
    AddTaskDialog(onDismiss = {}, onConfirm = {})
}
