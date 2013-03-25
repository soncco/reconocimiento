package filters;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Bitmap.Config;

public class GrayFilter {
	
	public GrayFilter() {
		
	}
	
	public static Bitmap gray(Bitmap oldImage) {
		
		int red, green, blue, pixel;
		Bitmap newBitmap = Bitmap.createBitmap(oldImage.getWidth(), oldImage.getHeight(), Config.ARGB_8888);
		
        for(int i = 0; i < oldImage.getWidth(); i++) {
        	for(int j = 0; j < oldImage.getWidth(); j++) {
        		
        		pixel = oldImage.getPixel(i, j);
        		red = Color.red(pixel);
        		green = red;
        		blue = red;
        		
       			newBitmap.setPixel(i, j, Color.rgb(red, green, blue));
        	} 
        }
        
        return newBitmap;
	}

}
