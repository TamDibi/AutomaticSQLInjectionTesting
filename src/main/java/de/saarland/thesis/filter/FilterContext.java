package de.saarland.thesis.filter;

import java.util.Set;

/**
 *
 * @author sireto
 */
public interface FilterContext {
    
    Set<String> getRootWords();
    
    Set<String> getRelatedWords();
}
