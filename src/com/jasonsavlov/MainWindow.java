package com.jasonsavlov;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

/**
 * Created by jason on 4/14/16.
 */
public class MainWindow implements DownloadActionListener
{
    private JTextField urlTextField;
    private JList urlList;
    private JButton beginButton;
    JPanel mainPanel;
    private JLabel closestMatchText;

    private DefaultListModel<WebPage> pageListModel;

    private List<WebPage> webPageList;


    public MainWindow()
    {
        pageListModel = new DefaultListModel<>();

        beginButton.addActionListener((ActionEvent e) -> {
               processURL(urlTextField.getText());
        });
    }

    public List<WebPage> getWebPageList()
    {
        return webPageList;
    }

    public void setWebPageList(List<WebPage> webPageList)
    {
        this.webPageList = webPageList;

        for (WebPage wp : this.webPageList)
        {
            pageListModel.addElement(wp);
        }

        urlList.setModel(pageListModel);
    }

    private void processURL(String url)
    {
        PageDownloader downloader = new PageDownloader(new WebPage(url), this);
        Thread urlDownloadThread = new Thread(downloader);
        urlDownloadThread.start();
        this.setStatusString("Downloading page source...");
    }

    private void setStatusString(String str)
    {
        synchronized (closestMatchText)
        {
            this.closestMatchText.setText(str);
        }
    }

    @Override
    public void finishedDownloadingContent(WebPage page)
    {
        System.out.println("finishedDownloadingContent(WebPage): " + page.getPageURL());

        CosineSimilarityCalculatorEngine engine = new CosineSimilarityCalculatorEngine(webPageList, page, this);
        new Thread(engine).start();
        this.setStatusString("Calculating similarity");
    }

    @Override
    public void finishedCalculatingSimilarity(CosineSimilarityCalculation.CosineSimilarityResult result)
    {
        // test it!
        System.out.println("Most similar page: " + result.page);
        this.setStatusString("Closest match: " + result.page);
    }
}
