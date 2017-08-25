package app.trading.exchanges

import com.pusher.client.Pusher
import com.pusher.client.channel.Channel
import com.pusher.client.connection.ConnectionEventListener
import com.pusher.client.connection.ConnectionState
import com.pusher.client.connection.ConnectionStateChange
import java.lang.Exception

class BitstampExchange : Exchange {

    private val PUSHER_KEY = "de504dc5763aeef9ff52"
    private val pusher = Pusher(PUSHER_KEY)

    private val orderBookListeners = ArrayList<OrderBookListener>()

    private val ORDER_BOOK_CHANNEL = "diff_order_book_ethusd"
    private val ORDER_BOOK_CHANNEL_EVENT = "data"

    private var orderBookChannel: Channel? = null
    private val orderBookChannelListener = { _: String, event: String, data: String ->
        println("Received event with data: " + data)
        notifyOrderBookListeners(OrderBookEvent(event, data))
    }

    override fun startOrderBookMonitoring(orderBookListener: OrderBookListener) {
        orderBookListeners.add(orderBookListener)
        startPusherIfNotStarted()
    }

    override fun removeOrderBookListener(orderBookListener: OrderBookListener) {
        orderBookListeners.remove(orderBookListener)
    }

    private fun startPusherIfNotStarted() {
        if (!isMonitoringOrderBook()) {
            pusher.connect(listenForPusherConnectionEvents(), ConnectionState.ALL)

            // Subscribe to a orderBookChannel
            orderBookChannel = pusher.subscribe(ORDER_BOOK_CHANNEL)

            // Bind to listen for events
            orderBookChannel?.bind(ORDER_BOOK_CHANNEL_EVENT, orderBookChannelListener)
        }
    }

    private fun isMonitoringOrderBook(): Boolean {
        return orderBookChannel?.isSubscribed ?: false
    }

    private fun notifyOrderBookListeners(event: OrderBookEvent) {
        orderBookListeners.forEach { it.onOrderBookEvent(event) }
    }

    override fun stopMonitoring() {
        orderBookChannel?.unbind(ORDER_BOOK_CHANNEL_EVENT, orderBookChannelListener)
        pusher.unsubscribe(ORDER_BOOK_CHANNEL)
        pusher.disconnect()
    }

    private fun listenForPusherConnectionEvents() = object : ConnectionEventListener {
        override fun onConnectionStateChange(change: ConnectionStateChange) {
            println("State changed to ${change.currentState}" +
                    " from " + change.previousState)
        }

        override fun onError(message: String, code: String, e: Exception) {
            println("There was a problem connecting!")
        }
    }
}