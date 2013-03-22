package filters;

//import java.awt.image.MemoryImageSource;
import java.nio.IntBuffer;

import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;

import fourier.FFT;
import fourier.InverseFFT;
import fourier.TwoDArray;

public class FourierInverse {
	
	protected static Bitmap auxImg;
	public static int black;
	public static int orig[] = null;
	public static int width, height;
	private static FFT fft;
	//private MemoryImageSource foutput;
	public static IntBuffer foutput;
	private static Bitmap finalimg;
	private static InverseFFT inverse;
	private static int[] texture;
	
	public FourierInverse() {
		
	}
	
	public static Bitmap inverse(Bitmap oldImage) {
		height = oldImage.getHeight();
		width = oldImage.getWidth();
		
		texture = new int[width *  height];

		inverse = new InverseFFT();
		orig = new int[width * height];
		/*PixelGrabber grabber = new PixelGrabber(oldImg, 0, 0, width, height,
				orig, 0, width);
		try {
			grabber.grabPixels();
		} catch (InterruptedException e2) {
			System.out.println("error: " + e2);
		}*/
		oldImage.getPixels(texture, 0, width, 0, 0, width, height);

		fft = new FFT(orig, width, height);
		TwoDArray output = inverse.transform(fft.intermediate);
		//foutput = new MemoryImageSource(width, height, inverse.getPixels(output), 0, width);
		foutput = IntBuffer.allocate(width*height);

		//finalimg = new BufferedImage(width, width, BufferedImage.TYPE_INT_RGB);
		finalimg = Bitmap.createBitmap(oldImage.getWidth(), oldImage.getHeight(), Config.ARGB_8888);
		//Image piximg = Toolkit.getDefaultToolkit().createImage(foutput);
		finalimg.copyPixelsToBuffer(foutput);
		//finalimg.getGraphics().drawImage(piximg, 0, 0, null);
		
		return finalimg;
	}

}
