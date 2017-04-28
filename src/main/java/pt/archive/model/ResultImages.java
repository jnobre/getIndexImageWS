package pt.archive.model;

import pt.archive.dto.ImageDTO;
import java.util.List;

public class ResultImages {
	
	private int numResults;
	private List< ImageDTO > images;
	
	public ResultImages( List<ImageDTO> images ) {
		super();
		this.numResults = images.size( );
		this.images = images;
	}
	public int getNumResults( ) {
		return numResults;
	}
	public void setNumResults( int numResults ) {
		this.numResults = numResults;
	}
	public List<ImageDTO> getImages( ) {
		return images;
	}
	public void setImages(List<ImageDTO> images) {
		this.images = images;
	}
	
	@Override
	public String toString() {
		return "ResultImages [numResults=" + numResults + ", images=" + images + "]";
	}
	
	
}
