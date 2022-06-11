package com.googlecode.jmoviedb;

import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.DirectColorModel;
import java.awt.image.IndexColorModel;
import java.awt.image.WritableRaster;

import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.ImageData;
import org.eclipse.swt.graphics.PaletteData;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.swt.widgets.Display;

public class Utils {
	public static ImageDescriptor resizePreserveAspect(ImageDescriptor imageDesc, int maxWidth, int maxHeight) {
		if (isLinux()) return imageDesc; // For Linux, SWT has built-in scaling that works

		ImageData imageData = imageDesc.getImageData(100);
		
		if (imageData.width==maxWidth || imageData.height==maxHeight)
			return imageDesc;
		
		return ImageDescriptor.createFromImage(resizePreserveAspect(imageData, maxWidth, maxHeight));
	}
	
	public static Image resizePreserveAspect(ImageData imageData, int maxWidth, int maxHeight) {
		Image image = new Image(Display.getCurrent(), imageData);
		return resizePreserveAspect(image, maxWidth, maxHeight);
	}
	
	public static Image resizePreserveAspect(Image image, int maxWidth, int maxHeight) {
		if (isLinux()) return image; // For Linux, SWT has built-in scaling that works
		ImageData imageData = image.getImageData();
		
		if (imageData.width==maxWidth || imageData.height==maxHeight)
			return image;
		
		float widthfactor = (float)maxWidth/(float)imageData.width;
		float heightFactor = (float)maxHeight/(float)imageData.height;
		
		float scale = Math.min(widthfactor, heightFactor);
		int newWidth = Math.round(scale * imageData.width);
	    int newHeight = Math.round(scale * imageData.height);
	    
	    return resize(image, newWidth, newHeight);
	}
	
	private static Image resize(Image image, int newWidth, int newHeight) {
		Image scaled = new Image(Display.getCurrent(), newWidth, newHeight);
		GC gc = new GC(scaled);
		try
		{
			// This method of scaling yields high quality, but does not preserve transparency.
		    gc.setAntialias(SWT.ON);
		    gc.setInterpolation(SWT.HIGH);
		    gc.drawImage(image, 0, 0, image.getBounds().width, image.getBounds().height, 0, 0, newWidth, newHeight);
		    ImageData interpolatedScaledImageData = scaled.getImageData();
		    
		    switch (image.getImageData().getTransparencyType()) {
		    case SWT.TRANSPARENCY_PIXEL:
		    	interpolatedScaledImageData.transparentPixel = image.getImageData().transparentPixel;
		    	scaled.dispose();
		    	return new Image(Display.getCurrent(), interpolatedScaledImageData);
		    	
		    case SWT.TRANSPARENCY_ALPHA:
		    	// The simple linear scaling of ImageData.scaledTo handles alpha; but yields poor image quality.
		    	// We copy only the scaled alpha and add it to our good interpolated scaled image.
		    	ImageData simpleScaledImgData = image.getImageData().scaledTo(newWidth, newHeight);
		    	byte[] alpha = simpleScaledImgData.alphaData;
		    	interpolatedScaledImageData.alphaData = alpha;
		    	scaled.dispose();
		    	return new Image(Display.getCurrent(), interpolatedScaledImageData);
		    	
		    case SWT.TRANSPARENCY_MASK:
		    	System.out.println("Transparency mask not implemented");
		    case SWT.TRANSPARENCY_NONE:
	    	default:
	    		return scaled;	    		
		    }
		}
		finally {
			gc.dispose();
			image.dispose();
		}
	}
	
	/**
	 * Resizes an image, using the given scaling factor. Constructs a new image resource, please take care of resource
	 * disposal if you no longer need the original one. This method is optimized for quality, not for speed.
	 * 
	 * https://stackoverflow.com/questions/4752748/swt-how-to-do-high-quality-image-resize
	 * 
	 * @param image source image
	 * @param scale scale factor (<1 = downscaling, >1 = upscaling)
	 * @return scaled image
	 */
	public static ImageData resize (ImageData image, float scale) {
	    int w = image.width;
	    int h = image.height;

	    // convert to buffered image
	    BufferedImage img = convertToAWT(image);

	    // resize buffered image
	    int newWidth = Math.round(scale * w);
	    int newHeight = Math.round(scale * h);

	    // determine scaling mode for best result: if downsizing, use area averaging, if upsizing, use smooth scaling
	    // (usually bilinear).
	    int mode = scale < 1 ? BufferedImage.SCALE_AREA_AVERAGING : BufferedImage.SCALE_SMOOTH;
	    java.awt.Image scaledImage = img.getScaledInstance(newWidth, newHeight, mode);

	    // convert the scaled image back to a buffered image
	    img = new BufferedImage(newWidth, newHeight, BufferedImage.TYPE_INT_RGB);
	    img.getGraphics().drawImage(scaledImage, 0, 0, null);

	    // reconstruct swt image
	    ImageData imageData = convertToSWT(img);
	    return imageData;
	}

