package pt.archive.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.CountDownLatch;

import org.apache.solr.client.solrj.response.QueryResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import pt.archive.dao.SolrDao;
import pt.archive.dto.ImageDTO;
import pt.archive.model.Image;
import pt.archive.utils.RequestData;

public class ImageSearchWorker implements Callable< List< ImageDTO > > {
	private final Logger log = LoggerFactory.getLogger( this.getClass( ) ); //Define the logger object for this class
	private RequestData requestData;
	private String solrURL;
	private int rowsSolr;
	private CountDownLatch doneSignal;
	
    public ImageSearchWorker(RequestData requestData, String solrURL, int rowsSolr , CountDownLatch doneSignal ) {
		super( );
		this.requestData 	= requestData;
		this.solrURL 		= solrURL;
		this.rowsSolr 		= rowsSolr;
		this.doneSignal 	= doneSignal;
	}
    
	@Override
	public List<ImageDTO> call( ) throws Exception {
		List< Image > images = null;
		try {
			SolrDao< Image > solrDao = new SolrDao< Image > ( solrURL , rowsSolr );
	    	images = readItems( solrDao , requestData );
	    	log.info( "Search solrURL["+solrURL+"] return [" + images.size( ) + "] images" );
		} catch( Exception e ) {
			log.error( "[Im][call]" , e );
		} finally {
			doneSignal.countDown( );
		}
		return  createDTO( images );
	}
	
    
    /**
     * Read items from Solr server
     * @param solrDao
     * @param query
     * @return
     */
	private List< Image > readItems( SolrDao< Image > solrDao , RequestData query ) {
        QueryResponse rsp = solrDao.findbyImgSrcAndImgAltAndTitle( query );
        List< Image > beans = rsp.getBeans( Image.class );
        return beans;
    }
    
	/**
	 * Converter bean tto dto object
	 * @param input
	 * @return
	 */
    private List< ImageDTO > createDTO( List< Image > input ) {
    	if( input == null) return null;
    	List< ImageDTO > result = new ArrayList< >( );
    	for( Image image : input )  //TODO with Java 8 this is not necessary
    		result.add( image._toConvertStudentDTO( ) );
    	return result;
    }
    
    

	
}
