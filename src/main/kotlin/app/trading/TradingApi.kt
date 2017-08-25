package app.trading

import app.trading.bots.BotManager
import app.trading.exchanges.ExchangeRepository
import spark.Spark

val TRADING_API_URL = "/trading"

fun startTradingApi() {

    val exchangeRepository = ExchangeRepository()
    val tradingBotManager = BotManager(exchangeRepository)

    Spark.path(TRADING_API_URL) {
        Spark.get("/scalper") { _, _ ->
            tradingBotManager.startScalpingBot()
            "scalping bot started"
        }

        Spark.get("/stop") { _, _ ->
            tradingBotManager.stopAllBots()
            "bots stopped"
        }
    }
}