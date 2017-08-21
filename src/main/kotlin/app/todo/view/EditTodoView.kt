package app.todo.view

import app.todo.TODO_ROOT_URL
import app.todo.model.Todo

class EditTodoView(val todo: Todo) : HtmlView() {

    override fun getHtml(): String =
            """
            <li class="editing">
                <form id="edit-form" ic-put-to="${TODO_ROOT_URL}/${todo.id}">
                    <input id="todo-edit" ic-get-from="/" ic-trigger-on="resetEscape" name="todo-title" class="edit" value="${todo.title}" autofocus>
                </form>
            </li>
            """
}