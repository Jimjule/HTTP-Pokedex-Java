package routes;

import route.Route;
import routes.files.HealthCheckHTMLRoute;
import routes.structured.text.BulbasaurResponse;
import routes.structured.text.CharmanderResponse;
import routes.structured.text.SquirtleResponse;

public class RouteMatcher {
    public static Route getRoute(String path) {
        Route route = null;
        try {
            switch (path) {
                case "/health-check.html":
                    route = new HealthCheckHTMLRoute();
                    break;
                case "/pokemon/id/1":
                    route = new BulbasaurResponse();
                    break;
                case "/pokemon/id/4":
                    route = new CharmanderResponse();
                    break;
                case "/pokemon/id/7":
                    route = new SquirtleResponse();
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
