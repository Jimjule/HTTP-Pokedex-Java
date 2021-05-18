import HTTPServer.Route;

import routes.*;

import HTTPServer.Response;
import HTTPServer.ResponseHelper;

public class ResponseBuilder {
    public static Response responseHandler(String method, String path, String body, Response response) {

        Route route = RouteMatcher.getRoute(path);

        if (ResponseHelper.checkRouteNotFound(response, route)) return response;

        String responseCode = ResponseHelper.getResponseCode(method, route);
        response.setParams(responseCode);

        ResponseHelper.setResponseHeaders(response, route);
        route.performRequest(method, response, body, path);
        ResponseHelper.checkRouteParamsFound(response, route);

        return response;
    }
}
