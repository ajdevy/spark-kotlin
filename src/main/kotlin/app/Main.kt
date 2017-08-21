package app

import app.todo.startTodoApi
import app.user.startUserApi
import spark.Spark.*
import java.util.logging.Level
import java.util.logging.Logger

val logger: Logger = Logger.getLogger("Main")

fun main(args: Array<String>) {

    logger.info("logger initialized")

    setupServer()

    startTodoApi()

    startUserApi()
}

fun setupServer() {
    exception(Exception::class.java) { e, _, _ -> logger.log(Level.SEVERE, "Got exception", e) }

    staticFiles.location("/public")

    port(9999)
}