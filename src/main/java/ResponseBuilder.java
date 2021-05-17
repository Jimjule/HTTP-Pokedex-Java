import HTTPServer.constants.Codes;
import HTTPServer.route.Route;

import routes.*;
import routes.files.HealthCheckHTMLRoute;

import HTTPServer.Response;
import routes.structured.text.PokedexResponse;

public class ResponseBuilder {
    public static Response responseHandler(String method, String path, String body, Response response) {

        Route route = RouteMatcher.getRoute(path);

        if (checkRouteNotFound(path, route, response)) return response;

        String responseCode = getResponseCode(method, route);
        response.setParams(responseCode);

        for (String header : route.getHeaders()) {
            response.addHeader(header);
        }
        setResponseBody(method, path, body, response, route);

        return response;
    }

    private static void setResponseBody(String method, String path, String body, Response response, Route route) {
        if (path.equals("/health-check.html")) {
            HealthCheckHTMLRoute healthCheckHTMLRoute = new HealthCheckHTMLRoute();
            response.setFile(healthCheckHTMLRoute.getFile());
        } else if (path.contains("/pokemon/id/")) {
            String id = path.replace("/pokemon/id/", "");
            PokedexResponse pokedexResponse = new PokedexResponse();
            boolean pokemonExists = pokedexResponse.pokemonExists(id);
            if (pokemonExists && method.equals("GET")) {
                pokedexResponse.getPokemon(id);
                response.setBody(pokedexResponse.getBody());
            } else if (!pokemonExists && method.equals("POST")) {
                pokedexResponse.addPokemon(id, body);
                pokedexResponse.getPokemon(id);
                response.setBody(pokedexResponse.getBody());
            } else {
                setRouteNotFound(response);
            }
        } else {
            response.setBody(route.getBody());
        }
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
