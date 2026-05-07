package br.com.brunomateus.todolist.model

import java.util.UUID

enum class Category(val label: String) {
    LAZER("Lazer"),
    ESTUDO("Estudo"),
    ESPORTE("Esporte"),
    SAUDE("Saúde")
}

data class Task(
    val id: UUID = UUID.randomUUID(),
    val description: String,
    val category: Category,
    var done: Boolean = false
)

val tasks = listOf(
    Task(description = "Dormir 12h", category = Category.SAUDE, done = false),
    Task(description = "Assistir aula", category = Category.ESTUDO, done = true)
)