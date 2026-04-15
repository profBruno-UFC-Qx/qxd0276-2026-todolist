package br.com.brunomateus.todolist.model

data class Task(
    val description: String,
    var done: Boolean = false
)

val tasks = listOf(
    Task(description = "Dormir 12h", done = false),
    Task(description = "Assistir aula", done = true)
)