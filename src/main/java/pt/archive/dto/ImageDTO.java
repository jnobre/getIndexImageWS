package pt.archive.dto;

public class ImageDTO {
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
	
	public ImageDTO( ) { }
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
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
	public String getVersion() {
		return version;
	}
	public void setVersion(String version) {
		this.version = version;
	}
	public String getImgTitle() {
		return imgTitle;
	}
	public void setImgTitle(String imgTitle) {
		this.imgTitle = imgTitle;
	}

	
	@Override
	public String toString() {
		return "ImageDTO [id=" + id + ", timestamp=" + timestamp + ", srcBase64=" + srcBase64 + ", imgSrc=" + imgSrc
				+ ", imgTitle=" + imgTitle + ", originalURL=" + originalURL + ", digest=" + digest + ", collection=" + collection + ", imgWidth="
				+ imgWidth + ", mimeType=" + mimeType + ", imgHeight=" + imgHeight + ", version=" + version + "]";
	}
	
	
	
}
