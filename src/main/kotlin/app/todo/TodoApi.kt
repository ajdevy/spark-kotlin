package app.todo

import app.logger
import app.todo.model.Status
import app.todo.model.Todo
import app.todo.model.TodoDao
import app.todo.view.EditTodoView
import app.todo.view.MainTodoView
import app.todo.view.TodoListView
import spark.Request
import spark.Response
import spark.Spark
import spark.utils.StringUtils
import java.util.*
import java.util.logging.Level

val TODO_ROOT_URL = "/todos"

fun startTodoApi() {
    Spark.path("/") {
        Spark.get("") { request, _ ->
            if ("true" == request.queryParams("ic-request")) {
                renderTodoListView(request)
            } else {
                MainTodoView(renderTodoListView(request))
            }
        }
        Spark.post("/todos") { request, _ ->
            TodoDao.add(Todo(request.queryParams("todo-title"),
                    UUID.randomUUID().toString(),
                    Status.ACTIVE))
            renderTodoListView(request)
        }

        Spark.get("/todos/:id/edit") { req, res -> renderEditTodo(req, res) }

        Spark.delete("/todos/completed") { request, _ ->
            TodoDao.removeCompleted()
            renderTodoListView(request)
        }
        Spark.delete("/todos/:id") { request, _ ->
            TodoDao.remove(request.params("id"))
            renderTodoListView(request)
        }

        Spark.put("/todos/toggle_status") { request, _ ->
            TodoDao.toggleAll(request.queryParams("toggle-all") != null)
            renderTodoListView(request)
        }
        Spark.put("/todos/:id") { request, _ ->
            TodoDao.update(request.params("id"),
                    request.queryParams("todo-title"))
            renderTodoListView(request)
        }
        Spark.put("/todos/:id/toggle_status") { request, _ ->
            TodoDao.toggleStatus(request.params("id"))
            renderTodoListView(request)
        }
    }
}

fun renderTodoListView(request: Request): TodoListView {
    val status = request.queryParamOrDefault("status", "")
    logger.info("render todo list, got status $status")
    var todoStatus: Status? = null
    if (StringUtils.isNotEmpty(status)) {
        try {
            todoStatus = Status.valueOf(status.toUpperCase())
        } catch (e: IllegalArgumentException) {
            logger.log(Level.SEVERE, "could not parse todo status", e);
        }
    }
    val todoList = if (todoStatus != null) TodoDao.ofStatus(todoStatus) else TodoDao.all()
    logger.info("got todo list $todoList")
    return TodoListView(status, todoList, status)
}

private fun renderEditTodo(request: Request, response: Response): String {
    //render edit div for todo item
    val todo = TodoDao.find(request.params("id"))
    return if (todo == null) {
        //no todo found, show main page
        response.redirect("/")
        ""
    } else {
        EditTodoView(todo).toString()
    }
}