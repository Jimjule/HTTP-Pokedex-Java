package routes;

import HTTPServer.Route;
import HTTPServer.Router;
import routes.files.HealthCheckHTMLRoute;
import routes.structured.text.PokedexResponse;

public class RouteMatcher implements Router {
    public Route getRoute(String path) {
        Route route = null;
        try {
            if (path.equals("/health-check.html")) {
                route = new HealthCheckHTMLRoute();
            } else if (path.contains("/pokemon/id/")) {
                route = new PokedexResponse();
            }
        } catch (NullPointerException ignore) {
            return null;
        }
        return route;
    }
}
