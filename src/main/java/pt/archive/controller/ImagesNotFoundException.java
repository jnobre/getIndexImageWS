package pt.archive.controller;

public class ImagesNotFoundException extends RuntimeException{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public ImagesNotFoundException( String id ) {
		super( String.format( "No image entry found with id:" , id ) );
	}

}
