package pt.archive.service;

import java.util.List;

import pt.archive.model.Image;

public interface ImageService {
	
	List< Image > searchTerm( String searchTerm );
	List< Image > search(String imgSrc , String imgTitle);
	List< Image > searchByImgSrc( String searchTerm );
	List< Image > findAll( );	
}
