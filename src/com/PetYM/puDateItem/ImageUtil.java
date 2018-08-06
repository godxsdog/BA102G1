package com.PetYM.puDateItem;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

import javax.imageio.ImageIO;

public class ImageUtil {

	/**
	 * å°‡å?–å½¢ç¸®å?å?Œå?å‚³ï¼Œå?‚æ?œç™¼??ŸéŒ¯èª¤å°±?›´?¥??å‚³??Ÿå?–ï?? ä¾‹å?‚ï?šimageSize??<=1?ç„¡æ³•å?–å?—å?–ç?„å¯¬é«˜ã?ç™¼??ŸException
	 * 
	 * @param srcImageData
	 *            ä¾†æ?å?–å½¢è³‡æ??
	 * @param newSize
	 *            æ¬²å?‡å?–å½¢ç¸®è‡³å¤šå?‘å°ºå¯¸ä»¥ä¸‹ï??50ä»?è¡¨å?‡å°ºå¯¸ç¸®å°è‡³50x50ä»¥å…§
	 * @return ç¸®å?å?Œç•¢??„å?–å½¢è³‡æ??
	 */
	public static byte[] shrink(byte[] srcImageData, int newSize) {
		ByteArrayInputStream bais = new ByteArrayInputStream(srcImageData);
		// ç¸®å?æ?”ä?‹ï??4ä»?è¡¨é™¤ä»?4
		double sampleSize = 1;
		int imageWidth = 0;
		int imageHeight = 0;

		// å¦‚æ?œæ¬²ç¸®å?ç?„å°ºå¯¸é?å?ï?Œå°±?›´?¥å®šç‚º128
		if (newSize <= 1) {
			newSize = 128;
		}

		try {
			BufferedImage srcBufferedImage = ImageIO.read(bais);
			int type = srcBufferedImage.getType();
			String format = "";
			if (type == BufferedImage.TYPE_4BYTE_ABGR || type == BufferedImage.TYPE_4BYTE_ABGR_PRE
					|| type == BufferedImage.TYPE_INT_ARGB || type == BufferedImage.TYPE_INT_ARGB_PRE) {
				format = "png";
			} else {
				format = "jpg";
			}
			imageWidth = srcBufferedImage.getWidth();
			imageHeight = srcBufferedImage.getHeight();
			if (imageWidth == 0 || imageHeight == 0) {
				return srcImageData;
			}
			// ?ªè¦å?–æ?”è?ƒé•·??„ä???Šè?…é?æ?‡å?šé•·åº?(desireSize)ï¼Œå°±è¨ˆç?—æ¬²ç¸®å?ç?„æ?”ä??
			// ä¸¦å?‡å?–æ?”å¯¬é«˜éƒ½?™¤ä»¥æ¬²ç¸®å?æ?”ä??
			int longer = Math.max(imageWidth, imageHeight);
			if (longer > newSize) {
				sampleSize = longer / (long) newSize;
				imageWidth = (int) (srcBufferedImage.getWidth() / sampleSize);
				imageHeight = (int) (srcBufferedImage.getHeight() / sampleSize);
			}

			BufferedImage scaledBufferedImage = new BufferedImage(imageWidth, imageHeight, type);
			Graphics graphics = scaledBufferedImage.createGraphics();
			graphics.drawImage(srcBufferedImage, 0, 0, imageWidth, imageHeight, null);
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			ImageIO.write(scaledBufferedImage, format, baos);
			return baos.toByteArray();
		} catch (IOException e) {
			e.printStackTrace();
			return srcImageData;
		}
	}
}
