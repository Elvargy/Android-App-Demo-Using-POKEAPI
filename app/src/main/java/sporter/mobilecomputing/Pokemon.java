package sporter.mobilecomputing;

/**
 * Created by Shane on 31/12/2017.
 */

public class Pokemon {

    private String url;
    private String name;

    public Pokemon(String url, String name) {
        this.url = url;
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public String getName() {
        return name;
    }
}
