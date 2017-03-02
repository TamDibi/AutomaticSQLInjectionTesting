package de.saarland.thesis.filter;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author sireto
 */
public class URLFilter {

    private String inputFile;
    private String outputFile;
    private FilterContext context;

    public URLFilter(String inputFile, String outputFile, FilterContext context) {
        this.inputFile = inputFile;
        this.outputFile = outputFile;
        this.context = context;
    }

    public void run() {
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new FileReader(inputFile));

            List<String> filteredUrls = new ArrayList<String>();

            String url = null;
            while ((url = reader.readLine()) != null) {
                if (!url.isEmpty()) {
                    // add all URLs that pass the filter
                    if (passURL(url) == true) {
                        filteredUrls.add(url);
                    }
                }
            }

            // write the filteredUrls to file
            saveToFile(filteredUrls);

        } catch (FileNotFoundException ex) {
            Logger.getLogger(URLFilter.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(URLFilter.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException ex) {
                    Logger.getLogger(URLFilter.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }

    public boolean passURL(String url) {
        // if no context, allow all URLs to pass
        if (context == null) {
            return true;
        } else {

            // allow URL if it contains any of the root words from context
            Set<String> rootWords = context.getRootWords();
            for (String word : rootWords) {
                if (url.contains(word)) {
                    return true;
                }
            }

            // allow URL if it contains any related words from context
            Set<String> relatedWords = context.getRelatedWords();
            for (String word : relatedWords) {
                if (url.contains(word)) {
                    return true;
                }
            }

        }

        // return false by default
        return false;
    }

    private void saveToFile(List<String> filteredUrlList) {
        BufferedWriter writer = null;
        try {
            writer = new BufferedWriter(new FileWriter(outputFile));
            for (String url : filteredUrlList) {
                writer.write(url);
                writer.newLine();
            }
            System.out.println("Filtered URLs saved to file:"+outputFile);
        } catch (IOException ex) {
            Logger.getLogger(URLFilter.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            if (writer != null) {
                try {
                    writer.close();
                } catch (IOException ex) {
                    Logger.getLogger(URLFilter.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        
    }

    public static void main(String[] args){
        String inputFile = "urls.txt";
        String outputFile = "filtered.txt";
        
        // Filter context
        FilterContext context = new AuthenticationContext();
        
        URLFilter filter = new URLFilter(inputFile, outputFile, context);
        filter.run();
    }

}
