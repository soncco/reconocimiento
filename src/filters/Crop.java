package filters;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.RectF;

public class Crop {
	
	public Crop() {
		
	}
	
	public static Bitmap crop(Bitmap bitmap) {
		int sourceWidth = bitmap.getWidth();
	    int sourceHeight = bitmap.getHeight();
	    
	    int newWidth = 400;
	    int newHeight = 400;

	    // Compute the scaling factors to fit the new height and width, respectively.
	    // To cover the final image, the final scaling will be the bigger 
	    // of these two.
	    float xScale = (float) newWidth / sourceWidth;
	    float yScale = (float) newHeight / sourceHeight;
	    float scale = Math.max(xScale, yScale);

	    // Now get the size of the source bitmap when scaled
	    float scaledWidth = scale * sourceWidth;
	    float scaledHeight = scale * sourceHeight;

	    // Let's find out the upper left coordinates if the scaled bitmap
	    // should be centered in the new size give by the parameters
	    float left = (newWidth - scaledWidth) / 2;
	    float top = (newHeight - scaledHeight) / 2;

	    // The target rectangle for the new, scaled version of the source bitmap will now
	    // be
	    RectF targetRect = new RectF(left, top, left + scaledWidth, top + scaledHeight);

	    // Finally, we create a new bitmap of the specified size and draw our new,
	    // scaled bitmap onto it.
	    Bitmap dest = Bitmap.createBitmap(newWidth, newHeight, bitmap.getConfig());
	    Canvas canvas = new Canvas(dest);
	    canvas.drawBitmap(bitmap, null, targetRect, null);

	    return dest;
	    
	}

}
