package pt.archive.dao;

import java.io.IOException;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import pt.archive.model.IImage;
import pt.archive.utils.Constants;

public class SolrDao < T > implements IImage {
	
	private final Logger log = LoggerFactory.getLogger( this.getClass( ) ); //Define the logger object for this class
	HttpSolrClient solrClient = null;
	private int rows;
	
	public SolrDao( String solrURL , int rows ) {
		solrClient = new HttpSolrClient( solrURL );
		solrClient.setDefaultMaxConnectionsPerHost( 10 );
		solrClient.setConnectionTimeout( Constants.timeoutConn );
		solrClient.setFollowRedirects( true );
		solrClient.setUseMultiPartPost( true );
		this.rows = rows;
	}
	
	public QueryResponse findAll( ){
		SolrQuery query = new SolrQuery( );
		query.setQuery( Constants.qReadAll ); // q=*:*
		QueryResponse rsp = null;
		try {
			rsp = solrClient.query( query );
		} catch (SolrServerException | IOException e) {
			log.error( "[SolrDAO] e" , e );
			return null;
		}
		return rsp;
	}
	
	public QueryResponse findbyImgSrcAndImgAltAndTitle( String queryStr ){
		SolrQuery param = new SolrQuery( );
		/*StringBuffer squery = new StringBuffer( );
		squery.append( IMGSRC_FIELD );
		squery.append( ":" );
		squery.append( "*" );*/
		
		param.setRows( rows );
		QueryResponse rsp = null;
		try {
			rsp = solrClient.query( param );
		} catch (SolrServerException | IOException e) {
			log.error( "[SolrDAO] e" , e );
			return null;
		}
		return rsp;
	}
	
	public QueryResponse findByImgAltNotNull( ){
		SolrQuery param = new SolrQuery( );
		StringBuffer query = new StringBuffer( );
		query.append( IMGALT_FIELD );
		query.append( ":" );
		query.append( Constants.qNotNull );
		log.info( "Query to solr: " + query.toString( ) );
		param.setQuery( query.toString() ); 
		QueryResponse rsp = null;
		try {
			rsp = solrClient.query( param );
		} catch (SolrServerException | IOException e) {
			log.error( "[SolrDAO] e" , e );
			return null;
		}
		return rsp;
	}
	
	public QueryResponse findByImgSrcNotNull( ){
		SolrQuery param = new SolrQuery( );
		StringBuffer query = new StringBuffer( );
		query.append( IMGSRC_FIELD );
		query.append( ":" );
		query.append( Constants.qNotNull );
		log.info( "Query to solr: " + query.toString( ) );
		param.setQuery( query.toString() ); 
		QueryResponse rsp = null;
		try {
			rsp = solrClient.query( param );
		} catch (SolrServerException | IOException e) {
			log.error( "[SolrDAO] e" , e );
			return null;
		}
		return rsp;
	}
	
	public QueryResponse findByImgTitleNotNull( ){
		SolrQuery param = new SolrQuery( );
		StringBuffer query = new StringBuffer( );
		query.append( IMGTITLE_FIELD );
		query.append( ":" );
		query.append( Constants.qNotNull );
		log.info( "Query to solr: " + query.toString( ) );
		param.setQuery( query.toString() ); 
		QueryResponse rsp = null;
		try {
			rsp = solrClient.query( param );
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
