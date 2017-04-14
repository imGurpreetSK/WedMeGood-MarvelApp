package me.gurpreetsk.marvel.model;

import ckm.simple.sql_provider.annotation.SimpleSQLColumn;
import ckm.simple.sql_provider.annotation.SimpleSQLTable;

/**
 * Created by gurpreet on 14/04/17.
 */

@SimpleSQLTable(table = "characters", provider = "MarvelProvider")
public class Character {

    @SimpleSQLColumn("id")
    private String id;
    @SimpleSQLColumn("name")
    private String name;
    @SimpleSQLColumn("description")
    private String description;
    @SimpleSQLColumn("thumbnail")
    private String thumbnail;
    //comics


    public Character() {
    }

    public Character(String id, String name, String description, String thumbnail) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.thumbnail = thumbnail;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }
}
