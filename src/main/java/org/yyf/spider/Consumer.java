package org.yyf.spider;

import org.apache.http.client.fluent.Content;
import org.apache.http.client.fluent.Request;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.yyf.spider.BasicTry.random;

/**
 * Created by tobi on 16-6-30.
 */
public class Consumer implements Runnable{
    //待保存的图片url
    LinkedBlockingQueue<String> toSaveQueue;
    //已经保存过的图片url
    LinkedBlockingQueue<String> savedQueue;
    String urlCandidate;
    public Consumer(String urlCandidate,LinkedBlockingQueue<String> toSaveQueue, LinkedBlockingQueue<String> savedQueue) {
        this.toSaveQueue = toSaveQueue;
        this.savedQueue = savedQueue;
        this.urlCandidate=urlCandidate;
    }

    @Override
    public void run() {
        try {
            imageExtractor(urlCandidate);
            savedQueue.offer(urlCandidate);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

     void imageExtractor(String imageUrl) throws IOException {
        if(imageUrl!=null){
            String imageName = imageUrl.substring(imageUrl.lastIndexOf("/")+1);
            Content content = Request.Get(imageUrl).execute().returnContent();
            FileOutputStream fileOutputStream = new FileOutputStream(imageName);
            fileOutputStream.write(content.asBytes());
            System.out.println("save "+imageName);
        }

    }

}
