package me.gurpreetsk.marvel.utils;

/**
 * Created by gurpreet on 14/04/17.
 */

public class Endpoints {

    public static final String BASE_URL = "http://gateway.marvel.com";

    public static String getComicsUrl() {
        return BASE_URL + "/v1/public/comics";
    }

    public static String getCharactersUrl() {
        return BASE_URL + "/v1/public/characters";
    }

}
