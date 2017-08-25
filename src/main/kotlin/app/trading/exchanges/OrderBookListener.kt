package app.trading.exchanges

interface OrderBookListener {

    fun onOrderBookEvent(orderBookEvent:OrderBookEvent)
}