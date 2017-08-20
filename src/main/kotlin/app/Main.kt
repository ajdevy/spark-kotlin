package app

import app.user.UserDao
import app.view.HtmlView
import app.view.MainView
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import spark.Spark.*
import java.util.logging.Level
import java.util.logging.Logger

fun main(args: Array<String>) {

    val logger = Logger.getLogger("Main")
    logger.info("logger initialized")

    exception(Exception::class.java) { e, req, res -> logger.log(Level.SEVERE, "Got exception", e) }

    val userDao = UserDao()

    path("/") {
        get("") { _, _->
            MainView()
        }
    }
    path("/users") {

        get("") { _, _ ->
            jacksonObjectMapper().writeValueAsString(userDao.users)
        }

        get("/:id") { req, _ ->
            userDao.findById(req.params("id").toInt())
        }

        get("/email/:email") { req, _ ->
            userDao.findByEmail(req.params("email"))
        }

        post("/create") { req, res ->
            userDao.save(name = req.queryParams("name"), email = req.queryParams("email"))
            res.status(201)
            "ok"
        }

        patch("/update/:id") { req, _ ->
            userDao.update(
                    id = req.params("id").toInt(),
                    name = req.queryParams("name"),
                    email = req.queryParams("email")
            )
            "ok"
        }

        delete("/delete/:id") { req, _ ->
            userDao.delete(req.params("id").toInt())
            "ok"
        }

    }

    userDao.users.forEach { logger.info(it.toString()) }

}