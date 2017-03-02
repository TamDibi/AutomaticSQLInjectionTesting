package de.saarland.thesis.sqlinjection;

/**
 *
 * @author sireto
 */
public class Main {
    public static void main(String[] args) {
        SqlInjector injector = new FirefoxSqlInjector();
        TestSQLInjection sqlInjector = new TestSQLInjection(injector);
        
        String filteredURLFile = "filtered.txt";
        sqlInjector.run(filteredURLFile);
    }
    
}
