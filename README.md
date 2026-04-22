# TodoList - Aprendizado Progressivo

Este projeto é uma aplicação de lista de tarefas (TodoList) desenvolvida para fins educacionais, focada em ensinar o desenvolvimento Android moderno com Jetpack Compose de forma progressiva para alunos de graduação.

## 🚀 Conceitos e Boas Práticas

Nesta etapa do projeto, avançamos do gerenciamento de estado simples para técnicas mais refinadas e padrões recomendados pela Google.

### 1. Estado Simples e Reatividade
O Jetpack Compose é declarativo. Para que a interface mude, alteramos o **estado** que a descreve usando `mutableStateOf` e `remember`.

```kotlin
@Composable
fun TodoItem(task: Task, modifier: Modifier = Modifier) {
    // Estado interno para controlar se o checkbox está marcado
    var checked by remember { mutableStateOf(task.done) }
    // ...
    Checkbox(checked = checked, onCheckedChange = { checked = it })
}
```

### 2. Estados Derivados com `derivedStateOf` (Performance)
Uma das melhores práticas em Compose é evitar cálculos desnecessários durante a recomposição. Usamos o `derivedStateOf` quando um estado é calculado a partir de outros estados.

No nosso caso, a lista filtrada depende da lista original de tarefas e das categorias selecionadas. O filtro só é reprocessado se uma dessas dependências mudar:

```kotlin
val filteredTodos by remember {
    derivedStateOf {
        if (selectedCategories.isEmpty()) {
            todos.toList()
        } else {
            todos.filter { it.category.name in selectedCategories }
        }
    }
}
```

### 3. State Hoisting (Elevação de Estado)
Para tornar os componentes mais "puros", reutilizáveis e fáceis de testar, aplicamos o **State Hoisting**. O componente `TodoMainScreen` não decide sozinho quando o diálogo abre ou fecha; ele recebe esse estado e as funções de callback do seu pai (`TodoListScaffold`).

```kotlin
@Composable
fun TodoMainScreen(
    dialogIsVibile: Boolean, // Estado elevado
    toggleDialog: () -> Unit, // Evento elevado
    modifier: Modifier = Modifier
) { ... }
```

### 4. Otimização de Listas com `key` e `animateItem`
Ao trabalhar com listas dinâmicas no `LazyColumn`, é fundamental usar o parâmetro `key`. Isso ajuda o Compose a identificar cada item de forma única (usando o `id` da `Task`), melhorando a performance e permitindo animações automáticas com `animateItem()`.

```kotlin
items(
    items = filteredTodos,
    key = { it.id } // Uso de ID único em vez da posição ou descrição
) { todo ->
    TodoItem(todo, modifier = Modifier.animateItem())
}
```

### 5. Extração de Recursos (Boas Práticas de UI)
Evitamos o uso de textos fixos (*hardcoded strings*) diretamente no código Kotlin. Movemos todos os textos para o arquivo `res/values/strings.xml`, o que facilita a manutenção e permite a tradução do app futuramente.

```kotlin
// Forma correta de acessar textos
Text(text = stringResource(R.string.add_button))
```

---

## 🛠️ Tecnologias Utilizadas

*   **Kotlin**: Linguagem base com recursos como Enums e UUID.
*   **Jetpack Compose**: `mutableStateListOf`, `mutableStateSetOf` e `derivedStateOf`.
*   **Material 3**: Uso de `Scaffold`, `FloatingActionButton` e `MultiChoiceSegmentedButtonRow`.

## 📖 Como usar este repositório

Este projeto demonstra a evolução de uma UI estática para uma aplicação reativa. Os alunos devem focar em entender como o **fluxo de dados unidirecional** (Unidirectional Data Flow) funciona: o estado desce para os componentes e os eventos sobem para modificar o estado.
