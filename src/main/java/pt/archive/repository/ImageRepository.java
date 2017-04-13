package pt.archive.repository;
import java.util.List;

import org.springframework.data.solr.repository.Query;
import org.springframework.data.solr.repository.SolrCrudRepository;
import org.springframework.stereotype.Repository;

import pt.archive.model.Image;

/**
 * This repository provides CRUD operations for {@link pt.archive.model.Image}
 * objects.
 * @author jnobre
 *
 */
@Repository
public interface ImageRepository extends SolrCrudRepository< Image , String > {
	
	/**
	 * Find all images entries from solr.
	 * @return The information of all image entries are found from solr.
	 */
	public List< Image > findAll( );
	
	/**
	 * Find ...
	 * @param searchTerm
	 * @return
	 */
	@Query( "imgSrc:*?0* imgTitle:*?0*" )
	public List< Image > findByQueryAnnotation( String searchTerm );
	
	
	/**
	 * Find images contains imgSrc and ImgTitle
	 * @param imgSrc
	 * @param ImgTitle
	 * @return
	 */
	public List< Image > findByImgSrcContainsOrImgTitleContains(String imgSrc, String ImgTitle);
	
}
