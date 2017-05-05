package pt.archive.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

import pt.archive.dto.ImageDTO;
import pt.archive.model.ResultImages;
import pt.archive.utils.Constants;
import pt.archive.utils.RequestData;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
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
	@Value( "${solr.collections}" )
	private String solrCollectionsProp;
	
	@Value( "${solr.server.host}" )
	private String solrURL;
	
	@Value( "${solr.num.rows}" )
	private int rowsSolr;
	
	@Value( "${solr.num.cores}" )
	private int NcoresSolr;
	
	@Value( "${stopWords.file}" )
	private String stopWordsFileLocation;
	
	@Value( "${TimeoutThreads}" )
	private long timeout;
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
	  log.info("Init method after properties are set : stopwords");
	  solrCollections = solrCollectionsProp.split( "," );
	  loadBlackListFiles( );
	  loadStopWords( );
	  printProperties( );
	}
	
	
	/**
	 * @param query: full-text element
	 * @param stamp: startdate-enddate 
	 * @param start: start index search
	 * @param safeImage: all - all images / yes - only safe image / no - only unsafe image
	 * @return 
	 */
    @RequestMapping( value = "/" , method = RequestMethod.GET )
    public ResultImages getImages( @RequestParam(value="query", defaultValue="") String query,
    									 @RequestParam(value="stamp", defaultValue="19960101000000-20151022163016") String stamtp,
    									 @RequestParam(value="start", defaultValue="0") String _startIndex,
    									 @RequestParam(value="safeImage", defaultValue="all") String _safeImage ) {
	    //List< Image > images = imageService.searchTerm( "socrates" );
	    //List< Image > images = imageService.findAll( );
    	ExecutorService pool = Executors.newFixedThreadPool( NcoresSolr );
    	boolean isAllDone = false;
    	CountDownLatch doneSignal;
    	List< ImageDTO > images = new ArrayList< >( );
    	log.info( "New request query[" + query + "] stamp["+ stamtp +"] start["+ _startIndex +"] safeImage["+ _safeImage +"]" );
    	
    	if( query == null || query.trim( ).equals( "" ) ) 
    		return null;
    	
    	long start = System.currentTimeMillis( );
    	
    	RequestData requestData = getTerms( query ); //extract terms of query 
    	RequestData resultQuery = prepareTerms( query , requestData ); 
    	
    	/*SolrDao< Image > solrDao = new SolrDao< Image > ( solrURL , rowsSolr );
    	List< Image > images = readItems( solrDao , resultQuery );*/
    	doneSignal = new CountDownLatch( NcoresSolr );
    	List< Future< List< ImageDTO > > > submittedJobs = new ArrayList< >( );
    	for( int i = 0 ; i < NcoresSolr ; i++ ) {
    		Future< List< ImageDTO > > job = null;
    		//log.info( "Submit new job collection["+solrCollections[i]+"]" );
    		job = pool.submit( new ImageSearchWorker(resultQuery, buildHostSolr( solrURL , solrCollections[ i ] ) , rowsSolr , doneSignal ) );
    		submittedJobs.add( job );
    	}
    	
    	try {
 			isAllDone = doneSignal.await( timeout , TimeUnit.SECONDS );
 		    if ( !isAllDone ) {
 		    	log.info( "Clean threads!!!!" );
 		    	cleanUpThreads( submittedJobs );
 		    }
            	
        } catch ( InterruptedException e1 ) {
        	cleanUpThreads( submittedJobs ); // take care, or cleanup
        }
    	
    	
    	//get images result to search
 		for( Future< List< ImageDTO > >  job : submittedJobs ) {
 			try {
 				List< ImageDTO > result = job.get( ); // wait for a processor to complete
                if ( !isAllDone && !job.isDone( ) ) {  // before doing a get you may check if it is done
                    job.cancel( true ); // cancel job and continue with others
                    continue;
                }
 			
	 			if( result != null && !result.isEmpty( ) ) {
	 				log.debug( "Resultados do future = " + result.size( ) );
	 				images.addAll( result );
	 			}
            } catch ( ExecutionException cause ) {
            	log.error( "[ImageSearchResultsController][getImageResults]", cause ); // exceptions occurred during execution, in any
            } catch ( InterruptedException e ) {
            	log.error( "[ImageSearchResultsController][getImageResults]", e ); // take care
            }
 		}
 
 		long elapsedTime = System.currentTimeMillis( ) - start;
    	log.info( "Search ["+query+"] Results = [" + images.size( ) +"] time = [" + elapsedTime + "] milliseconds.");
    	printTerms( requestData );
    	return new ResultImages(  images );
    }
    
    private String buildHostSolr( String solrURL , String collection ) {
    	return solrURL.concat( Constants.urlBarOP ).concat( collection );
    }
    
   /* private List< Image > readItems( SolrDao< Image > solrDao , RequestData query ) {
        QueryResponse rsp = solrDao.findbyImgSrcAndImgAltAndTitle( query );
        List< Image > beans = rsp.getBeans( Image.class );
        return beans;
    }

    private List< ImageDTO > createDTO( List< Image > input ) {
    	List< ImageDTO > result = new ArrayList< >( );
    	for( Image image : input )  //TODO with Java 8 this is not necessary
    		result.add( image._toConvertStudentDTO( ) );
    	return result;
    }*/
    
    /**
     * Print application properties
     */
    private void printProperties( ){
    	log.info( "********* Properties *********" );
    	log.info( "Collections: " );
    	for( String collection : solrCollections )
    		log.info( "  " + collection );
    	log.info( "******************************" );
    	log.info( "******* StopWords Urls *******" );
    	for( String word : stopwords ) 
    		log.info( "  " + word );
    	log.info("***************************");
    }
    
    /**
     * Prepare terms of the ranking
     * @param query
     * @return
     */
    private RequestData prepareTerms( String query , RequestData requestData ) {
    	removeStopWords( requestData );
    	return removeCharactersAdvancedSearch( query , requestData );
    }
    
    /**
     * Remove stop words of the terms
     */
    private void removeStopWords( RequestData requestData ) {
    	for( Iterator< String > iterator = requestData.getTerms( ).iterator( ) ; iterator.hasNext( ); ) {
    		String term = iterator.next( );
    		for( String stopWord : stopwords ) {
    			if( term.toLowerCase( ).equals( stopWord ) ) {
    				log.info( "[StopWords] Remove term["+term+"] to ranking" );
    				iterator.remove( );
    			}
    		}
    	}
    }
    
    /**
     * Remove terms in the advanced search 
     * @param query
     * @return
     */
    private RequestData removeCharactersAdvancedSearch( String query , RequestData requestData) {
    	
    	for( Iterator< String > iterator = requestData.getTerms( ).iterator( ) ; iterator.hasNext( ); ) {
    		String term = iterator.next( );
    		if( term.startsWith( Constants.sizeSearch ) &&
        			term.startsWith( Constants.sortCriteria )) {
    			log.info( "[StopWords] Remove term["+term+"] to ranking" );
    			iterator.remove( );
    		}
    		
    	}
    	return requestData;
    }
    
    /**
     * get parameter of the query (Advanced search)
     * @param query
     */
    private RequestData getTerms( String query ) {
    	String criteriaRank = "";
    	RequestData requestData = new RequestData( );
    	char sort = 45;
    	Matcher m = Pattern.compile( "([^\"]\\S*|\".+?\")\\s*" ).matcher( query );
    	while( m.find( ) ) {
    		if( m.group( 1 ).startsWith( Constants.sortCriteria ) ) {
    			String auxSort = m.group( 1 ).substring( m.group( 1 ).indexOf( Constants.sortCriteria ) + Constants.sortCriteria.length( ) );
    			log.info( "  auxSort => " + auxSort );
    			sort = 46;
    			if( auxSort.equals( Constants.criteriaRank.NEW.toString( ) ) )
    				requestData.setCriteriaRank( "new" );
    			else if( auxSort.equals( Constants.criteriaRank.OLD.toString( ) ) )
    				requestData.setCriteriaRank( "old" );
    			else {
    				requestData.setCriteriaRank( "score" );
    			}
    		} else if( !m.group( 1 ).startsWith( Constants.typeSearch ) && !m.group( 1 ).startsWith( Constants.sizeSearch ) && !m.group( 1 ).startsWith( Constants.siteSearch ) && !m.group( 1 ).startsWith( Constants.negSearch ) ) {
    			requestData.getTerms( ).add( m.group( 1 ).replace( "\"" ,  "" ) );
    		}
    		requestData.getAllterms( ).add( m.group( 1 ).replace( "\"" ,  "" ) );
    		if( m.group( 1 ).startsWith( Constants.typeSearch ) ){
    			requestData.setType( true );
    		} else if( m.group( 1 ).startsWith( Constants.siteSearch ) ) {
    			requestData.setSite( true );
    		} else if( m.group( 1 ).startsWith( Constants.sizeSearch ) ) {
    			requestData.setSize( true );
    		} else if( m.group( 1 ).startsWith( Constants.sortCriteria ) ) {
    			requestData.setSort( true );
    		} else if( m.group( 1 ).startsWith( Constants.negSearch ) ) {
    			requestData.setNeg( true );
    		}
    	}
    	
    	if( sort == 45 )
    		criteriaRank = "score";
    	
    	log.info( "criteriaRank["+criteriaRank+"]" );
    	return requestData;
    }
    
    private void loadStopWords( ) {
    	Scanner s = null;
    	try{
    		s = new Scanner(new BufferedReader(new FileReader( stopWordsFileLocation )));
    		stopwords = new ArrayList< String >( );
    		if( !s.hasNext( ) ) 
    			log.error( "Stopwords file is empty! check file onwer/permissions ["+stopWordsFileLocation+"]" );
    		while( s.hasNext( ) ) {
    			stopwords.add( s.next( ) );
    		}
    	} catch( IOException e ) {
    		log.error( ""
    				+ " " , e );
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
    
    /**
     * Clean memory threads
     * @param submittedJobs
     */
    private void cleanUpThreads( List< Future< List< ImageDTO > > > submittedJobs ) {
    	for ( Future< List< ImageDTO > > job : submittedJobs ) {
    		if( job != null )
    			job.cancel( true );
    	}
    }
    
    private void printTerms( RequestData requestData ) {
    	log.info( "*** Terms ****" );
    	for( String term : requestData.getTerms( ) )
    		log.info( "term = " + term );
    	log.info( "**************" );
    	log.info( "*** AllTerms ****" );
    	for( String term : requestData.getAllterms( ) )
    		log.info( "Allterm = " + term );
    	log.info( "**************" );
    }
    
    @ExceptionHandler
    @ResponseStatus( HttpStatus.NOT_FOUND )
    public void handleTodoNotFound( ImagesNotFoundException ex ) {
    	log.error( "Handling error with message: {}" , ex.getMessage( ) );
    }

    
}
