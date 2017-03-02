package de.saarland.thesis;

import de.saarland.thesis.urlcrawler.Crawler;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author sireto
 */
public class Main {
    public static void main(String[] args) {
        
        String websiteUrl = "http://localhost/mutillidae";
        
        // create crawler object
        Crawler crawler = new Crawler(websiteUrl);
        
        // start crawling
        crawler.run();
        
        // print to console
        crawler.print();
        
        // save urls to a file
        String outputFile = "urls.txt";
        crawler.save(outputFile);
        
    }
    
}