	public static BufferedImage convertToAWT (ImageData data) {
	    ColorModel colorModel = null;
	    PaletteData palette = data.palette;
	    if (palette.isDirect) {
	        colorModel = new DirectColorModel(data.depth, palette.redMask, palette.greenMask, palette.blueMask);
	        BufferedImage bufferedImage = new BufferedImage(colorModel, colorModel.createCompatibleWritableRaster(data.width, data.height),
	            false, null);
	        WritableRaster raster = bufferedImage.getRaster();
	        int[] pixelArray = new int[3];
	        for (int y = 0; y < data.height; y++) {
	            for (int x = 0; x < data.width; x++) {
	                int pixel = data.getPixel(x, y);
	                RGB rgb = palette.getRGB(pixel);
	                pixelArray[0] = rgb.red;
	                pixelArray[1] = rgb.green;
	                pixelArray[2] = rgb.blue;
	                raster.setPixels(x, y, 1, 1, pixelArray);
	            }
	        }
	        return bufferedImage;
	    } else {
	        RGB[] rgbs = palette.getRGBs();
	        byte[] red = new byte[rgbs.length];
	        byte[] green = new byte[rgbs.length];
	        byte[] blue = new byte[rgbs.length];
	        for (int i = 0; i < rgbs.length; i++) {
	            RGB rgb = rgbs[i];
	            red[i] = (byte) rgb.red;
	            green[i] = (byte) rgb.green;
	            blue[i] = (byte) rgb.blue;
	        }
	        if (data.transparentPixel != -1) {
	            colorModel = new IndexColorModel(data.depth, rgbs.length, red, green, blue, data.transparentPixel);
	        } else {
	            colorModel = new IndexColorModel(data.depth, rgbs.length, red, green, blue);
	        }
	        BufferedImage bufferedImage = new BufferedImage(colorModel, colorModel.createCompatibleWritableRaster(data.width, data.height),
	            false, null);
	        WritableRaster raster = bufferedImage.getRaster();
	        int[] pixelArray = new int[1];
	        for (int y = 0; y < data.height; y++) {
	            for (int x = 0; x < data.width; x++) {
	                int pixel = data.getPixel(x, y);
	                pixelArray[0] = pixel;
	                raster.setPixel(x, y, pixelArray);
	            }
	        }
	        return bufferedImage;
	    }
	}

	public static ImageData convertToSWT (BufferedImage bufferedImage) {
	    if (bufferedImage.getColorModel() instanceof DirectColorModel) {
	        DirectColorModel colorModel = (DirectColorModel) bufferedImage.getColorModel();
	        PaletteData palette = new PaletteData(colorModel.getRedMask(), colorModel.getGreenMask(), colorModel.getBlueMask());
	        ImageData data = new ImageData(bufferedImage.getWidth(), bufferedImage.getHeight(), colorModel.getPixelSize(), palette);
	        WritableRaster raster = bufferedImage.getRaster();
	        int[] pixelArray = new int[3];
	        for (int y = 0; y < data.height; y++) {
	            for (int x = 0; x < data.width; x++) {
	                raster.getPixel(x, y, pixelArray);
	                int pixel = palette.getPixel(new RGB(pixelArray[0], pixelArray[1], pixelArray[2]));
	                data.setPixel(x, y, pixel);
	            }
	        }
	        return data;
	    } else if (bufferedImage.getColorModel() instanceof IndexColorModel) {
	        IndexColorModel colorModel = (IndexColorModel) bufferedImage.getColorModel();
	        int size = colorModel.getMapSize();
	        byte[] reds = new byte[size];
	        byte[] greens = new byte[size];
	        byte[] blues = new byte[size];
	        colorModel.getReds(reds);
	        colorModel.getGreens(greens);
	        colorModel.getBlues(blues);
	        RGB[] rgbs = new RGB[size];
	        for (int i = 0; i < rgbs.length; i++) {
	            rgbs[i] = new RGB(reds[i] & 0xFF, greens[i] & 0xFF, blues[i] & 0xFF);
	        }
	        PaletteData palette = new PaletteData(rgbs);
	        ImageData data = new ImageData(bufferedImage.getWidth(), bufferedImage.getHeight(), colorModel.getPixelSize(), palette);
	        data.transparentPixel = colorModel.getTransparentPixel();
	        WritableRaster raster = bufferedImage.getRaster();
	        int[] pixelArray = new int[1];
	        for (int y = 0; y < data.height; y++) {
	            for (int x = 0; x < data.width; x++) {
	                raster.getPixel(x, y, pixelArray);
	                data.setPixel(x, y, pixelArray[0]);
	            }
	        }
	        return data;
	    }
	    return null;
	}

	public static boolean isNullOrEmpty(String param) { 
		return param == null || param.isEmpty();
	}

	public static boolean isLinux() {
		return System.getProperty("os.name").toLowerCase().startsWith("linux");
	}
}
