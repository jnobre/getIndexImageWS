package pt.archive.model;

import java.io.Serializable;

import org.apache.solr.client.solrj.beans.Field;
import org.springframework.data.annotation.Id;
import org.springframework.data.solr.core.mapping.SolrDocument;

import pt.archive.dto.ImageDTO;

@SolrDocument(solrCoreName="IA")
public class Image implements Serializable , IImage {
	
	@Id
	@Field( ID_FIELD )
	private String id;
	@Field( TIMESTAMP_FIELD )
    private String timestamp;
    @Field( SRCBASE64_FIELD )
    private String srcBase64;
    @Field( IMGSRC_FIELD )
    private String imgSrc;
    @Field( IMGTITLE_FIELD )
    private String imgTitle;
	@Field( ORIGINALURL_FIELD )
    private String originalURL;
	@Field( DIGEST_FIELD )
	private String digest;
	@Field( COLLECTION_FIELD )
	private String collection;
	@Field( IMGWIDTH_FIELD )
	private String imgWidth;
	@Field( MIMETYPE_FIELD )
	private String mimeType;
	@Field( IMGHEIGHT_FIELD )
	private String imgHeight;
	@Field( VERSION_FIELD )
	private String version;
	
	public Image( ) { } //Empty constructor is required
	
	
	public Image( Builder builder ) {
		this.timestamp 		= builder.timestamp;
		this.srcBase64 		= builder.srcBase64;
		this.imgSrc 		= builder.imgSrc;
		this.originalURL 	= builder.originalURL;
		this.digest 		= builder.digest;
		this.collection 	= builder.collection;
		this.imgWidth 		= builder.imgWidth;
		this.mimeType 		= builder.mimeType;
		this.imgHeight 		= builder.imgHeight;
		this.id 			= builder.id;
		this.version 		= builder.version;
	}


	public String getTimestamp() {
		return timestamp;
	}

	public String getSrcBase64() {
		return srcBase64;
	}

	public String getImgSrc() {
		return imgSrc;
	}

	public String getOriginalURL() {
		return originalURL;
	}

	public String getDigest() {
		return digest;
	}

	public void setDigest(String digest) {
		this.digest = digest;
	}

	public String getCollection() {
		return collection;
	}

	public String getImgWidth() {
		return imgWidth;
	}

	public String getMimeType() {
		return mimeType;
	}

	public String getImgHeight() {
		return imgHeight;
	}

	public String getId() {
		return id;
	}

	public String getVersion() {
		return version;
	}

    public String getImgTitle() {
		return imgTitle;
	}


	public void setImgTitle(String imgTitle) {
		this.imgTitle = imgTitle;
	}

	@Override
	public String toString( ) {
		return "Item [timestamp=" + timestamp + ", srcBase64=" + srcBase64 + ", imgSrc=" + imgSrc + ", originalURL="
				+ originalURL + ", digest=" + digest + ", collection=" + collection + ", imgWidth=" + imgWidth
				+ ", mimeType=" + mimeType + ", imgHeight=" + imgHeight + ", id=" + id + ", version=" + version + "]";
	}
	
	public ImageDTO _toConvertStudentDTO( ){
		ImageDTO dto = new ImageDTO( );
		dto.setCollection( collection );
		dto.setDigest( digest );
		dto.setId( id );
		dto.setImgHeight( imgHeight );
		dto.setImgSrc( imgSrc );
		dto.setImgWidth( imgWidth );
		dto.setMimeType( mimeType );
		dto.setOriginalURL( originalURL );
		dto.setSrcBase64( srcBase64 );
		dto.setTimestamp( timestamp );
		dto.setVersion( version );
		return dto;
	}
	
	public static class Builder{
		private String id;
	    private String timestamp;
	    private String srcBase64;
	    private String imgSrc;
	    private String originalURL;
		private String digest;
		private String collection;
		private String imgWidth;
		private String mimeType;
		private String imgHeight;
		private String version;
		private String imgTitle;
	/*	public Builder( Long id, String timestamp, String srcBase64, String imgSrc, String originalURL, String digest, String collection,
				String imgWidth, String mimeType, String imgHeight,  String version) {
			build = new Image( );
			build.id 			= id.toString( );
			build.timestamp 	= timestamp;
			build.srcBase64 	= srcBase64;
			build.imgSrc 		= imgSrc;
			build.originalURL 	= originalURL;
			build.digest 		= digest;
			build.collection 	= collection;
			build.imgWidth 		= imgWidth;
			build.mimeType 		= mimeType;
			build.imgHeight 	= imgHeight;
			build.version 		= version;
		}*/
		private Builder() {}
		Builder timestamp( String timestamp ) {
			this.timestamp = timestamp;
			return this;
		}
		
		Builder srcBase64( String srcBase64 ) {
			this.srcBase64 = srcBase64;
			return this;
		}
		
		Builder imgSrc( String imgSrc ) {
			this.imgSrc = imgSrc;
			return this;
		}
		
		Builder imgTitle( String imgTitle ) {
			this.imgTitle = imgTitle;
			return this;
		}
		
		Builder originalURL( String originalURL ) {
			this.originalURL = originalURL;
			return this;
		}
		
		Builder digest( String digest ) {
			this.digest = digest;
			return this;
		}
		
		Builder collection( String collection ) {
			this.collection = collection;
			return this;
		}
		
		Builder imgWidth( String imgWidth ) {
			this.imgWidth = imgWidth;
			return this;
		}
		
		Builder mimeType( String mimeType ) {
			this.mimeType = mimeType;
			return this;
		}
		
		Builder imgHeight( String imgHeight ) {
			this.imgHeight = imgHeight;
			return this;
		}
		
		Builder version( String version ) {
			this.version = version;
			return this;
		}
		
		public Image build( ) {
			Image build = new Image( this );
			return build;
		}
	}
}
