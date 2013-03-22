package filters;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Bitmap.Config;

public class NegativeFilter {
	
	public NegativeFilter() {
		
	}
	
	public static Bitmap negativize(Bitmap oldImage) {
		
		int red, green, blue, pixel;
		int newred, newgreen, newblue;
		Bitmap newBitmap = Bitmap.createBitmap(oldImage.getWidth(), oldImage.getHeight(), Config.ARGB_8888);
		
        for(int i = 0; i < oldImage.getWidth(); i++) {
        	for(int j = 0; j < oldImage.getWidth(); j++) {
        		
        		pixel = oldImage.getPixel(i, j);
        		red = Color.red(pixel);
        		green = Color.green(pixel);
        		blue = Color.blue(pixel);
        		
        		newred = (255 - red); 		 
        		newgreen = (255 - green);
        		newblue = (255 - blue); 		 

    			newBitmap.setPixel(i, j, Color.rgb(newred, newgreen, newblue));
        	} 
        }
        
        return newBitmap;
	}

}
