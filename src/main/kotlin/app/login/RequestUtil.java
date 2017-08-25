package app.login;

public class RequestUtil {

    public static String getQueryLocale(spark.Request request) {
        return request.queryParams("locale");
    }

    public static String getUsernameParam(spark.Request request) {
        return request.queryParams("username");
    }

    public static String getPasswordParam(spark.Request request) {
        return request.queryParams("password");
    }

    public static String getLoginRedirectParam(spark.Request request) {
        return request.queryParams("loginRedirect");
    }

    public static String getSessionLocale(spark.Request request) {
        return request.session().attribute("locale");
    }

    public static String getSessionCurrentUser(spark.Request request) {
        return request.session().attribute("currentUser");
    }

    public static boolean removeSessionAttrLoggedOut(spark.Request request) {
        Object loggedOut = request.session().attribute("loggedOut");
        request.session().removeAttribute("loggedOut");
        return loggedOut != null;
    }

    public static String removeSessionAttrLoginRedirect(spark.Request request) {
        String loginRedirect = request.session().attribute("loginRedirect");
        request.session().removeAttribute("loginRedirect");
        return loginRedirect;
    }

    public static boolean clientAcceptsHtml(spark.Request request) {
        String accept = request.headers("Accept");
        return accept != null && accept.contains("text/html");
    }

    public static boolean clientAcceptsJson(spark.Request request) {
        String accept = request.headers("Accept");
        return accept != null && accept.contains("application/json");
    }

}
