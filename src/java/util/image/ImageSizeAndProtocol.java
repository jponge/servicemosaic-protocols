package util.image;

import fr.isima.ponge.wsprotocol.BusinessProtocol;

public class ImageSizeAndProtocol {
	private BusinessProtocol protocol;
	private int width;
	private int height;
	
	public ImageSizeAndProtocol(int width, int height, BusinessProtocol prot) {
		this.width = width;
		this.height = height;
		this.protocol = prot;
	}
	
	public BusinessProtocol getProtocol() {
		return protocol;
	}
	public void setProtocol(BusinessProtocol protocol) {
		this.protocol = protocol;
	}
	public int getWidth() {
		return width;
	}
	public void setWidth(int width) {
		this.width = width;
	}
	public int getHeight() {
		return height;
	}
	public void setHeight(int height) {
		this.height = height;
	}
	
	
}
