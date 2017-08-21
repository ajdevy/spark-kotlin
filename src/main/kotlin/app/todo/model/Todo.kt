package app.todo.model

class Todo(var title: String, var id: String, var status: Status) {

    fun toggleStatus() {
        this.status = if (isComplete) Status.ACTIVE else Status.COMPLETE
    }

    val isComplete: Boolean
        get() = this.status == Status.COMPLETE

    override fun toString(): String {
        return "Todo(title='$title', id='$id', status=$status)"
    }
}