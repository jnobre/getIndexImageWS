package pt.archive.utils;

import java.io.IOException;

import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.solr.client.solrj.ResponseParser;
import org.apache.solr.client.solrj.SolrRequest;
import org.apache.solr.client.solrj.SolrServerException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HttpSolrClientv2 extends  org.apache.solr.client.solrj.impl.HttpSolrClient {
	
	private static final long serialVersionUID = 880828089651504018L;
	private final Logger log = LoggerFactory.getLogger( this.getClass( ) ); //Define the logger object for this class
	
	public HttpSolrClientv2( String baseURL, HttpClient client, ResponseParser parser) {
		super( baseURL , client , parser );
		log.info( "BASE URL 1=== " + baseURL );
	}

	public HttpSolrClientv2( String baseURL , HttpClient client ) {
        super( baseURL , client );
        log.info( "BASE URL 2=== " + baseURL );
    }


    public HttpSolrClientv2( String baseURL ) {
        super(  baseURL  );
        log.info( "BASE URL 3=== " + baseURL.substring( 0 , baseURL.lastIndexOf( "/" ) ) );
    }
    
    @SuppressWarnings("rawtypes")
    @Override
    protected HttpRequestBase createMethod( SolrRequest request, String collection ) throws IOException, SolrServerException {
        String col = ( collection != null && baseUrl.endsWith( collection ) ) ? null : collection;  
        log.info( "[createMethod] request = " + request.getPath( ) );
        return super.createMethod(request, col);
    }

}
