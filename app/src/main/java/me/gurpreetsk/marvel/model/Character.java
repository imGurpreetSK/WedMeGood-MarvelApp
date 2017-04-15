package me.gurpreetsk.marvel.model;

import android.os.Parcel;
import android.os.Parcelable;

import ckm.simple.sql_provider.annotation.SimpleSQLColumn;
import ckm.simple.sql_provider.annotation.SimpleSQLTable;

/**
 * Created by gurpreet on 14/04/17.
 */

@SimpleSQLTable(table = "characters", provider = "MarvelProvider")
public class Character implements Parcelable {

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

    public Character(Parcel in) {
        String[] data = new String[4];
        in.readStringArray(data);
        this.id = data[0];
        this.name = data[1];
        this.description = data[2];
        this.thumbnail = data[3];
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeStringArray(new String[]{this.id,
                this.name,
                this.description,
                this.thumbnail});
    }

    public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {
        public Character createFromParcel(Parcel in) {
            return new Character(in);
        }

        public Character[] newArray(int size) {
            return new Character[size];
        }
    };

}
