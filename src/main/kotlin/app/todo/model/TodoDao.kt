package app.todo.model

import java.util.stream.Collectors

object TodoDao {

    private val DATA = java.util.ArrayList<Todo>()

    fun add(todo: Todo) {
        DATA.add(todo)
    }

    fun find(id: String): Todo? {
        return DATA.stream().filter { t -> t.id == id }.findFirst().orElse(null)
    }

    fun update(id: String, title: String) {
        find(id)!!.title = title
    }

    fun ofStatus(status: Status): List<Todo> {
        return DATA.stream().filter { t -> t.status == status }
                .collect(Collectors.toList())
    }

    fun remove(id: String) {
        DATA.remove(find(id))
    }

    fun removeCompleted() {
        ofStatus(Status.COMPLETE).forEach { t -> TodoDao.remove(t.id) }
    }

    fun toggleStatus(id: String) {
        find(id)!!.toggleStatus()
    }

    fun toggleAll(complete: Boolean) {
        TodoDao.all().forEach { t -> t.status = if (complete) Status.COMPLETE else Status.ACTIVE }
    }

    fun all(): List<Todo> {
        return DATA
    }
}
