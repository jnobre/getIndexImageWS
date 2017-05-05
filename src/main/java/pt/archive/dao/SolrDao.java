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
import pt.archive.utils.RequestData;

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
		StringBuffer buildstr = new StringBuffer( ); 
		buildstr.append( Constants.solrWildcards );
		buildstr.append( Constants.solrOP );
		buildstr.append( Constants.solrWildcards );
		
		query.setQuery( buildstr.toString( ) ); // q=*:*
		QueryResponse rsp = null;
		try {
			rsp = solrClient.query( query );
		} catch (SolrServerException | IOException e) {
			log.error( "[SolrDAO] e" , e );
			return null;
		}
		return rsp;
	}
	
	public QueryResponse findbyImgSrcAndImgAltAndTitle( RequestData request ){
		
		SolrQuery param = new SolrQuery( );
		String pquery = "";
		
		pquery = getQueryImgSrcAndImgAltAndTitle( request );
		//log.info( "Query to Solr: " + pquery );
		param.setQuery( pquery );
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
	
	public String getQueryImgSrcAndImgAltAndTitle( RequestData queryTerms ) {
		StringBuffer buildstr = new StringBuffer( );
		//String[] terms = queryTerms.split( " " );
		int cnt = 0;
		for( String term : queryTerms.getTerms( ) ) {
			
			
			buildstr.append( IMGSRC_FIELD ); //ImgSrc field
			buildstr.append( Constants.solrOP );
			buildstr.append( Constants.solrWildcards );
			buildstr.append( term );
			buildstr.append( Constants.solrWildcards );
			buildstr.append( Constants.solrOpOR );
			buildstr.append( Constants.space ); // space command ' '
			
			buildstr.append( IMGALT_FIELD ); // ImgAlt field
			buildstr.append( Constants.solrOP );
			buildstr.append( Constants.solrWildcards );
			buildstr.append( term );
			buildstr.append( Constants.solrWildcards );
			buildstr.append( Constants.solrOpOR );
			buildstr.append( Constants.space ); // space command ' '
			
			buildstr.append( IMGTITLE_FIELD ); //ImgTitle field
			buildstr.append( Constants.solrOP );
			buildstr.append( Constants.solrWildcards );
			buildstr.append( term );
			buildstr.append( Constants.solrWildcards );
			if( ++cnt < queryTerms.getTerms( ).size( ) )
				buildstr.append( Constants.solrOpOR );
			
			buildstr.append( Constants.space ); // space command ' '
			
		}
		return buildstr.toString( );
	}
	
	
	public void checkAdvancedQuery( RequestData requestData ){
		
		if( requestData.isSize( ) ) {
			//TODO
		}
		
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
	
	
    protected HttpSolrClient getSolrClient( ) {
        return solrClient;
    }

    protected void setSolrClient( HttpSolrClient solrClient ) {
        this.solrClient = solrClient;
    }
	
}
