package app.account

import app.logger
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import spark.Spark

fun startAccountApi() {

    val accountDao = AccountDao()

    Spark.path("/accounts") {

        Spark.get("") { _, _ ->
            jacksonObjectMapper().writeValueAsString(accountDao.accounts)
        }

        Spark.get("/:id") { req, _ ->
            accountDao.findById(req.params("id").toInt())
        }

        Spark.get("/email/:email") { req, _ ->
            accountDao.findByEmail(req.params("email"))
        }

        Spark.post("/create") { req, res ->
            accountDao.save(name = req.queryParams("name"), email = req.queryParams("email"))
            res.status(201)
            "ok"
        }

        Spark.patch("/update/:id") { req, _ ->
            accountDao.update(
                    id = req.params("id").toInt(),
                    name = req.queryParams("name"),
                    email = req.queryParams("email")
            )
            "ok"
        }

        Spark.delete("/delete/:id") { req, _ ->
            accountDao.delete(req.params("id").toInt())
            "ok"
        }
    }

    accountDao.accounts.forEach { logger.info(it.toString()) }
}
