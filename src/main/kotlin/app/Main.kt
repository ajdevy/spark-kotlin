package app

import app.todo.startTodoApi
import app.trading.startTradingApi
import app.account.startAccountApi
import app.login.startLoginUserApi
import spark.Spark.*
import java.util.logging.Level
import java.util.logging.Logger

val logger: Logger = Logger.getLogger("Main")

fun main(args: Array<String>) {

    logger.info("logger initialized")

    setupServer()

    startTodoApi()

    startAccountApi()

    startTradingApi()

    startLoginUserApi()
}

fun setupServer() {
    exception(Exception::class.java) { e, _, _ -> logger.log(Level.SEVERE, "Got exception", e) }

    val keyStorePath = "/home/user/keys/private-key.jks"
    val keyStorePassword = "password"
    //        Those are all strings, and for example, the keystore values could be:
//        secure(keyStorePath, keyStorePassword, trustStorePath, trustStorePassword);


    staticFiles.location("/public")

    port(9999)
}