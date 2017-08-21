package app.todo.view

import app.todo.TODO_ROOT_URL
import app.todo.model.Todo


class TodoListView(private val status: String,
                   private val todos: List<Todo>,
                   private val filter: String = "") : HtmlView() {

    override fun getHtml(): String {
        return """
                <header ic-include='{"status":"$status"}'>
                    <h1>todos</h1>
                    <form id="todo-form" ic-post-to="${TODO_ROOT_URL}">
                        <input id="new-todo" placeholder="What needs to be done?" name="todo-title" pattern=".{4,}" required title="> 3 chars" autofocus>
                    </form>
                </header>

                <section id="main" ic-include='{"status":"$status"}'>
                    <ul id="todo-list">
                ${generateTodoList(todos)}
                    </ul>
                </section>

                <footer>
                    <ul id="filters" ic-push-url="true"> ##maintains history
                        <li #if($filter == "")        class="selected"#end ic-get-from="/">All</li>
                        <li #if($filter == "active")  class="selected"#end ic-get-from="/?status=active">Active</li>
                        <li #if($filter == "complete")class="selected"#end ic-get-from="/?status=complete">Completed</li>
                    </ul>
                </footer>
                """
    }

    private fun generateTodoList(todos: List<Todo>): String {
        val todoListBuilder = StringBuilder()
        for (todo in todos) {
            todoListBuilder.append(createTodoViewHtml(todo))
        }
        return todoListBuilder.toString()
    }

    private fun createTodoViewHtml(todo: Todo) = """
            <li class="${if (todo.isComplete) "completed" else ""}">
                <div class="view">
                    <input type="checkbox" class="toggle" ic-put-to="${TODO_ROOT_URL}/${todo.id}/toggle_status" #if(${todo.isComplete})checked#end>
                    <label ic-get-from="${TODO_ROOT_URL}/${todo.id}/edit" ic-target="closest li" ic-trigger-on="dblclick" ic-replace-target="true">${todo.title}</label>
                    <button class="destroy" ic-delete-from="${TODO_ROOT_URL}/${todo.id}"></button>
                </div>
            </li>
            """
}