# TodoList - Aprendizado Progressivo

Este projeto é uma aplicação de lista de tarefas (TodoList) desenvolvida para fins educacionais, focada em ensinar o desenvolvimento Android moderno com Jetpack Compose e as melhores práticas de arquitetura para alunos de graduação.

## 🏗️ Arquitetura e Conceitos Abordados

O projeto segue as recomendações oficiais do Android, utilizando uma arquitetura em camadas e o padrão **UDF (Unidirectional Data Flow)**.

### 1. ViewModel e Sobrevivência de Estado
Utilizamos o `TodoViewModel` para gerenciar a lógica de negócio e manter o estado da tela. Por herdar de `ViewModel`, os dados não são perdidos em mudanças de configuração, como a rotação da tela.

```kotlin
class TodoViewModel : ViewModel() {
    // O estado é privado para escrita, mas público para leitura
    var uiState by mutableStateOf(TodoUiState())
        private set

    fun addTask(task: Task) { /* ... */ }
    fun toggleTaskDone(task: Task) { /* ... */ }
}
```

### 2. UI State Centralizado (`TodoUiState`)
Em vez de variáveis de estado espalhadas, utilizamos um único `data class` imutável que representa tudo o que a tela precisa exibir. Isso torna o estado da aplicação previsível e fácil de depurar.

```kotlin
data class TodoUiState(
    val todos: List<Task> = tasks,
    val selectedCategories: Set<String> = emptySet(),
    val isDialogVisible: Boolean = false
) {
    // Lógica de filtro derivada (propriedade calculada)
    val filteredTodos: List<Task> get() = /* lógica de filtro */
}
```

### 3. Imutabilidade e Recomposição Eficiente
Para que o Compose detecte mudanças de forma otimizada, o modelo `Task` é imutável. Quando uma tarefa é alterada (ex: marcada como concluída), criamos uma nova instância da lista e da tarefa usando o método `.copy()`.

```kotlin
// Exemplo de atualização imutável no ViewModel
val updatedTodos = uiState.todos.map {
    if (it.id == task.id) it.copy(done = !it.done) else it
}
uiState = uiState.copy(todos = updatedTodos)
```

### 4. State Hoisting (Elevação de Estado)
Os componentes de UI (Composables) são, em sua maioria, **stateless**. Eles não gerenciam estado; apenas recebem os dados para exibição e notificam eventos através de lambdas.

```kotlin
@Composable
fun TodoList(
    items: List<Task>, 
    onTaskDone: (Task) -> Unit // Evento elevado para o ViewModel
) {
    // ...
}
```

### 5. Otimizações de UI e Recursos
*   **LazyColumn com Keys**: Utilizamos `key = { it.id }` para que o Compose identifique itens de forma única, permitindo animações fluidas e melhor performance.
*   **Recursos Externos**: Todas as strings estão localizadas em `strings.xml`, seguindo as boas práticas de internacionalização e manutenção.

---

## 🛠️ Tecnologias Utilizadas

*   **Kotlin**: `data classes`, `enums`, `UUID` e `Imutabilidade`.
*   **Jetpack Compose**: UI declarativa e `Stateless Composables`.
*   **Material 3**: Design system moderno (`Scaffold`, `SegmentedButton`).
*   **Android Architecture Components**: `ViewModel` e `Lifecycle`.

## 📖 Como usar este repositório para estudo

Este projeto demonstra como estruturar uma aplicação real:
1.  O **Estado** (UiState) "desce" do ViewModel para os componentes.
2.  Os **Eventos** "sobem" dos componentes para o ViewModel através de callbacks.
3.  A lógica de filtro e regras de negócio ficam protegidas dentro do ViewModel, longe da camada de visualização.
