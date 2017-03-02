package de.saarland.thesis.urlcrawler;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.net.SocketTimeoutException;
import java.util.HashSet;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.net.ssl.SSLHandshakeException;
import org.jsoup.HttpStatusException;
import org.jsoup.Jsoup;
import org.jsoup.UnsupportedMimeTypeException;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 *
 * @author sireto
 */
public class Crawler {

    private String baseUrl = "";
    private Set<String> crawledURLs = null;
    private Set<String> toCrawlURLs = null;

    public Crawler(String baseUrl) {
        this.baseUrl = baseUrl;
        this.crawledURLs = new HashSet<String>();
        this.toCrawlURLs = new HashSet<String>();
    }

    public void run() {
        // crawl the base url first
        crawl(baseUrl);

        do {
            Set<String> clonedToCrawlUrls = new HashSet<>();
            clonedToCrawlUrls.addAll(toCrawlURLs);

            clonedToCrawlUrls.stream().forEach((url) -> {
                System.out.println("url:" + url);
                crawl(url);
            });
        } while (toCrawlURLs.size() > 0);

    }

    private void crawl(String url) {

        try {
            Document doc = Jsoup.connect(url).get();

            // add url to crawled list
            crawledURLs.add(url);

            Elements anchorTags = doc.getElementsByTag("a");

            for (Element anchorTag : anchorTags) {
                String nextUrl = anchorTag.attr("abs:href");
                //System.out.println(nextUrl);

                // crawl only url of the domain
                if (!nextUrl.contains(baseUrl)) {
                    continue;
                }

                // add to crawl list if not already crawled; 
                // check if present in crawled set
                if (!crawledURLs.contains(nextUrl) && !toCrawlURLs.contains(nextUrl)) {
                    //System.out.println("Adding to list : "+nextUrl);
                    toCrawlURLs.add(nextUrl);
                }
            }

        } catch (SSLHandshakeException she) {
        } catch (UnsupportedMimeTypeException umte) {
        } catch (HttpStatusException hse) {
        } catch (SocketTimeoutException ex) {
        } catch (IOException ex) {
            Logger.getLogger(Crawler.class.getName()).log(Level.SEVERE, null, ex);
        }

        toCrawlURLs.remove(url);
    }

    public void print() {
        for (String url : crawledURLs) {
            System.out.println(url);
        }
    }

    public Set<String> getCrawledURls() {
        return this.crawledURLs;
    }

    public void save(String outputFile) {
        BufferedWriter writer = null;
        try {
            writer = new BufferedWriter(new FileWriter(outputFile));
            
            for(String url: crawledURLs){
                writer.write(url+"\n");
            }
        } catch (IOException ex) {
            Logger.getLogger(Crawler.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
            if(writer!=null){
                try {
                    writer.close();
                } catch (IOException ex) {
                    Logger.getLogger(Crawler.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }

}
