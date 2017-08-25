package app.trading.bots

import app.trading.exchanges.ExchangeRepository

class BotManager(private val exchangeRepository: ExchangeRepository) {

    private val bots = ArrayList<TradingBot>()

    fun startScalpingBot() {
        val bot = ScalpingBot(exchangeRepository)
        bots.add(bot)
        bot.startTrading()
    }

    fun stopAllBots() {

        bots.forEach { bot -> bot.stopTrading() }
        bots.clear()

        exchangeRepository.exchanges.forEach { it.stopMonitoring() }
    }
}