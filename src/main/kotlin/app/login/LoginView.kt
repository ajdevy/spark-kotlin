package app.login

import app.todo.view.HtmlView
import app.translation.MessageBundle
import spark.utils.StringUtils

class LoginView(private val localizations: MessageBundle,
                private val loginRedirect: String = "",
                val authenticationFailed: Boolean = false,
                val authenticationSucceeded: Boolean = false,
                val loggedOut: Boolean = true,
                val currentUser: String = "") : HtmlView() {

    override fun getHtml(): String {
        return """
<form id="loginForm" method="post">
    ${when {
            authenticationFailed -> """<p class="bad notification">${localizations.get("LOGIN_AUTH_FAILED")}</p>"""
            authenticationSucceeded -> """<p class="good notification">${localizations.get("LOGIN_AUTH_SUCCEEDED", currentUser)}</p>"""
            loggedOut -> """<p class="notification">${localizations.get("LOGIN_LOGGED_OUT")}</p>"""
            else -> ""
        }}
    <h1>${localizations.get("LOGIN_HEADING")}</h1>
    <p>${localizations.get("LOGIN_INSTRUCTIONS", PAGE_AFTER_LOGIN)}</p>
    <label>${localizations.get("LOGIN_LABEL_USERNAME")}</label>
    <input type="text" name="username" placeholder="${localizations.get("LOGIN_LABEL_USERNAME")}" value="" required>
    <label>${localizations.get("LOGIN_LABEL_PASSWORD")}</label>
    <input type="password" name="password" placeholder="${localizations.get("LOGIN_LABEL_PASSWORD")}" value="" required>
    ${if (StringUtils.isNotEmpty(loginRedirect))
            """<input type="hidden" name="loginRedirect" value="$loginRedirect">"""
        else ""}
    <input type="submit" value="${localizations.get("LOGIN_BUTTON_LOGIN")}">
</form>
                """
    }
}