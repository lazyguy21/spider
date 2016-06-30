package org.yyf.spider;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * Created by tobi on 16-6-30.
 */
public class AdvancedSpider {


    public static void main(String[] args) {
        //待保存的图片url
        LinkedBlockingQueue<String> toSaveQueue = new LinkedBlockingQueue<>();
        //已经保存过的图片url
        LinkedBlockingQueue<String> savedQueue = new LinkedBlockingQueue<>();
        //待爬的url链接
        LinkedBlockingQueue<String> toCrawlURL = new LinkedBlockingQueue<>();
        //已爬的url链接
        LinkedBlockingQueue<String> crawledURL = new LinkedBlockingQueue<>();

        String url = "http://www.tuicool.com/articles/QfEri2";
        toCrawlURL.add(url);
        int threadCount = Runtime.getRuntime().availableProcessors() * 2;
        ExecutorService executorService = Executors.newFixedThreadPool(threadCount);
        executorService.submit(new Producer(toSaveQueue,savedQueue,toCrawlURL,crawledURL));


        ExecutorService executorService2 = Executors.newFixedThreadPool(threadCount);
        executorService2.submit(new Producer(toSaveQueue,savedQueue,toCrawlURL,crawledURL));

    }
}
