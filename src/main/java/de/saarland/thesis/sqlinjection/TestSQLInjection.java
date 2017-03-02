/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.saarland.thesis.sqlinjection;

import de.saarland.thesis.filter.URLFilter;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author sireto
 */
public class TestSQLInjection {

    SqlInjector pageInjector;

    public TestSQLInjection(SqlInjector injector) {
        this.pageInjector = injector;
    }

    public void run(String inputFile) {

        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new FileReader(inputFile));

            List<String> filteredUrls = new ArrayList<String>();

            String url = null;
            while ((url = reader.readLine()) != null) {
                if (!url.isEmpty()) {
                    pageInjector.setURL(url);
                    pageInjector.run();

                    if (pageInjector.isSuccessful()) {
                        System.out.println("SQL injection was successful");
                    } else {
                        System.out.println("SQL injection failed");
                    }
                }
            }

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

}
