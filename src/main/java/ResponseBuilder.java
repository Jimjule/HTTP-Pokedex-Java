import constants.Codes;

import routes.*;
import routes.files.HealthCheckHTMLRoute;

import route.Route;

public class ResponseBuilder {
    public static Response responseHandler(String method, String path, String body, Response response) {

        Route route = RouteMatcher.getRoute(path);

        if (checkRouteNotFound(path, route, response)) return response;

        String responseCode = getResponseCode(method, route);
        response.setParams(responseCode);

        for (String header : route.getHeaders()) {
            response.addHeader(header);
        }
        if (path.equals("/health-check.html")) {
            HealthCheckHTMLRoute healthCheckHTMLRoute = new HealthCheckHTMLRoute();
            response.setFile(healthCheckHTMLRoute.getFile());
        } else {
            response.setBody(route.getBody());
        }

        return response;
    }

    private static boolean checkRouteNotFound(String path, Route route, Response response) {
        if (route == null) {
            response.setParams(Codes._404.getCode());
            response.setBody("");
            return true;
        }

        return false;
    }

    private static String getResponseCode(String method, Route route) {
        String responseCode;
        if (!route.getAllow().contains(method)) {
            responseCode = Codes._405.getCode();
        } else {
            responseCode = Codes._200.getCode();
        }
        return responseCode;
    }
}
