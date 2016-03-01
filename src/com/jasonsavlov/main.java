package com.jasonsavlov;


import java.util.ArrayList;

/**
 * Created by jason on 2/23/16.
 */
public class main
{
    // An array of URL strings used in this program.
    public static final String[] page_urls = {
            "https://en.wikipedia.org/wiki/Java_(programming_language)",
            "https://en.wikipedia.org/wiki/Scala_(programming_language)",
            "https://en.wikipedia.org/wiki/Python_(programming_language)",
            "https://en.wikipedia.org/wiki/C_(programming_language)",
            "https://en.wikipedia.org/wiki/Swift_(programming_language)",
            "https://en.wikipedia.org/wiki/C_Sharp_(programming_language)",
            "https://en.wikipedia.org/wiki/PHP",
            "https://en.wikipedia.org/wiki/JavaScript",
            "https://en.wikipedia.org/wiki/C%2B%2B",
            "https://en.wikipedia.org/wiki/Objective-C"
    };

    public static void main(String[] args)
    {
        ArrayList<WebPage> webPageArray = new ArrayList<WebPage>();
        Thread[] pageThreads;
        for (String s : page_urls) {
            WebPage wp = new WebPage(s);
            webPageArray.add(wp);
        }

        pageThreads = new Thread[webPageArray.size()];
        for (int i = 0; i < pageThreads.length; i++) {
            pageThreads[i] = new Thread(new PageDownloader(webPageArray.get(i)));
            pageThreads[i].start();
        }

        for (int q = pageThreads.length - 1; q >= 0; q--) {
            try {
                pageThreads[q].join();
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
        }

        System.out.println("Program complete.");
    }
}
