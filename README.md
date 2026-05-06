# TodoList - Aprendizado Progressivo

Este projeto é uma aplicação de lista de tarefas (TodoList) desenvolvida para fins educacionais, focada em ensinar o desenvolvimento Android moderno com Jetpack Compose de forma progressiva para alunos de graduação.

## 🚀 Conceitos Abordados

Nesta etapa do projeto, o foco evoluiu do gerenciamento de estado básico para a organização de arquitetura com **State Holders** e otimização de performance.

### 1. Separação de Preocupações com State Holder (`TodoState`)

Para evitar que os componentes de UI fiquem sobrecarregados com lógica de negócio, movemos o estado para uma classe dedicada: `TodoState`. Isso permite que a UI foque apenas em "como mostrar os dados".

```kotlin
class TodoState {
    private val _todos = mutableStateListOf(*tasks.toTypedArray())
    val filteredTodos by derivedStateOf { /* lógica de filtro */ }
    
    fun addTask(task: Task) { _todos.add(task) }
}
```

### 2. State Hoisting (Elevação de Estado)

Seguindo as melhores práticas, transformamos o `TodoItem` em um componente **stateless**. Ele não gerencia mais seu próprio estado interno; em vez disso, recebe os dados e os eventos (callbacks) de quem o chama.

```kotlin
@Composable
fun TodoItem(
    task: Task,
    onCheckedChange: (Boolean) -> Unit, // Evento elevado
    modifier: Modifier = Modifier
) {
    Checkbox(
        checked = task.done, // Estado vem de cima
        onCheckedChange = onCheckedChange
    )
    Text(text = task.description)
}
```

### 3. Estados Derivados com `derivedStateOf` (Performance)

Quando um estado (como a lista filtrada) depende de outros estados (a lista original e as categorias selecionadas), usamos `derivedStateOf`. Isso garante que o cálculo do filtro só ocorra quando uma das dependências realmente mudar, economizando processamento durante a recomposição.

### 4. Otimização de Listas e Recursos

*   **Chaves Únicas (`key`)**: No `LazyColumn`, utilizamos `key = { it.id }` para que o Compose identifique cada item de forma única (via UUID), garantindo animações fluidas com `animateItem()`.
*   **Strings Externas**: Todas as strings foram movidas para `strings.xml`, facilitando a manutenção e futura tradução.


### 5. Extração de Recursos (Boas Práticas de UI)
Evitamos o uso de textos fixos (*hardcoded strings*) diretamente no código Kotlin. Movemos todos os textos para o arquivo `res/values/strings.xml`, o que facilita a manutenção.
Além disso, facilita uma possível internacionalização do código.
---

## 🛠️ Tecnologias Utilizadas

*   **Kotlin**: Uso de `data classes`, `enums` e `UUID`.
*   **Jetpack Compose**: UI declarativa com foco em `Stateless Composables`.
*   **Material 3**: Design system moderno com componentes como `Scaffold` e `SegmentedButton`.

## 📖 Como usar este repositório

Este projeto demonstra a transição de um estado simples e "espalhado" para um padrão organizado (UDF - Unidirectional Data Flow). Os alunos devem observar como o estado "desce" para os componentes e os eventos "sobem" para o State Holder.
