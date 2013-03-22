package brau.robotica.biometrico;

import filters.BinarizeFilter;
import filters.FourierInverse;
import filters.GrayFilter;
import filters.NegativeFilter;
import filters.SobelFilter;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;

public class MainActivity extends Activity {
	
	private static final int SELECT_PICTURE = 1;
	private String selectedImagePath;
	private ImageView img;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		img = (ImageView) findViewById(R.id.ImageView01);
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
	    // Handle item selection
	    switch (item.getItemId()) {
	        case R.id.action_choose:
	        	Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent,"Select Picture"), SELECT_PICTURE);
	            return true;
	        default:
	            return super.onOptionsItemSelected(item);
	    }
	}
	
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            if (requestCode == SELECT_PICTURE) {
                Uri selectedImageUri = data.getData();
                selectedImagePath = getPath(selectedImageUri);
                Bitmap myBitmap = BitmapFactory.decodeFile(selectedImagePath);
                System.out.println("Image Path : " + selectedImagePath);
                Bitmap newBitmap = null;
                //newBitmap = BinarizeFilter.binarize(myBitmap);
                newBitmap = GrayFilter.gray(myBitmap);
                newBitmap = SobelFilter.sobel(newBitmap);
                newBitmap = NegativeFilter.negativize(newBitmap);                
                //newBitmap = FourierInverse.inverse(myBitmap);
                
                //img.setImageURI(selectedImageUri);
                img.setImageBitmap(newBitmap);
                
            }
        }
    }
 
    public String getPath(Uri uri) {
        String[] projection = { MediaStore.Images.Media.DATA };
        Cursor cursor = getContentResolver().query(uri, projection, null, null, null);
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        return cursor.getString(column_index);
    }

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
