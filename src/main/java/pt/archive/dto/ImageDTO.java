package pt.archive.dto;

import java.util.List;



public class ImageDTO {
	private String id;
    private Long timestamp;
    private byte[] srcBase64;
    private String imgSrc;
    private String originalURL;
	private String digest;
	private String collection;
	private Double imgWidth;
	private String mimeType;
	private Double imgHeight;
	private long version;
	private String imgTitle;
	private String imgAlt;
	
	public ImageDTO( ) { }
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public Long getTimestamp() {
		return timestamp;
	}
	public void setTimestamp(Long timestamp) {
		this.timestamp = timestamp;
	}
	public byte[] getSrcBase64() {
		return srcBase64;
	}
	public void setSrcBase64(byte[] srcBase64) {
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
	public String getCollection( ) {
		return collection;
	}
	public void setCollection(String collection) {
		this.collection = collection;
	}
	public Double getImgWidth() {
		return imgWidth;
	}
	public void setImgWidth(Double imgWidth) {
		this.imgWidth = imgWidth;
	}
	public String getMimeType() {
		return mimeType;
	}
	public void setMimeType( String mimeType) {
		this.mimeType = mimeType;
	}
	public Double getImgHeight() {
		return imgHeight;
	}
	public void setImgHeight(Double imgHeight) {
		this.imgHeight = imgHeight;
	}
	public long getVersion() {
		return version;
	}
	public void setVersion(long version) {
		this.version = version;
	}
	public String getImgTitle() {
		return imgTitle;
	}
	public void setImgTitle(String imgTitle) {
		this.imgTitle = imgTitle;
	}
	public String getImgAlt() {
		return imgAlt;
	}

	public void setImgAlt(String imgAlt) {
		this.imgAlt = imgAlt;
	}

	@Override
	public String toString() {
		return "ImageDTO [id=" + id + ", timestamp=" + timestamp + ", srcBase64=" + srcBase64 + ", imgSrc=" + imgSrc
				+ ", imgTitle=" + imgTitle + ", originalURL=" + originalURL + ", digest=" + digest + ", collection=" + collection + ", imgWidth="
				+ imgWidth + ", mimeType=" + mimeType + ", imgHeight=" + imgHeight + ", version=" + version + ", imgAlt=" + imgAlt + "]";
	}
	
	
	
}
