/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package webserver.http;

import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author Carlos Coelho
 */
public class URI {

    private static final String QUERY_BEGINING = "\\?";
    private static final String QUERY_SEPARATOR = "&";
    private static final String KEY_VALUE_SEPARATOR = "=";

    //Uri raw text
    private String uri;

    //URI sem querry
    private String querylessURI;
    private Map<String, String> queryValues;

    /**
     * Inicializa o campo URI, separando-o por conteúdo 
     * @param uri 
     */
    public URI(String uri) {
        this.uri = uri;
        if (uri==null) {
            throw new IllegalArgumentException("URI cannot be null!");
        }
        queryValues=new HashMap<>();
        
        String temp[] = uri.split(QUERY_BEGINING);
        if (temp[0].trim().isEmpty()) {
            throw new IllegalArgumentException("URI cannot be empty!");
        }
        querylessURI = temp[0];

        if (temp.length > 1) {
            fillQueryMap(temp[1]);
        }
    }

    private void fillQueryMap(String query) {
        String temp[] = query.split(QUERY_SEPARATOR);
        String key;
        String value;
        
        for (String s : temp) {
            String keyAndValue[] = s.split(KEY_VALUE_SEPARATOR);
            value="";
            switch(keyAndValue.length){
                case 2:
                    value=keyAndValue[1];
                case 1:
                    key=keyAndValue[0];
                    queryValues.put(key, value);
                default:
            }
        }
    }

    public String getUri() {
        return uri;
    }

    public String getQuerylessURI() {
        return querylessURI;
    }

    public Map<String, String> getQueryValues() {
        return queryValues;
    }

    @Override
    public String toString() {
        return uri;
    }

    
}
