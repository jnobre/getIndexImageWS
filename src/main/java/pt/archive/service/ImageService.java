package pt.archive.service;

import java.util.List;
import pt.archive.model.Image;

public interface ImageService {
	
	public List< Image > searchTerm( String searchTerm );
	
	public List< Image > search(String imgSrc , String imgTitle);
	
	public List< Image > searchByImgSrc( String searchTerm );
	
}
