package app.todo.view

class MainView(private val todoListView: TodoListView) : HtmlView() {

    override fun getHtml(): String =
            """
            <!doctype html>
            <html lang="en" data-framework="intercoolerjs">
            <head>
                <meta charset="utf-8">
                <title>Spark & intercooler</title>
                <meta name="viewport" content="width=device-width, initial-scale=1">
                <link rel="stylesheet" href="/css/styles.css">
            </head>
            <body>
                <section id="todoapp" ic-target="this">
                    ${this.todoListView}
                </section>
                <div id="info">
                    <p>Double-click to edit a todo</p>
                </div>
                <script src="/js/vendor/jquery-1.12.4.min.js"></script>
                <script src="/js/vendor/intercooler-0.9.6.min.js"></script>
                <script src="/js/app.js"></script>
            </body>
            </html>
            """
}