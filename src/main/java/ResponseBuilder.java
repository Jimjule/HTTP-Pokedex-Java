import HTTPServer.Codes;
import HTTPServer.Route;

import routes.*;

import HTTPServer.Response;

public class ResponseBuilder {
    public static Response responseHandler(String method, String path, String body, Response response) {

        Route route = RouteMatcher.getRoute(path);

        if (checkRouteNotFound(path, route, response)) return response;

        String responseCode = getResponseCode(method, route);
        response.setParams(responseCode);

        for (String header : route.getHeaders()) {
            response.addHeader(header);
        }
        route.performRequest(method, response, body, path);
        if (!route.getRouteIsFound()) {
            setRouteNotFound(response);
        }

        return response;
    }

    private static boolean checkRouteNotFound(String path, Route route, Response response) {
        if (route == null) {
            setRouteNotFound(response);
            return true;
        }

        return false;
    }

    private static void setRouteNotFound(Response response) {
        response.setParams(Codes._404.getCode());
        response.setBody("");
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
