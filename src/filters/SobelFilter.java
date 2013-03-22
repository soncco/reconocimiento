package filters;

import com.nostra13.universalimageloader.utils.L;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Bitmap.Config;
import util.Matrix;

public class SobelFilter {
	
	public static int N = 3;
	public static int M = 3;
	
	public static int MLimit = 3;
	public static int NLimit = 3;

	int x = 0;
	int y = 0;
	
	public SobelFilter() {
		
	}
	
	public static Bitmap sobel(Bitmap oldImage) {
		int red, green, blue, pixel;
		double newred, newgreen, newblue;
		Bitmap newBitmap = Bitmap.createBitmap(oldImage.getWidth(), oldImage.getHeight(), Config.ARGB_8888);
		int x = oldImage.getWidth();
		int y = oldImage.getHeight();
		
		double[][] data = { { -1, -2, -1 }, { 0, 0, 0 }, { 1, 2, 1 } };
		double[][] data2 = { { -1, 0, 1 }, { -2, 0, 2 }, { -1, 0, 1 } };
		
		Matrix mat1 = new Matrix(data);
		Matrix mat2 = new Matrix(data2);
		
		MLimit = (M - 1) / 2;
		NLimit = (N - 1) / 2;
		
		// Aux 
		
		
		int bx = 0;
		int cx = 0;
        for(int i = 0; i < oldImage.getWidth(); i++) {
        	for(int j = 0; j < oldImage.getWidth(); j++) {
        		
        		pixel = oldImage.getPixel(i, j);
        		red = Color.red(pixel);
        		green = Color.green(pixel);
        		blue = Color.blue(pixel);
        		
        		if(i < N || i > (x - N) || j < M || j > (y - M))
        		{		
        			newBitmap.setPixel(i, j, Color.rgb(red, green, blue));
        			bx++;
        		} else {
        			double sum1r = 0;
        	        double sum1b = 0;
        	        double sum1g = 0;
        	        double sum2r = 0;
        	        double sum2b = 0;
        	        double sum2g = 0;
        			cx++;
        			for (int k = -NLimit; k <= NLimit; k++) {
        				for (int l = -MLimit; l <= MLimit; l++) {
        					int pixelV = oldImage.getPixel(i + l, j + k);
        		            int redV = Color.red(pixelV);
        		            int greenV = Color.green(pixelV);
        		            int blueV = Color.blue(pixelV);

        					// get the corresponding filter coefficient
        					double c = mat1.getData(k + NLimit, l + MLimit);
        					sum1r = sum1r + c * redV;
        					sum1g = sum1g + c * greenV;
        					sum1b = sum1b + c * blueV;

        					c = mat2.getData(k + NLimit, l + MLimit);
        					sum2r = sum2r + c * redV;
        					sum2g = sum2g + c * greenV;
        					sum2b = sum2b + c * blueV;

        				}
        			}
        			
        			newred = sum1r + sum2r;
        			newgreen = sum1g + sum2g;
        			newblue = sum1b + sum2b;
        			
        			//L.i("R: " + newred + " G: " + newgreen + "B: " + newblue);
        			
        			if(newred > 1)
        				newred = 255;
        			if(newred < 0)
        				newred = 0;
        			if(newgreen > 1)
        				newgreen = 255;
        			if(newgreen < 0)
        				newgreen = 0;
        			if(newblue > 1)
        				newblue = 255;
        			if(newblue < 0)
        				newblue = 0;

        			newBitmap.setPixel(i, j, Color.rgb((int)newred, (int)newgreen, (int)newblue));
        		}
        	} 
        }
        
        L.i("bx: " + bx + " cx: " + cx);
        
        return newBitmap;
	}

}
