package pt.archive.utils;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.CloudSolrServer;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.apache.solr.client.solrj.impl.HttpSolrServer;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SolrClient {
	
	/**
	 * 
	 */
	private final Logger log = LoggerFactory.getLogger( this.getClass( ) ); //Define the logger object for this class
	private static final long serialVersionUID = 1L;
	private final Map< String, SolrClient > solrClients = new HashMap< >( );
	private  String username;
	private  String password;
	private  String[] termsQuery; 
	
	
	public SolrClient( ) { }  // Empty constructor 
	
	public SolrClient( String username, String password , String[] terms ) {
		
		this.username 	= username;
	    this.password 	= password;
	    this.termsQuery = terms;
	}
	
	public String execute(  ) {
		log.info(  "Starting off " + this.getClass( ).toString( ) );
		//SolrDao<Item> solrDao = new SolrDao<Item> ( Constants.solrURl );
		HttpSolrClient solr = new HttpSolrClient( Constants.solrURl );
		QueryResponse  response = null;

		try {
			response = solr.query(new SolrQuery( "*:*" ) );
			
		} catch (SolrServerException | IOException e) {
			log.error( "Error [SolrClient][execute] " , e );
		}
		return response.toString( );
	    //log.info( "" +  );
		
	}
	 


}
