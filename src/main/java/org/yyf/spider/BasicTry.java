package org.yyf.spider;

import org.apache.http.client.fluent.Content;
import org.apache.http.client.fluent.Request;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Random;

/**
 * Created by tobi on 16-6-30.
 */
public class BasicTry {
    static Random random = new Random(1);
    public static void main(String[] args) throws IOException {
        String url = "http://www.tuicool.com/articles/QfEri2";
        Connection connect = Jsoup.connect(url);
        Document document = connect.get();
        Elements img = document.getElementsByTag("img");
        for (Element element : img) {
            System.out.println(element);
            String src = element.attr("src");
            System.out.println(src);
            imageExtractor(src);
        }
    }

    static void imageExtractor(String imageUrl) throws IOException {
        Content content = Request.Get(imageUrl).execute().returnContent();
        int name =random.nextInt(10000);
        FileOutputStream fileOutputStream = new FileOutputStream("" + name + ".png");
        fileOutputStream.write(content.asBytes());
    }
}
