package brau.robotica.biometrico;

import filters.GrayFilter;
import filters.NegativeFilter;
import filters.SobelFilter;

import android.os.Bundle;
import android.os.Environment;
import android.app.Activity;
import android.util.Log;
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

	/**
	 * Crea la primera Actividad y también la Base de datos.
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		img = (ImageView) findViewById(R.id.ImageView01);
		
		createDB();
	}

	/**
	 * Maneja el menú principal.
	 */
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
	    switch (item.getItemId()) {
	    	// EScoje una imagen del sistema de archivos.
	        case R.id.action_choose:
	        	Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent,"Seleccionar imagen"), SELECT_PICTURE);
	            return true;
	        // Lanza la acción Login.
	        case R.id.action_test:
	        	Intent intentTest = new Intent(this, CompareActivity.class);
	        	startActivity(intentTest);	        	
	        	return true;
		default:
	            return super.onOptionsItemSelected(item);
	    }
	}
	
	/**
	 * Maneja el resultado de la actividad.
	 */
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            if (requestCode == SELECT_PICTURE) {
            	// Toma la imagen seleccionada y aplica los filtros.
                Uri selectedImageUri = data.getData();
                selectedImagePath = getPath(selectedImageUri);
                Bitmap myBitmap = BitmapFactory.decodeFile(selectedImagePath);
                System.out.println("Image Path : " + selectedImagePath);
                Bitmap newBitmap = null;
                // Filtro Escala de Grises.
                newBitmap = GrayFilter.gray(myBitmap);
                // Filtro Operador Sobel.
                newBitmap = SobelFilter.sobel(newBitmap);
                // Filtro Negativo.
                newBitmap = NegativeFilter.negativize(newBitmap);                
                img.setImageBitmap(newBitmap);
                
            }
        }
    }
 
	/**
	 * Obtiene el path de una imagen recién tomada.
	 * @param uri
	 * @return
	 */
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
	
	/**
	 * Crea la base de datos e ingresa datos.
	 */
	public void createDB() {
		DatabaseHandler db = new DatabaseHandler(this);
        /**
         * CRUD Operations
         * */
        // Inserting Contacts
		String path = Environment.getExternalStorageDirectory().getAbsolutePath() + "/DCIM/est/";
        Log.d("Insert: ", "Inserting ..");
        db.addContact(new Persona("Teresa", path + "pic1.jpg"));
        db.addContact(new Persona("Nadia", path + "pic2.jpg"));
        db.addContact(new Persona("Silvia", path + "pic3.jpg"));
        db.addContact(new Persona("Braulio", path + "pic4.jpg"));
        db.addContact(new Persona("Roberto", path + "pic5.jpg"));
        db.addContact(new Persona("Grace", path + "pic6.jpg"));
	}

}
