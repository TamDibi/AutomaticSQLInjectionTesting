package de.saarland.thesis.sqlinjection;

/**
 *
 * SQL Injector interface
 */
public interface SqlInjector {

    void setURL(String url);

    String getURL();

    void run();
    
    boolean isSuccessful();
}
