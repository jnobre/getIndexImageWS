package pt.archive.dao;

import java.io.IOException;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import pt.archive.utils.Constants;

public class SolrDAO < T > {
	
	private final Logger log = LoggerFactory.getLogger( this.getClass( ) ); //Define the logger object for this class
	HttpSolrClient solrClient = null;
	
	
	public SolrDAO( String solrURL ) {
		solrClient = new HttpSolrClient( solrURL );
		solrClient.setDefaultMaxConnectionsPerHost( 10 );
		solrClient.setConnectionTimeout( Constants.timeoutConn );
		solrClient.setFollowRedirects( true );
		solrClient.setUseMultiPartPost( true );
	}
	
	public QueryResponse readll( ){
		SolrQuery query = new SolrQuery( );
		query.setQuery( "imgSrc:*socrates*" );
		QueryResponse rsp = null;
			try {
				rsp = solrClient.query( query );
			} catch (SolrServerException | IOException e) {
				log.error( "[SolrDAO] e" , e );
				return null;
			}
		return rsp;
	}
	
	
	private void configureSolr( ){
	}
	
    protected HttpSolrClient getSolrClient( ) {
        return solrClient;
    }

    protected void setSolrClient( HttpSolrClient solrClient ) {
        this.solrClient = solrClient;
    }
	
}
