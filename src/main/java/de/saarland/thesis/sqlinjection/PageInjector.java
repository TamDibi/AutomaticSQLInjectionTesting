package de.saarland.thesis.sqlinjection;

/**
 *
 * @author sireto
 */
public class PageInjector {
    public static void main(String[] args) {
        String url="http://localhost/mutillidae/index.php?page=login.php";
        
        //SqlInjector
        SqlInjector injector = new FirefoxSqlInjector();
        injector.setURL(url);
        
        injector.run();
        
        if(injector.isSuccessful()){
            System.out.println("SQL injection was successful");
        }else{
            System.out.println("SQL injection failed");
        }
        
    }
    
}
