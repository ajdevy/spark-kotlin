package app.user

import app.logger
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import spark.Spark

fun startUserApi() {

    val userDao = UserDao()

    Spark.path("/users") {

        Spark.get("") { _, _ ->
            jacksonObjectMapper().writeValueAsString(userDao.users)
        }

        Spark.get("/:id") { req, _ ->
            userDao.findById(req.params("id").toInt())
        }

        Spark.get("/email/:email") { req, _ ->
            userDao.findByEmail(req.params("email"))
        }

        Spark.post("/create") { req, res ->
            userDao.save(name = req.queryParams("name"), email = req.queryParams("email"))
            res.status(201)
            "ok"
        }

        Spark.patch("/update/:id") { req, _ ->
            userDao.update(
                    id = req.params("id").toInt(),
                    name = req.queryParams("name"),
                    email = req.queryParams("email")
            )
            "ok"
        }

        Spark.delete("/delete/:id") { req, _ ->
            userDao.delete(req.params("id").toInt())
            "ok"
        }

    }

    userDao.users.forEach { logger.info(it.toString()) }
}
