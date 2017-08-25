package app.login

import app.todo.view.HtmlView
import app.translation.MessageBundle
import spark.utils.StringUtils

class MainView(private val localizations: MessageBundle,
               private val currentUser: String) : HtmlView() {

    override fun getHtml(): String {
        return """
<header>
    <nav>
        <ul id="chooseLanguage">
            <form>
                <li>
                    <button name="locale" value="de" style="background-image: url(/img/german.png);"></button>
                </li>
                <li>
                    <button name="locale" value="en" style="background-image: url(/img/english.png);"></button>
                </li>
            </form>
        </ul>
        <ul id="menu">
        ${if (StringUtils.isNotEmpty(currentUser))
            """  <li>
                    <form method="post" action="${LOGOUT}">
                        <button id="logout">${localizations.get("COMMON_NAV_LOGOUT")}</button>
                    </form>
                </li>
            """
        else
            """<li><a href="${LOGIN}"">${localizations.get("COMMON_NAV_LOGIN")}</a></li>"""
        }
        </ul>
    </nav>
</header>
                """
    }
}