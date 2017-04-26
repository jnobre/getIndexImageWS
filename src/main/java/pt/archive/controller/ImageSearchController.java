package pt.archive.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.solr.repository.config.EnableSolrRepositories;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import pt.archive.model.Image;
import pt.archive.model.ImageDTO;
import pt.archive.repository.ImageRepository;
import pt.archive.service.ImageService;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.annotation.Resource;

import org.springframework.http.HttpStatus;

@RestController
@EnableSolrRepositories( basePackages = { "pt.archive.repository" } )
public class ImageSearchController {
	
	private final Logger log = LoggerFactory.getLogger( this.getClass( ) ); //Define the logger object for this class
	private final ImageService imageService;
    
	@Autowired // no necessary in spring 4.3+
	public ImageSearchController(ImageService imageService) {
	    this.imageService = imageService;
	}
	
	/**
	 * Initialize init 
	 * @throws Exception
	 */
	@PostConstruct
	public void initIt( ) throws Exception {
	  log.info("Init method after properties are set : blacklistUrlFile & blacklistDomainFile");
	  printProperties( );
	}
	
	
	/**
	 * @param query: full-text element
	 * @param startData: 
	 * @param endData
	 * @return 
	 */
    @RequestMapping( value = "/" , method = RequestMethod.GET )
    public List< ImageDTO > getImages( @RequestParam(value="query", defaultValue="") String query,
    									 @RequestParam(value="stamp", defaultValue="19960101000000-20151022163016") String stamtp,
    									 @RequestParam(value="start", defaultValue="0") String _startIndex,
    									 @RequestParam(value="safeImage", defaultValue="all") String _safeImage ) {
	    //List< Image > images = imageService.searchByImgSrc( "http://images.cdn.impresa.pt/tvmais/2015-11-10-mb-socrates-legislativas2015-10.jpg?v=w75h75" ); 
    	List< Image > images = imageService.findAll( );
    	return createDTO( images );
    }

    private List< ImageDTO > createDTO( List< Image > input ) {
    	List< ImageDTO > result = new ArrayList< >( );
    	for( Image image : input )  //TODO with Java 8 this is not necessary
    		result.add( image._toConvertStudentDTO( ) );
    	return result;
    }
    
    private void printProperties( ){
    	log.info( "********* Properties *********" );
    	
    	log.info( "******************************" );
    }
    
    @ExceptionHandler
    @ResponseStatus( HttpStatus.NOT_FOUND )
    public void handleTodoNotFound( ImagesNotFoundException ex ) {
    	log.error( "Handling error with message: {}" , ex.getMessage( ) );
    }

    
}
