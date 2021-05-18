package routes.structured.text;

import HTTPServer.Response;
import HTTPServer.Headers;
import HTTPServer.Route;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class PokedexResponse implements Route {
    private String body;
    private ArrayList<String> headers = new ArrayList<>();
    private static final List<String> allow = Arrays.asList("GET", "POST", "HEAD");
    private boolean routeIsFound;

    private HashMap<String, String> pokemon = new HashMap<>();

    public PokedexResponse() {
        pokemon.put("1", "{\"id\":1,\"name\":\"Bulbasaur\"}");
        pokemon.put("4", "{\"id\":4,\"name\":\"Charmander\"}");
        pokemon.put("7", "{\"id\":7,\"name\":\"Squirtle\"}");
    }

    public void getPokemon(String id) {
        body = pokemon.get(id);
    }

    public void addPokemon(String id, String body) {
        pokemon.put(id, body);
    }

    public boolean pokemonExists(String id) {
        return pokemon.get(id) != null;
    }

    @Override
    public String getBody() {
        return body;
    }

    @Override
    public ArrayList<String> getHeaders() {
        headers.add(Headers.CONTENT_TYPE_JSON.getHeader());
        headers.add(formatAllow());
        return headers;
    }

    @Override
    public String formatAllow() {
        String allowHeader = Headers.ALLOW.getHeader();
        allowHeader += String.join(", ", allow);
        return allowHeader;
    }

    @Override
    public List<String> getAllow() {
        return allow;
    }

    @Override
    public void performRequest(String method, Response response, String body, String path) {
        String id = path.replace("/pokemon/id/", "");
        PokedexResponse pokedexResponse = new PokedexResponse();
        boolean pokemonExists = pokedexResponse.pokemonExists(id);

        if (pokemonExists && method.equals("GET")) {
            pokedexResponse.getPokemon(id);
            response.setBody(pokedexResponse.getBody());
            this.routeIsFound = true;
        } else if (!pokemonExists && method.equals("POST")) {
            pokedexResponse.addPokemon(id, body);
            pokedexResponse.getPokemon(id);
            response.setBody(pokedexResponse.getBody());
            this.routeIsFound = true;
        } else {
            this.routeIsFound = false;
        }
    }

    public boolean getRouteIsFound() {
        return this.routeIsFound;
    }
}
