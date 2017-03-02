/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.saarland.thesis.filter;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.lucene.wordnet.SynonymMap;

/**
 *
 * @author sireto
 */
public class SynonymFinder {
    
    private static SynonymFinder instance = new SynonymFinder();
    
    private SynonymMap synonymMap = null;
    
    private SynonymFinder(){
        try {
            synonymMap = new SynonymMap(new FileInputStream("wordnet/wn_s.pl"));
        } catch (FileNotFoundException ex) {
            Logger.getLogger(SynonymFinder.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(SynonymFinder.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public static SynonymFinder getInstance(){
        if(instance==null){
            instance = new SynonymFinder();
        }
        return instance;
    }
    
    public Set<String> getSynonyms(String word){
        Set<String> synonymSet = new HashSet<String>();
        if(synonymMap!=null){
            for(String synonym: synonymMap.getSynonyms(word)){
                synonymSet.add(synonym);
            }
        }
        return synonymSet;
    }
    
    public static void main(String[] args) throws FileNotFoundException, IOException {
        String[] words = new String[]{"hard", "woods", "forest", "wolfish", "password"};
        
        SynonymFinder finder = SynonymFinder.getInstance();
        for (int i = 0; i < words.length; i++) {
            System.out.println(words[i]+":"+finder.getSynonyms(words[i]));
        }

    }

}
