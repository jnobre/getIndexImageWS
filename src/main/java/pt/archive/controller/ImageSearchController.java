package pt.archive.controller;

import org.apache.solr.client.solrj.response.QueryResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.solr.repository.config.EnableSolrRepositories;
import org.springframework.data.solr.server.support.HttpSolrClientFactory;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import pt.archive.dao.SolrDao;
import pt.archive.dto.ImageDTO;
import pt.archive.model.Image;
import pt.archive.model.ResultImages;
import pt.archive.service.ImageService;
import pt.archive.utils.Constants;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.annotation.PostConstruct;
import org.springframework.http.HttpStatus;

@RestController
@Configuration
@EnableSolrRepositories( basePackages = { "pt.archive.repository" } )
public class ImageSearchController {
	
	private final Logger log = LoggerFactory.getLogger( this.getClass( ) ); //Define the logger object for this class
	HttpSolrClientFactory solrClientFactory = null;
	private String[ ] solrCollections;
	private List< String > stopwords;
	private String criteriaRank;
	private List< String > terms;
	private List< String > allterms;
	
	@Value( "${solr.collections}" )
	private String solrCollectionsProp;
	
	@Value( "${solr.server.host}" )
	private String solrURL;
	
	@Value( "${stopWords.file}" )
	private String stopWordsFileLocation;
	
	//private final ImageService imageService;
/*	@Autowired // no necessary in spring 4.3+
	public ImageSearchController(ImageService imageService) {
	    this.imageService = imageService;
	}*/
	
	/**
	 * Initialize init 
	 * @throws Exception
	 */
	@PostConstruct
	public void initIt( ) throws Exception {
	  log.info("Init method after properties are set : blacklistUrlFile & blacklistDomainFile");
	  solrCollections = solrCollectionsProp.split( "," );
	  loadBlackListFiles( );
	  loadStopWords( );
	  printProperties( );
	}
	
	
	/**
	 * @param query: full-text element
	 * @param startData: 
	 * @param endData
	 * @return 
	 */
    @RequestMapping( value = "/" , method = RequestMethod.GET )
    public ResultImages getImages( @RequestParam(value="query", defaultValue="") String query,
    									 @RequestParam(value="stamp", defaultValue="19960101000000-20151022163016") String stamtp,
    									 @RequestParam(value="start", defaultValue="0") String _startIndex,
    									 @RequestParam(value="safeImage", defaultValue="all") String _safeImage ) {
	    //List< Image > images = imageService.searchTerm( "socrates" );
	    //List< Image > images = imageService.findAll( );
    	log.info( "New request query[" + query + "] stamp["+ stamtp +"] start["+ _startIndex +"] safeImage["+ _safeImage +"]" );
    	if( query == null || query.trim( ).equals( "" ) ) 
    		return null;
    	getTerms( query );
    	SolrDao< Image > solrDao = new SolrDao< Image > ( solrURL );
    	List< Image > images = readItems( solrDao );
    	return new ResultImages(  createDTO( images ) );
    }
    
    private List< Image > readItems( SolrDao< Image > solrDao ) {
        QueryResponse rsp = solrDao.readAll( );
        List< Image > beans = rsp.getBeans( Image.class );
        return beans;
    }

    private List< ImageDTO > createDTO( List< Image > input ) {
    	List< ImageDTO > result = new ArrayList< >( );
    	for( Image image : input )  //TODO with Java 8 this is not necessary
    		result.add( image._toConvertStudentDTO( ) );
    	return result;
    }
    
    private void printProperties( ){
    	log.info( "********* Properties *********" );
    	log.info( "Collections: " );
    	for( String collection : solrCollections )
    		log.info( "  " + collection );
    	log.info( "******************************" );
    }
    
    /**
     * get parameter of the query (Advanced search)
     * @param query
     */
    private void getTerms( String query ) {
    	terms = new LinkedList< >( );
    	allterms = new LinkedList< >( );
    	char sort = 45;
    	String sortTerm = "";
    	Matcher m = Pattern.compile( "([^\"]\\S*|\".+?\")\\s*" ).matcher( query );
    	while( m.find( ) ) {
    		if( m.group( 1 ).startsWith( Constants.sortCriteria ) ) {
    			String auxSort = m.group( 1 ).substring( m.group( 1 ).indexOf( Constants.sortCriteria ) + Constants.sortCriteria.length( ) );
    			sortTerm = m.group( 1 );
    			log.info( "  auxSort => " + auxSort + " remove = " + sortTerm );
    			sort = 46;
    			if( auxSort.equals( Constants.criteriaRank.NEW.toString( ) ) )
    				criteriaRank = "new";
    			else if( auxSort.equals( Constants.criteriaRank.OLD.toString( ) ) )
    				criteriaRank = "old";
    			else {
    				criteriaRank = "score";
    				sortTerm = "";
    			}
    		} else if( !m.group( 1 ).startsWith( Constants.typeSearch ) && !m.group( 1 ).startsWith( Constants.sizeSearch ) && !m.group( 1 ).startsWith( Constants.siteSearch ) && !m.group( 1 ).startsWith( Constants.negSearch ) ) {
    			terms.add( m.group( 1 ).replace( "\"" ,  "" ) );
    		}
    		allterms.add( m.group( 1 ).replace( "\"" ,  "" ) );
    	}
    	
    	if( sort == 45 )
    		criteriaRank = "score";
    	
    	log.info( "criteriaRank["+criteriaRank+"]" );
    }
    
    private void loadStopWords( ) {
    	Scanner s = null;
    	try{
    		
    		s = new Scanner( new File( stopWordsFileLocation ) );
    		stopwords = new ArrayList< String >( );
    		while( s.hasNext( ) ) {
    			stopwords.add( s.next( ) );
    		}
    	} catch( IOException e ) {
    		log.error( "Load stopWords file error: " , e );
    	} finally {
    		if( s != null )
    			s.close( );
    	}
    }
    
    /**
     * load blacklist files
     */
    private void loadBlackListFiles( ) {
    	//TODO loadBlackListUrls( );
    	//TODO loadBlackListDomain( );
    }
    
    @ExceptionHandler
    @ResponseStatus( HttpStatus.NOT_FOUND )
    public void handleTodoNotFound( ImagesNotFoundException ex ) {
    	log.error( "Handling error with message: {}" , ex.getMessage( ) );
    }

    
}
