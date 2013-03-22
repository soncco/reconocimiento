package filters;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Bitmap.Config;

public class BinarizeFilter {
	
	public BinarizeFilter() {
		
	}
	
	public static Bitmap binarize(Bitmap oldImage) {
		
		int red, green, blue, pixel, prom;
		Bitmap newBitmap = Bitmap.createBitmap(oldImage.getWidth(), oldImage.getHeight(), Config.ARGB_8888);
		
        for(int i = 0; i < oldImage.getWidth(); i++) {
        	for(int j = 0; j < oldImage.getWidth(); j++) {
        		
        		pixel = oldImage.getPixel(i, j);
        		red = Color.red(pixel);
        		green = Color.green(pixel);
        		blue = Color.blue(pixel);
        		prom = Math.round((red + green + blue)/3);
        		
        		if(prom > 127)
        			newBitmap.setPixel(i, j, Color.WHITE);
        		else
        			newBitmap.setPixel(i, j, Color.BLACK);
        	} 
        }
        
        return newBitmap;
	}

}
