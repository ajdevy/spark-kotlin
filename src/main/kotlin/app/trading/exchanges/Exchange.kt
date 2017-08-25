package app.trading.exchanges

interface Exchange {

    fun startOrderBookMonitoring(orderBookListener: OrderBookListener)

    fun removeOrderBookListener(orderBookListener: OrderBookListener)

    fun stopMonitoring()
}