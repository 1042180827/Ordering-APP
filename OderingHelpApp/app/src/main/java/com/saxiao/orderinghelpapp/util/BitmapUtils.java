package com.saxiao.orderinghelpapp.util;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import java.io.ByteArrayOutputStream;

public class BitmapUtils {

	public static byte[] bitmap2Bytes(Bitmap bitmap) {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
		return baos.toByteArray();
	}

	public static Bitmap byte2Bitmap(byte[] data) {
		return BitmapFactory.decodeByteArray(data, 0, data.length);
	}
}
