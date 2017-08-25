package app.translation

import java.text.MessageFormat
import java.util.*

class MessageBundle(languageTag: String?) {

    private val DEFAULT_LOCALE = Locale.ENGLISH
    private val messages: ResourceBundle

    constructor(request: spark.Request) : this(app.login.RequestUtil.getSessionLocale(request)) {}

    init {
        val locale = if (languageTag != null) Locale(languageTag) else DEFAULT_LOCALE
        this.messages = ResourceBundle.getBundle("localization/messages", locale)
    }

    operator fun get(message: String): String {
        return messages.getString(message)
    }

    operator fun get(key: String, vararg args: Any): String {
        return MessageFormat.format(get(key), *args)
    }

}