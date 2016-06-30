package org.yyf.spider;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * Created by tobi on 16-6-30.
 */
public class Producer implements Runnable {
    //待保存的图片url
    LinkedBlockingQueue<String> toSaveQueue;
    //已经保存过的图片url
    LinkedBlockingQueue<String> savedQueue;
    //待爬的url链接
    LinkedBlockingQueue<String> toCrawlURL;
    //已爬的url链接
    LinkedBlockingQueue<String> crawledURL;

    public Producer(LinkedBlockingQueue<String> toSaveQueue, LinkedBlockingQueue<String> savedQueue, LinkedBlockingQueue<String> toCrawlURL,  LinkedBlockingQueue<String> crawledURL) {
        this.toSaveQueue = toSaveQueue;
        this.savedQueue = savedQueue;
        this.toCrawlURL = toCrawlURL;
        this.crawledURL = crawledURL;
    }

    @Override
    public void run() {
//        String url = "http://www.tuicool.com/articles/QfEri2";

        try {
            String url = toCrawlURL.poll();
            Connection connect = Jsoup.connect(url);
            Document document = connect.get();
            processImageTag(document);
            processURLTag(document);
            crawledURL.offer(url);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private void processURLTag(Document document) {
        Elements aTags = document.getElementsByTag("a");
        for (Element element : aTags) {
            String src = element.attr("href");
            if (!crawledURL.contains(src)) {
                toCrawlURL.offer(src);
            }
        }
    }

    private void processImageTag(Document document) {
        Elements img = document.getElementsByTag("img");
        for (Element element : img) {
            String src = element.attr("src");
            if (!savedQueue.contains(src)) {
                toSaveQueue.offer(src);
            }
        }
    }

}
