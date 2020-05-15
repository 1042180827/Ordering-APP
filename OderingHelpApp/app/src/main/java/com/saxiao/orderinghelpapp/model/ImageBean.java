package com.saxiao.orderinghelpapp.model;

import android.graphics.Bitmap;

/**
 * 图片类
 */
public class ImageBean {
	private String imagePath;
	private String image;
	private Bitmap bitmap;
	/**
	 * 是否是默认的添加图片
	 */
	private boolean isOriginal;

	public String getImagePath() {
		return imagePath;
	}

	public void setImagePath(String imagePath) {
		this.imagePath = imagePath;
	}

	public Bitmap getBitmap() {
		return bitmap;
	}

	public void setBitmap(Bitmap bitmap) {
		this.bitmap = bitmap;
	}

	public boolean isOriginal() {
		return isOriginal;
	}

	public void setOriginal(boolean original) {
		isOriginal = original;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}
}
