package com.jasonsavlov;

import com.sun.istack.internal.NotNull;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;

/**
 * Created by jason on 2/23/16.
 */
public class PageDownloader implements Runnable
{
    private final WebPage mainPage;
    private DownloadActionListener actionListener = null;

    @Override
    public void run()
    {
        // Start out by getting a Document from JSoup based
        // on the URL of the mainPage
        Document doc = null;
        System.out.println("Downloading from " + mainPage.getPageURL());
        try {
            doc = Jsoup.connect(mainPage.getPageURL()).get();
        } catch (IOException ex) {

        }

        System.out.println("Finished downloading from " + mainPage.getPageURL());

        // Next, get the parsed body from the Document
        String parsedBody = doc.body().text();

        // Split up the words
        String[] words = parsedBody.split("\\s+");

        // Remove punctuation and other non-alphanumeric characters from the list of words.
        // Also make them all lowercase.
        for (int i = 0; i < words.length; i++) {
            words[i] = words[i].replaceAll("[^a-zA-Z]", "").toLowerCase();
        }

        // Get the JSHashTable from mainPage
        JSHashTable hashTable = mainPage.getHashTable();

        for (String s : words) {
            hashTable.add(s);
        }

        if (actionListener != null) {
            actionListener.finishedDownloadingContent(this.mainPage);
        }

    }



    public PageDownloader(@NotNull WebPage page) {
        mainPage = page;
    }

    public PageDownloader(@NotNull WebPage page, DownloadActionListener listener)
    {
        this.mainPage = page;
        this.actionListener = listener;
    }
}
