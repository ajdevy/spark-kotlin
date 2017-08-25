package app.login

import app.translation.MessageBundle
import app.user.UserController
import app.user.UserDao
import spark.Request
import spark.Response
import spark.Spark

val LOGIN = "/login/"
val LOGOUT = "/logout/"
val PAGE_AFTER_LOGIN = "/"

val userDao = UserDao()
val userController = UserController(userDao)

fun startLoginUserApi() {

    Spark.get(PAGE_AFTER_LOGIN) { request, _ ->
        MainView(MessageBundle(request), currentUser = RequestUtil.getSessionCurrentUser(request))
    }

    Spark.path(LOGIN) {

        Spark.before("*", addTrailingSlashes)
        Spark.before("*", handleLocaleChange)

        Spark.post("/", handleLoginPost)

        Spark.get("/", handleLoginGet)
    }

    Spark.post(LOGOUT, handleLogoutPost)
}

val handleLoginGet = { request: Request, response: Response ->
    LoginView(MessageBundle(request),
            currentUser = RequestUtil.getSessionCurrentUser(request),
            loggedOut = RequestUtil.removeSessionAttrLoggedOut(request),
            loginRedirect = RequestUtil.removeSessionAttrLoginRedirect(request))
}

val handleLoginPost = { request: Request, response: Response ->
    val authenticationSuccessful = userController.authenticate(
            RequestUtil.getUsernameParam(request), RequestUtil.getPasswordParam(request))
    if (!authenticationSuccessful) {
        LoginView(MessageBundle(request), authenticationFailed = true)
    } else {
        request.session().attribute("currentUser", RequestUtil.getUsernameParam(request))
        if (RequestUtil.getLoginRedirectParam(request) != null) {
            response.redirect(RequestUtil.getLoginRedirectParam(request))
        }
        LoginView(MessageBundle(request), authenticationSucceeded = true,
                currentUser = RequestUtil.getUsernameParam(request))
    }
}

val handleLogoutPost = { request: Request, response: Response ->
    request.session().removeAttribute("currentUser")
    request.session().attribute("loggedOut", true)
    response.redirect(LOGIN)
}

// If a user manually manipulates paths and forgets to add
// a trailing slash, redirect the user to the correct path
var addTrailingSlashes = { request: Request, response: Response ->
    if (!request.pathInfo().endsWith("/")) {
        response.redirect(request.pathInfo() + "/")
    }
}

// Locale change can be initiated from any page
// The locale is extracted from the request and saved to the user's session
var handleLocaleChange = { request: Request, response: Response ->
    if (RequestUtil.getQueryLocale(request) != null) {
        request.session().attribute("locale", RequestUtil.getQueryLocale(request))
        response.redirect(request.pathInfo())
    }
}