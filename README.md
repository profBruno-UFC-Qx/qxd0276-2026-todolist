# TodoList - Aprendizado Progressivo

Este projeto é uma aplicação de lista de tarefas (TodoList) desenvolvida para fins educacionais, focada em ensinar o desenvolvimento Android moderno com Jetpack Compose de forma progressiva para alunos de graduação.

## 🚀 Conceitos Abordados

Nesta etapa inicial do projeto, o foco principal é o **Gerenciamento de Estado (State Management)** no Jetpack Compose.

### 1. Estado Simples com `mutableStateOf` e `remember`

O Jetpack Compose é uma ferramenta declarativa. Para que a interface mude, não alteramos os componentes diretamente; alteramos o **estado** que os descreve.

No componente `TodoItem`, utilizamos `mutableStateOf` para criar um estado observável e `remember` para garantir que o valor sobreviva às recomposições do componente:

```kotlin
@Composable
fun TodoItem(task: Task, modifier: Modifier = Modifier) {
    // Estado interno para controlar se o checkbox está marcado
    var checked by remember { mutableStateOf(task.done) }

    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
    ) {
        Checkbox(
            checked = checked,
            onCheckedChange = { checked = it } // Ao mudar, o estado atualiza e a UI reflete a mudança
        )
        Text(text = task.description)
    }
}
```

### 2. Listas Dinâmicas com `mutableStateListOf`

Para que uma lista (como o `LazyColumn`) seja atualizada automaticamente quando adicionamos ou removemos itens, precisamos de uma lista que o Compose consiga observar.

```kotlin
@Composable
fun TodoMainScreen(modifier: Modifier = Modifier) {
    // mutableStateListOf notifica o Compose quando a lista sofre alterações (add/remove)
    val todos = remember { mutableStateListOf(*tasks.toTypedArray()) }

    LazyColumn(...) {
        items(todos, key = { it.description }) { todo ->
            TodoItem(todo)
        }
    }
}
```

### 3. Controle de Visibilidade de Diálogos

O estado também é usado para controlar elementos de navegação e interface, como diálogos de entrada de dados. No projeto, usamos uma variável booleana para decidir se o `AddTaskDialog` deve ser exibido:

```kotlin
var showDialog by remember { mutableStateOf(false) }

// Botão que "abre" o dialog
Button(onClick = { showDialog = true }) { ... }

// A visibilidade do componente é controlada pelo valor de showDialog
AnimatedVisibility(visible = showDialog) {
    AddTaskDialog(
        onDismiss = { showDialog = false }, 
        onConfirm = { showDialog = false }
    )
}
```

---

## 🛠️ Tecnologias Utilizadas

*   **Kotlin**: Linguagem oficial para Android.
*   **Jetpack Compose**: Kit de ferramentas moderno para construir UI nativa.
*   **Material 3**: Design system atualizado do Google.
*   **Scaffold**: Componente que fornece a estrutura visual básica (TopBar, FAB, etc).

## 📖 Como usar este repositório

Este projeto foi estruturado para ser seguido passo a passo. Os alunos devem observar como os dados fluem da fonte de dados (`model/TaskRepositories.kt`) para a interface (`MainActivity.kt`) e como as interações do usuário modificam esses dados através do estado.
