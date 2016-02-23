package com.jasonsavlov;

/**
 * Created by jason on 2/23/16.
 */
public class WebPage
{

    private final String pageURL;
    private JSHashTable hashTable = new JSHashTable();

    public WebPage(String pageURL) {
        this.pageURL = pageURL;
    }

    public JSHashTable getHashTable() {
        return this.hashTable;
    }

    public String getPageURL() {
        return this.pageURL;
    }
}
