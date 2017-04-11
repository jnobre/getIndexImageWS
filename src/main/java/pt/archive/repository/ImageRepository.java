package pt.archive.repository;
import java.util.List;

import org.springframework.data.solr.repository.Query;
import org.springframework.data.solr.repository.SolrCrudRepository;
import pt.archive.model.Image;

public interface ImageRepository extends SolrCrudRepository< Image , String > {
	
	@Query( "imgSrc:*?0* imgTitle:*?0*" )
	public List< Image > findByQueryAnnotation( String searchTerm );
	
	public List< Image > findByImgSrcContainsOrImgTitleContains(String imgSrc, String ImgTitle);
	
}
