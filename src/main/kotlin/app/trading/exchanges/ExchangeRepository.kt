package app.trading.exchanges

import java.util.*

class ExchangeRepository {
    val exchanges: List<Exchange> = Arrays.asList(BitstampExchange())
}