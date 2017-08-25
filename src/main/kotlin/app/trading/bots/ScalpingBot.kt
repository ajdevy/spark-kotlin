package app.trading.bots

import app.trading.exchanges.Exchange
import app.trading.exchanges.ExchangeRepository
import app.trading.exchanges.OrderBookEvent
import app.trading.exchanges.OrderBookListener
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import java.util.logging.Level
import java.util.logging.Logger

class ScalpingBot(private val exchangeRepository: ExchangeRepository) : TradingBot {

    private val logger = Logger.getLogger("ScalpingBot")!!

    private val tradingSubscriptions = CompositeDisposable()
    private val orderBookListeners = HashMap<Exchange, OrderBookListener>()

    override fun startTrading() {
        exchangeRepository.exchanges.forEach { exchange ->
            tradingSubscriptions.add(
                    listenForOrderBookEvents(exchange)
                            .doOnNext { exchangeEventPair ->
                                logger.info("got order book event: ${exchangeEventPair.event}, with data: ${exchangeEventPair.data}")
                                //TODO: compare/process data from exchange
                                //TODO: make/adjust request for sell/buy
                            }
                            .subscribe({ _ -> },
                                    { error -> logger.log(Level.SEVERE, "got an error while listening for order book events", error) }))
        }
    }

    private fun listenForOrderBookEvents(exchange: Exchange): Observable<OrderBookEvent> =
            Observable.create<OrderBookEvent> { subscriber ->
                val orderBookListener = object : OrderBookListener {
                    override fun onOrderBookEvent(orderBookEvent: OrderBookEvent) {
                        subscriber.onNext(orderBookEvent)
                    }
                }
                orderBookListeners.put(exchange, orderBookListener)
                exchange.startOrderBookMonitoring(orderBookListener)
            }

    override fun stopTrading() {
        //TODO: stop getting data from exchange
        //TODO: interrupt current request for trade
        orderBookListeners.forEach { exchange, listener -> exchange.removeOrderBookListener(listener) }
        orderBookListeners.clear()

        tradingSubscriptions.clear()
    }
}