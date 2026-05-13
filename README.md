# TodoList - Aprendizado Progressivo

Este projeto é uma aplicação de lista de tarefas (TodoList) desenvolvida para fins educacionais, focada em ensinar o desenvolvimento Android moderno com Jetpack Compose e as melhores práticas de arquitetura para alunos de graduação.

## 🏗️ Arquitetura e Conceitos Abordados

O projeto segue as recomendações oficiais do Android, utilizando uma arquitetura em camadas e o padrão **UDF (Unidirectional Data Flow)** com fluxos reativos.

### 1. ViewModel e StateFlow (Reatividade Moderna)
O gerenciamento de estado evoluiu para utilizar o `StateFlow`. O `StateFlow` é um fluxo de dados observável que mantém o estado da UI de forma reativa e ciente do ciclo de vida, permitindo uma separação clara entre a lógica e a interface.

```kotlin
class TodoViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(TodoUiState())
    val uiState = _uiState.asStateFlow()

    fun addTask(task: Task) {
        // Uso do .update para garantir que a atualização seja atômica
        // O parâmetro 'currentState' garante que trabalhamos com o dado mais recente
        _uiState.update { currentState ->
            currentState.copy(
                todos = currentState.todos + task,
                isDialogVisible = false
            )
        }
    }
}
```

### 2. UI State Centralizado (`TodoUiState`)
Todo o estado necessário para renderizar a tela é consolidado em um único `data class` imutável. Isso torna o estado da aplicação previsível, fácil de testar e depurar.

```kotlin
data class TodoUiState(
    val todos: List<Task> = tasks,
    val selectedCategories: Set<String> = emptySet(),
    val isDialogVisible: Boolean = false
) {
    // Propriedade calculada: a filtragem acontece de forma reativa
    val filteredTodos: List<Task> get() = if (selectedCategories.isEmpty()) todos else todos.filter { it.category.name in selectedCategories }
}
```

### 3. Coleta de Estado com `collectAsStateWithLifecycle`
Na camada de UI, consumimos o estado utilizando a biblioteca de ciclo de vida do Android. Isso garante que a coleta de dados seja interrompida quando o aplicativo vai para o segundo plano, otimizando o consumo de bateria e processamento.

```kotlin
@Composable
fun TodoListScaffold(viewModel: TodoViewModel = viewModel()) {
    // Coleta o estado de forma segura e eficiente
    val state by viewModel.uiState.collectAsStateWithLifecycle()
    
    TodoMainScreen(state = state, ...)
}
```

### 4. Imutabilidade e Performance
Para que o Compose identifique as mudanças de estado corretamente, mantemos nossos modelos imutáveis (`val`). Sempre que uma tarefa é alterada, o `ViewModel` emite um novo objeto de estado com a lista atualizada através do método `.copy()`.

```kotlin
fun toggleTaskDone(task: Task) {
    _uiState.update { currentState ->
        val updatedTodos = currentState.todos.map {
            if (it.id == task.id) it.copy(done = !it.done) else it
        }
        currentState.copy(todos = updatedTodos)
    }
}
```

### 5. Otimizações de UI e Recursos
*   **LazyColumn com Keys**: Utilizamos `key = { it.id }` para garantir performance e animações corretas.
*   **Recursos Externos**: Todas as strings estão localizadas em `strings.xml`, seguindo as boas práticas de internacionalização e manutenção.
*   **State Hoisting**: Os componentes de UI recebem apenas os dados necessários e notificam mudanças via lambdas, permanecendo testáveis e reutilizáveis (stateless).

---

## 🛠️ Tecnologias Utilizadas

*   **Kotlin Coroutines & Flow**: Gerenciamento de estado reativo com `StateFlow`.
*   **Jetpack Compose**: UI declarativa com consumo de estado via Lifecycle.
*   **Material 3**: Design system moderno.
*   **Android Architecture Components**: `ViewModel` e `Lifecycle`.

## 📖 Como usar este repositório para estudo

Este projeto demonstra a maturidade da arquitetura Android:
1.  **O Estado Desce**: O `UiState` flui do ViewModel para a UI.
2.  **Os Eventos Sobem**: A UI notifica o ViewModel sobre ações do usuário via lambdas.
3.  **Segurança**: O uso de imutabilidade e fluxos reativos previne bugs comuns de concorrência e inconsistência de dados.
