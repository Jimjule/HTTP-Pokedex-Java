package routes;

import route.Route;
import routes.files.HealthCheckHTMLRoute;

public class RouteMatcher {
    public static Route getRoute(String path) {
        Route route = null;
        try {
            switch (path) {
                case "/health-check.html":
                    route = new HealthCheckHTMLRoute();
                    break;
                default:
                    break;
            }
        } catch (NullPointerException ignore) {
            return null;
        }
        return route;
    }
}
