package routes.structured.text;

import constants.Headers;
import route.Route;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CharmanderResponse implements Route {
    private static final String body = "{\"id\":4,\"name\":\"Charmander\"}";
    private ArrayList<String> headers = new ArrayList<>();
    private static final List<String> allow = Arrays.asList("GET", "HEAD");

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
}
