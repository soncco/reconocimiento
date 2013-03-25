package brau.robotica.biometrico;

import java.util.ArrayList;
import java.util.List;

import filters.Compare;
import filters.GrayFilter;
import filters.Process;
import filters.Crop;
import filters.NegativeFilter;
import filters.SobelFilter;

//import com.nostra13.universalimageloader.utils.L;

import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Point;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
//import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Toast;

public class CompareActivity extends Activity {

	// Controles.
	private Spinner sp = null;
	private ImageView img = null;
	private ImageView img2 = null;
	private Button bt = null;
	private Button cp = null;
	private TextView res = null;
	
	// Variables.
	private Persona persona;
	private Bitmap storedBitmap;
	private Bitmap takenBitmap;
	
	// Request code para la actividad.
	public static final int CAMERA_REQUEST  = 8888;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_compare);
		
		// Instancia de DB.
		final DatabaseHandler db = new DatabaseHandler(this);
		
		// Obtenemos los datos.
		List<String> names = db.getAllNames();
		
		// Llenamos el Spinner.
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
	            android.R.layout.simple_dropdown_item_1line, names);		
		sp = (Spinner) findViewById(R.id.autoCompleteTextView1);
		sp.setAdapter(adapter);
		
		// Listener para mostrar la imagen procesada al escoger un nombre.
		sp.setOnItemSelectedListener(new OnItemSelectedListener() {
			
			@Override
			public void onItemSelected(AdapterView<?> parentView, View selectedItem,
					int position, long id) {
				String nombre = parentView.getItemAtPosition(position).toString();
				persona = db.getPersonaByName(nombre);
				img = (ImageView) findViewById(R.id.ImageView02);
				Bitmap myBitmap = processBitmap(BitmapFactory.decodeFile(persona.getPicture()));
				Toast.makeText(parentView.getContext(), persona.getPicture(), Toast.LENGTH_SHORT).show();
                img.setImageBitmap(myBitmap);
                storedBitmap = myBitmap;
			}

			@Override
			public void onNothingSelected(AdapterView<?> parentView) {
				
			}
		});
		
		bt = (Button) findViewById(R.id.button1);
		// Listener para activar la cámara.
		bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE); 
                startActivityForResult(cameraIntent, CAMERA_REQUEST); 
            }
        });
		
		cp = (Button) findViewById(R.id.button2);
		
		res = (TextView) findViewById(R.id.textView3);
		
		// Listener para empezar la comparación.
		cp.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				
				ArrayList<Point> lines1 = new ArrayList<Point>();
				ArrayList<Point> lines2 = new ArrayList<Point>();
				Process process1 = new Process();
				Process process2 = new Process();

				lines1 = process1.updown(storedBitmap);
		        lines2 = process2.updown(takenBitmap);

		        double percent = Compare.compare(lines1, process1.getCamino(), lines2, process2.getCamino());

		        Toast.makeText(getApplicationContext(), String.valueOf(percent), Toast.LENGTH_LONG).show();
		        
		        res.setText("Resultado de la imagen: " + String.valueOf(percent) + "%");
			}
		});
		
	}
	
	// Listener para mostrar la imagen tomada en el ImageView nuevo.
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (requestCode == CAMERA_REQUEST && resultCode == RESULT_OK) { 
			try {
				img2 = (ImageView) findViewById(R.id.ImageView03);
		        takenBitmap = (Bitmap) data.getExtras().get("data");
		        takenBitmap = processBitmap(Crop.crop(takenBitmap));
		        img2.setImageBitmap(takenBitmap);
		        
		        Toast.makeText(getApplicationContext(), (String)("W: " + takenBitmap.getWidth() + "H: " + takenBitmap.getHeight()), Toast.LENGTH_LONG).show();

		        /*storedBitmap = processBitmap(storedBitmap);
		        takenBitmap = processBitmap(takenBitmap);*/
		        
			} catch(Exception e) {
				//dialogo(e.toString());
				Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_LONG).show();
			}
	    } 
    }
	
	/**
	 * Procesa una imagen y le aplica filtros.
	 * @param bitmap
	 * @return
	 */
	public Bitmap processBitmap(Bitmap bitmap) {
		bitmap = GrayFilter.gray(bitmap);
		bitmap = SobelFilter.sobel(bitmap);
		bitmap = NegativeFilter.negativize(bitmap);
		return bitmap;
	}
	
	

}
