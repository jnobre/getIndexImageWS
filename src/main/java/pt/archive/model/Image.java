package pt.archive.model;

import org.apache.solr.client.solrj.beans.Field;
import org.springframework.data.annotation.Id;

public class Image {
	
	@Id
	@Field
	private String id;
	@Field("timestamp")
    private String timestamp;
    @Field("srcBase64")
    private String srcBase64;
    @Field("imgSrc")
    private String imgSrc;
    @Field("originalURL")
    private String originalURL;
	@Field( "digest" )
	private String digest;
	@Field( "collection" )
	private String collection;
	@Field( "imgWidth" )
	private String imgWidth;
	@Field( "mimeType" )
	private String mimeType;
	@Field( "imgHeight" )
	private String imgHeight;
	@Field( "_version_" )
	private String version;
	
	public Image( ) { } //Empty constructor is required
	
	/*
	public Image(String timestamp, String srcBase64, String imgSrc, String originalURL, String digest, String collection,
			String imgWidth, String mimeType, String imgHeight, String id, String version) {
		super( );
		this.timestamp 		= timestamp;
		this.srcBase64 		= srcBase64;
		this.imgSrc 		= imgSrc;
		this.originalURL 	= originalURL;
		this.digest 		= digest;
		this.collection 	= collection;
		this.imgWidth 		= imgWidth;
		this.mimeType 		= mimeType;
		this.imgHeight 		= imgHeight;
		this.id 			= id;
		this.version 		= version;
	}
*/

	public String getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
	}

	public String getSrcBase64() {
		return srcBase64;
	}

	public void setSrcBase64(String srcBase64) {
		this.srcBase64 = srcBase64;
	}

	public String getImgSrc() {
		return imgSrc;
	}

	public void setImgSrc(String imgSrc) {
		this.imgSrc = imgSrc;
	}

	public String getOriginalURL() {
		return originalURL;
	}

	public void setOriginalURL(String originalURL) {
		this.originalURL = originalURL;
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

	public void setCollection(String collection) {
		this.collection = collection;
	}

	public String getImgWidth() {
		return imgWidth;
	}

	public void setImgWidth(String imgWidth) {
		this.imgWidth = imgWidth;
	}

	public String getMimeType() {
		return mimeType;
	}

	public void setMimeType(String mimeType) {
		this.mimeType = mimeType;
	}

	public String getImgHeight() {
		return imgHeight;
	}

	public void setImgHeight(String imgHeight) {
		this.imgHeight = imgHeight;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	@Override
	public String toString() {
		return "Item [timestamp=" + timestamp + ", srcBase64=" + srcBase64 + ", imgSrc=" + imgSrc + ", originalURL="
				+ originalURL + ", digest=" + digest + ", collection=" + collection + ", imgWidth=" + imgWidth
				+ ", mimeType=" + mimeType + ", imgHeight=" + imgHeight + ", id=" + id + ", version=" + version + "]";
	}
	
	public static Builder getBuilder(Long id, String timestamp, String srcBase64, String imgSrc, String originalURL, String digest, String collection,
			String imgWidth, String mimeType, String imgHeight, String version) {
		return new Builder( id, timestamp, srcBase64, imgSrc, originalURL, digest, collection,imgWidth, mimeType, imgHeight, version );
	}
	
	public static class Builder{
		private Image build;
		
		public Builder( Long id, String timestamp, String srcBase64, String imgSrc, String originalURL, String digest, String collection,
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
		}
		
		
		
	}
}
