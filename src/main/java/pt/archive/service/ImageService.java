package pt.archive.service;

import java.util.List;

import pt.archive.model.Image;

public interface ImageService {
	/**
	 * 
	 * @param searchTerm
	 * @return
	 */
	List< Image > searchTerm( String searchTerm );
	
	/**
	 * 
	 * @param imgSrc
	 * @param imgTitle
	 * @return
	 */
	List< Image > search(String imgSrc , String imgTitle);
	
	/**
	 * 
	 * @param searchTerm
	 * @return
	 */
	List< Image > searchByImgSrc( String searchTerm );
	
    /**
     * Finds all image entries.
     * @return  The information of all todo entries.
     */
    List< Image > findAll( );	
}
