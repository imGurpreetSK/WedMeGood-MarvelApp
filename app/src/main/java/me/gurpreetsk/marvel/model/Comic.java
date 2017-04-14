package me.gurpreetsk.marvel.model;

import ckm.simple.sql_provider.annotation.SimpleSQLColumn;
import ckm.simple.sql_provider.annotation.SimpleSQLTable;

/**
 * Created by gurpreet on 14/04/17.
 */

@SimpleSQLTable(table = "comics", provider = "MarvelProvider")
public class Comic {

    @SimpleSQLColumn("id")
    private String id;
    @SimpleSQLColumn("title")
    private String title;
    @SimpleSQLColumn("thumbnail")
    private String thumbnail;
    @SimpleSQLColumn("resource_uri")
    private String resourceURI;
    @SimpleSQLColumn("issue_number")
    private String issueNumber;
    @SimpleSQLColumn("format")
    private String format;
    @SimpleSQLColumn("page_count")
    private String pageCount;

    public Comic() {
    }

    public Comic(String id, String title, String thumbnail, String resourceURI, String issueNumber,
                 String format, String pageCount) {
        this.id = id;
        this.title = title;
        this.thumbnail = thumbnail;
        this.resourceURI = resourceURI;
        this.issueNumber = issueNumber;
        this.format = format;
        this.pageCount = pageCount;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public String getResourceURI() {
        return resourceURI;
    }

    public void setResourceURI(String resourceURI) {
        this.resourceURI = resourceURI;
    }

    public String getIssueNumber() {
        return issueNumber;
    }

    public void setIssueNumber(String issueNumber) {
        this.issueNumber = issueNumber;
    }

    public String getFormat() {
        return format;
    }

    public void setFormat(String format) {
        this.format = format;
    }

    public String getPageCount() {
        return pageCount;
    }

    public void setPageCount(String pageCount) {
        this.pageCount = pageCount;
    }
}
