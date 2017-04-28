package pt.archive.model;

import java.io.Serializable;
import java.util.List;

import org.apache.solr.client.solrj.beans.Field;
import org.springframework.data.annotation.Id;

import pt.archive.dto.ImageDTO;

public class Image implements Serializable , IImage {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@Field( ID_FIELD )
	private String id;
	@Field( TIMESTAMP_FIELD )
    private List< Long > timestamp;
    @Field( SRCBASE64_FIELD )
    private byte[] srcBase64;
    @Field( IMGSRC_FIELD )
    private List< String > imgSrc;
    @Field( IMGTITLE_FIELD )
    private String imgTitle;
	@Field( ORIGINALURL_FIELD )
    private List< String > originalURL;
	@Field( DIGEST_FIELD )
	private String digest;
	@Field( COLLECTION_FIELD )
	private List< String > collection;
	@Field( IMGWIDTH_FIELD )
	private List< Double > imgWidth;
	@Field( MIMETYPE_FIELD )
	private List< String > mimeType;
	@Field( IMGHEIGHT_FIELD )
	private List< Double > imgHeight;
	@Field( VERSION_FIELD )
	private long version;
	@Field( IMGALT_FIELD )
	private List< String > imgAlt;
	
	public Image( ) { } //Empty constructor is required
	
	
	/*public Image( Builder builder ) {
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
	}*/
	
	public Image( String id, List< Long > timestamp, byte[] srcBase64, 
			List< String > imgSrc, List< String > originalURL, String digest, List< String > collection,
			List< Double > imgWidth, List< String > mimeType, List< Double > imgHeight,  long version) {
		this.id 			= id;
		this.timestamp 		= timestamp;
		this.srcBase64 		= srcBase64;
		this.imgSrc 		= imgSrc;
		this.originalURL 	= originalURL;
		this.digest 		= digest;
		this.collection 	= collection;
		this.imgWidth 		= imgWidth;
		this.mimeType 		= mimeType;
		this.imgHeight 		= imgHeight;
		this.version 		= version;
	}


	public List< Long > getTimestamp() {
		return timestamp;
	}

	public byte[] getSrcBase64() {
		return srcBase64;
	}

	public List< String > getImgSrc() {
		return imgSrc;
	}
	
	public List< String > getOriginalURL() {
		return originalURL;
	}

	public String getDigest() {
		return digest;
	}

	public void setDigest(String digest) {
		this.digest = digest;
	}

	public List< String > getCollection() {
		return collection;
	}

	public List< Double > getImgWidth() {
		return imgWidth;
	}

	public List< String > getMimeType() {
		return mimeType;
	}

	public List< Double > getImgHeight() {
		return imgHeight;
	}

	public String getId() {
		return id;
	}

	public long getVersion() {
		return version;
	}

    public String getImgTitle() {
		return imgTitle;
	}


	public void setImgTitle(String imgTitle) {
		this.imgTitle = imgTitle;
	}
	
	public List<String> getImgAlt() {
		return imgAlt;
	}


	public void setImgAlt(List<String> imgAlt) {
		this.imgAlt = imgAlt;
	}


	public void setId(String id) {
		this.id = id;
	}


	public void setTimestamp(List< Long > timestamp) {
		this.timestamp = timestamp;
	}


	public void setSrcBase64(byte[] srcBase64) {
		this.srcBase64 = srcBase64;
	}


	public void setImgSrc(List<String> imgSrc) {
		this.imgSrc = imgSrc;
	}


	public void setOriginalURL(List<String> originalURL) {
		this.originalURL = originalURL;
	}


	public void setCollection(List<String> collection) {
		this.collection = collection;
	}


	public void setImgWidth(List< Double > imgWidth) {
		this.imgWidth = imgWidth;
	}


	public void setMimeType(List<String> mimeType) {
		this.mimeType = mimeType;
	}


	public void setImgHeight(List< Double > imgHeight) {
		this.imgHeight = imgHeight;
	}


	public void setVersion(long version) {
		this.version = version;
	}


	@Override
	public String toString( ) {
		return "Item [timestamp=" + timestamp + ", srcBase64=" + srcBase64 + ", imgSrc=" + imgSrc + ", originalURL="
				+ originalURL + ", digest=" + digest + ", collection=" + collection + ", imgWidth=" + imgWidth
				+ ", mimeType=" + mimeType + ", imgHeight=" + imgHeight + ", id=" + id + ", version=" + version + "]";
	}
	
	public ImageDTO _toConvertStudentDTO( ){
		ImageDTO dto = new ImageDTO( );
		if( collection != null )
			dto.setCollection( collection.get( 0 ) );
		dto.setDigest( digest );
		dto.setId( id );
		if( imgHeight != null )
			dto.setImgHeight( imgHeight.get( 0 ) );
		if( imgSrc != null )
			dto.setImgSrc( imgSrc.get( 0 ) );
		if( imgWidth != null )
			dto.setImgWidth( imgWidth.get( 0 ) );
		if( mimeType != null )
			dto.setMimeType( mimeType.get( 0 ) );
		if( originalURL != null )
			dto.setOriginalURL( originalURL.get( 0 ) );
		dto.setSrcBase64( srcBase64 );
		if( timestamp != null )
			dto.setTimestamp( timestamp.get( 0 ) );
		dto.setVersion( version );
		return dto;
	}
	
	public static class Builder{
		private String id;
	    private List< String > timestamp;
	    private byte[] srcBase64;
	    private List< String > imgSrc;
	    private List< String > originalURL;
		private String digest;
		private List< String > collection;
		private List< String > imgWidth;
		private List< String > mimeType;
		private List< String > imgHeight;
		private long version;
		private String imgTitle;
		private List< String > imgAlt;
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
		Builder timestamp( List< String > timestamp ) {
			this.timestamp = timestamp;
			return this;
		}
		
		Builder srcBase64( byte[ ] srcBase64 ) {
			this.srcBase64 = srcBase64;
			return this;
		}
		
		Builder imgSrc( List< String > imgSrc ) {
			this.imgSrc = imgSrc;
			return this;
		}
		
		Builder imgTitle( String imgTitle ) {
			this.imgTitle = imgTitle;
			return this;
		}
		
		Builder originalURL( List< String > originalURL ) {
			this.originalURL = originalURL;
			return this;
		}
		
		Builder digest( String digest ) {
			this.digest = digest;
			return this;
		}
		
		Builder collection( List< String > collection ) {
			this.collection = collection;
			return this;
		}
		
		Builder imgWidth( List< String > imgWidth ) {
			this.imgWidth = imgWidth;
			return this;
		}
		
		Builder mimeType( List< String > mimeType ) {
			this.mimeType = mimeType;
			return this;
		}
		
		Builder imgAlt( List< String > imgAlt ) {
			this.imgAlt = imgAlt;
			return this;
		}
		
		Builder imgHeight( List< String > imgHeight ) {
			this.imgHeight = imgHeight;
			return this;
		}
		
		Builder version( long version ) {
			this.version = version;
			return this;
		}
		
		/*public Image build( ) {
			Image build = new Image( this );
			return build;
		}*/
	}
}
