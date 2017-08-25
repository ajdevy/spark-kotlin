package app.login;

public class LoginController {

//    public static spark.Route serveLoginPage = (spark.Request request, spark.Response response) -> {
//        java.util.Map<String, Object> model = new java.util.HashMap<>();
//        model.put("loggedOut", RequestUtil.removeSessionAttrLoggedOut(request));
//        model.put("loginRedirect", RequestUtil.removeSessionAttrLoginRedirect(request));
//        return ViewUtil.render(request, model, Path.Template.LOGIN);
//    };
//
//    public static spark.Route handleLoginPost = (spark.Request request, spark.Response response) -> {
//        java.util.Map<String, Object> model = new java.util.HashMap<>();
//        if (!app.user.UserController.authenticate(RequestUtil.getUsernameParam(request), RequestUtil.getPasswordParam(request))) {
//            model.put("authenticationFailed", true);
//            return ViewUtil.render(request, model, Path.Template.LOGIN);
//        }
//        model.put("authenticationSucceeded", true);
//        request.session().attribute("currentUser", RequestUtil.getUsernameParam(request));
//        if (RequestUtil.getLoginRedirectParam(request) != null) {
//            response.redirect(RequestUtil.getLoginRedirectParam(request));
//        }
//        return ViewUtil.render(request, model, Path.Template.LOGIN);
//    };

//    public static spark.Route handleLogoutPost = (spark.Request request, spark.Response response) -> {
//        request.session().removeAttribute("currentUser");
//        request.session().attribute("loggedOut", true);
//        response.redirect(LOGIN);
//        return null;
//    };

    // The origin of the request (request.pathInfo()) is saved in the session so
    // the user can be redirected back after login
//    public static void ensureUserIsLoggedIn(spark.Request request, spark.Response response) {
//        if (request.session().attribute("currentUser") == null) {
//            request.session().attribute("loginRedirect", request.pathInfo());
//            response.redirect(Path.Web.LOGIN);
//        }
//    }
}