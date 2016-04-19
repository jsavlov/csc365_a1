package com.jasonsavlov;


import javax.swing.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

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

    public static List<WebPage> webPages;

    public static void main(String[] args)
    {
        JFrame loadingFrame = new JFrame("LoadingDialog");
        loadingFrame.setContentPane(new LoadingDialog().panel1);
        loadingFrame.pack();
        loadingFrame.setVisible(true);
        List<WebPage> wps = new ArrayList<>();
        Thread[] pageThreads;
        for (String s : page_urls) {
            WebPage wp = new WebPage(s);
            wps.add(wp);
        }

        webPages = Collections.unmodifiableList(wps);

        pageThreads = new Thread[webPages.size()];
        for (int i = 0; i < pageThreads.length; i++) {
            pageThreads[i] = new Thread(new PageDownloader(webPages.get(i)));
            pageThreads[i].start();
        }

        for (int q = pageThreads.length - 1; q >= 0; q--) {
            try {
                pageThreads[q].join();
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
        }

        loadingFrame.setVisible(false);


        JFrame frame = new JFrame("MainWindow");
        MainWindow mw = new MainWindow();
        mw.setWebPageList(webPages);
        frame.setContentPane(mw.mainPanel);
        //frame.setContentPane(new MainWindow().mainPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);


        //System.out.println("Program complete.");
    }


}
