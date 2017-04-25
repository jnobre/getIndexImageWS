package pt.archive.service;

import java.util.List;
import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pt.archive.model.Image;
import pt.archive.repository.ImageRepository;

@Service
public class ImageServiceImpl implements ImageService {

	
	private final ImageRepository repository;
	
	@Autowired // no necessary in spring 4.3+
	public ImageServiceImpl( ImageRepository repository ) {
	    this.repository = repository;
	}
    
	@Override
	public List< Image > search( String imgSrc , String imgTitle ) {
		return repository.findByImgSrcContainsOrImgTitleContains( imgSrc , imgTitle );
	}

	@Override
	public List< Image > searchTerm( String searchTerm ) {
		return repository.findByQueryAnnotation( searchTerm );
	}
	
	@Override
	public List< Image > searchByImgSrc( String searchTerm ) {
		return repository.findByQueryAnnotation( searchTerm );
	}

	@Override
	public List< Image > findAll( ) {
		return repository.findAll( );
	}
	
}
