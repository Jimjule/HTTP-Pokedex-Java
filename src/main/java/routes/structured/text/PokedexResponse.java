package routes.structured.text;

import constants.Headers;
import route.Route;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class PokedexResponse implements Route {
    private String body;
    private ArrayList<String> headers = new ArrayList<>();
    private static final List<String> allow = Arrays.asList("GET", "HEAD");

    private HashMap<String, String> pokemon = new HashMap<>();

    public PokedexResponse() {
        pokemon.put("1", "{\"id\":1,\"name\":\"Bulbasaur\"}");
        pokemon.put("4", "{\"id\":4,\"name\":\"Charmander\"}");
        pokemon.put("7", "{\"id\":7,\"name\":\"Squirtle\"}");
    }

    public void getPokemon(String id) {
        body = pokemon.get(id);
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

    public boolean pokemonExists(String id) {
        return pokemon.get(id) != null;
    }
}
