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

import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import org.springframework.http.HttpStatus;

@RestController
@Configuration
@EnableSolrRepositories( basePackages = { "pt.archive.repository" } )
public class ImageSearchController {
	
	private final Logger log = LoggerFactory.getLogger( this.getClass( ) ); //Define the logger object for this class
	HttpSolrClientFactory solrClientFactory = null;
	private String[ ] solrCollections;
	
	@Value( "${solr.collections}" )
	private String solrCollectionsProp;
	
	@Value( "${solr.server.host}" )
	private String solrURL;
	
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
    
    @ExceptionHandler
    @ResponseStatus( HttpStatus.NOT_FOUND )
    public void handleTodoNotFound( ImagesNotFoundException ex ) {
    	log.error( "Handling error with message: {}" , ex.getMessage( ) );
    }

    
}
