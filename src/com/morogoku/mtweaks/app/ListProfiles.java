package com.morogoku.mtweaks.app;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;

import java.io.File;
import java.util.List;
import java.util.ArrayList;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;


@SuppressLint("InflateParams")
public class ListProfiles extends Activity {

	ListView listView ;
	private List<String> item = null;
	final Context mContext = this;

	
	protected void restart() {

		Intent intent = getIntent();
    	overridePendingTransition(0, 0);
    	intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
    	finish();
    	overridePendingTransition(0, 0);
    	startActivity(intent);
	}
	
	
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_profiles);
        
        //Creamos el array para almacenar el listado de archivos
        item = new ArrayList<String>();
      
        String dirPath = "/data/.mtweaks";
        
        File f = new File(dirPath);
        File[] filelist = f.listFiles();
        
        for (int i = 0; i < filelist.length; i++)
        {
            File file = filelist[i];
            
            // Listamos si es un archivo
            if (file.isFile())
            {
            	// Si termina en ".profile"
            	if (file.getName().endsWith(".profile"))
            	{
            		// Si empieza por "." no listamos 
            		if (file.getName().startsWith(".")){
            		}
            		else
            		{
            			String s = file.getName();
            			s = s.substring(0, s.length() - 8);
            			item.add(s);
            		}
            	}
            }
        }
        
        // Get ListView object from xml
        listView = (ListView) findViewById(R.id.list);
        
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
            android.R.layout.simple_list_item_1, android.R.id.text1, item);

        // Assign adapter to ListView
        listView.setAdapter(adapter); 
        
        // ListView Item Click Listener
        listView.setOnItemClickListener(new OnItemClickListener() {
        	
        	// Acciones al hacer click simple
	        public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
	            
	        	// Guardamos el nombre del archivo pulsado en itemValue
	            final String itemValue = (String) listView.getItemAtPosition(position);
            	 
	            // Mostramos aviso de carga
	            AlertDialog.Builder builder1 = new AlertDialog.Builder(mContext);
	            builder1.setMessage(getString(R.string.profile_warning, itemValue))
	  	            .setPositiveButton(R.string.btn_yes, new DialogInterface.OnClickListener() {
	  	            	public void onClick(DialogInterface dialog, int id) {
	  	            		// Si pulsamos SI mandamos el nombre a MainActivity para cargar el perfil alli
	  	            		Intent i = new Intent( ListProfiles.this, ListProfiles.class );
	  	            		i.putExtra("filename", item.get(position));
	  	            		setResult( Activity.RESULT_OK, i );
	  	            		ListProfiles.this.finish();
	  	        	    }
	  	        	})
	  	        	.setNegativeButton(R.string.btn_no, new DialogInterface.OnClickListener() {
	  	        		public void onClick(DialogInterface dialog, int id) {
	  	        			// Si pulsamos NO, no hacemos nada 
	   	        	    }
	  	        	})
					.setTitle(R.string.warning_title)
					.setIcon(R.drawable.ic_launcher)
					.create();
	  	        builder1.show();
	        }
	
	    });
        
        // ListView Item LongClick Listener
        listView.setOnItemLongClickListener(new OnItemLongClickListener() {
        	 
        	// Con pulsacion larga mostramos Opciones 
        	public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
        		
        		// Guardamos el nombre del archivo pulsado en itemValue 
        		final String itemValue = (String) listView.getItemAtPosition(position);
        		
        		// Proteccion de los perfiles de sistema para no modificarlos
        		// Si pulsamos default no mostramos opciones y toast de aviso
        		if ("default".equals(itemValue)){
        			Toast toast1 = Toast.makeText(getApplicationContext(), R.string.no_modify_default, Toast.LENGTH_LONG);
        	        toast1.show(); 
        		}
        		// Si pulsamos battery no mostramos opciones y toast de aviso
        		else if ("battery".equals(itemValue)) {
        			Toast toast1 = Toast.makeText(getApplicationContext(), R.string.no_modify_battery, Toast.LENGTH_LONG);
        	        toast1.show();
        		}
        		// Si pulsamos performance no mostramos opciones y toast de aviso
        		else if ("performance".equals(itemValue)){
        			Toast toast1 = Toast.makeText(getApplicationContext(), R.string.no_modify_performance, Toast.LENGTH_LONG);
        	        toast1.show();
        		}
        		// Para el resto mostramos opciones
        		else {
 	            	
	        		String[] opc = new String[] { "Renombrar", "Eliminar"};
	                
	        		AlertDialog opciones = new AlertDialog.Builder(ListProfiles.this)
	        			.setTitle("Opciones")
	        			.setItems(opc, new DialogInterface.OnClickListener() {
	        			    public void onClick(DialogInterface dialog, int selected) {
	        			    	// Si elegimos Renombrar:
	        			    	if (selected == 0) {
									//mostramos cuadro de dialogo para renombrar
		  	        	     		LayoutInflater layoutInflater = LayoutInflater.from(ListProfiles.this);
		  	        	     		View promptView = layoutInflater.inflate(R.layout.rename_dialog, null);
		  	        	     		AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(ListProfiles.this);
		  	        	     		alertDialogBuilder.setView(promptView);
	
		  	        	     		final EditText editText = (EditText) promptView.findViewById(R.id.rename_edittext);

		  	        	     		// Aviso de confirmacion
		  	        	     		alertDialogBuilder.setCancelable(false)
	  	        	     				.setPositiveButton(R.string.btn_ok, new DialogInterface.OnClickListener() {
	  	        	     					public void onClick(DialogInterface dialog, int id) {
	  	        	     						// Si aceptamos renombramos el perfil
	  	        	     						String name = editText.getText().toString();
	  	        	     						Utils.executeRootCommandInThread("/res/uci.sh rename "+itemValue+" "+name);
	  	        	     						
	  	        	     						// Si renombramos el perfil activo restauramos el nuevo perfil
	  	        	     						String perfilActivo = Utils.leer("/data/.mtweaks/.active.profile");
	  	        	     						if (perfilActivo.equals(name)){
	  	        	     							// Toast informativo
	  	        	     							Toast toast1 = Toast.makeText(getApplicationContext(), getString(R.string.rename_active_profile, name), Toast.LENGTH_LONG);
					  	        	    			toast1.show();
					  	        	    			// Devuelvo el valor del nuevo perfil para recargarlo
	  	        	     							Intent i = new Intent( ListProfiles.this, ListProfiles.class );
		  	        	  	  	            		i.putExtra("filename", name);
		  	        	  	  	            		setResult( Activity.RESULT_OK, i );
		  	        	  	  	            		ListProfiles.this.finish();
		  	        	  	  	            	// Si no es el perfil activo volvemos a restaurar perfil
	  	        	     						} else {
	  	        	     							Toast toast1 = Toast.makeText(getApplicationContext(), getString(R.string.rename_profile, name), Toast.LENGTH_LONG);
					  	        	    			toast1.show();
		  	        	     						// Devuelvo el valor "restart" a MainActivity para que me retorne a ListProfiles.
		  	        	     						// Si reinicio ListProfiles sin mas luego no funciona "onActivityResult" en MainActivity
		  	        	     						Intent i = new Intent( ListProfiles.this, ListProfiles.class );
		  	        		  	            		i.putExtra("filename", "restart");
		  	        		  	            		setResult( Activity.RESULT_OK, i );
		  	        		  	            		ListProfiles.this.finish();
	  	        	     						}
	  	        	     					}
	  	        	     				})
	  	        	     				.setNegativeButton(R.string.btn_cancel, new DialogInterface.OnClickListener() {
	  	        	     					public void onClick(DialogInterface dialog, int id) {
	  	        	     						// Si cancelamos no hacemos nada
	  	        	     						dialog.cancel();
	  	        	     					}
	  	        	     				});
	
		  	        	     		// create an alert dialog
		  	        	     		AlertDialog alert = alertDialogBuilder.create();
		  	        	     		alert.show();
		  	        	     		
		  	        	     	// Si elegimos Eliminar				
								} else if (selected == 1) {
									//acciones para eliminar
									AlertDialog.Builder builder1 = new AlertDialog.Builder(mContext);
					  	        	builder1.setMessage(getString(R.string.delete_warning, itemValue))
				  	        	    	.setPositiveButton(R.string.btn_yes, new DialogInterface.OnClickListener() {
				  	        	    		public void onClick(DialogInterface dialog, int id) {
				  	        	    			// Si aceptamos eliminamos el perfil
				  	        	    			Utils.executeRootCommandInThread("rm $PROFILE_PATH/"+itemValue+".profile");
				  	        	    			// Toast de eliminacion
				  	        	    			Toast toast1 = Toast.makeText(getApplicationContext(), getString(R.string.delete_toast, itemValue), Toast.LENGTH_LONG);
				  	        	    			toast1.show();
				  	        	    			// Devuelvo el valor "restart" a MainActivity para que me retorne a ListProfiles.
	  	        	     						// Si reinicio ListProfiles sin mas luego no funciona "onActivityResult" en MainActivity
				  	        	    			Intent i = new Intent( ListProfiles.this, ListProfiles.class );
	  	        		  	            		i.putExtra("filename", "restart");
	  	        		  	            		setResult( Activity.RESULT_OK, i );
	  	        		  	            		ListProfiles.this.finish();
				  	        	    		}
				  	        	    	})
				  	        	    	.setNegativeButton(R.string.btn_no, new DialogInterface.OnClickListener() {
				  	        	    		public void onClick(DialogInterface dialog, int id) {
				  	        	    			// Si cancelamos no hacemos nada
				  	        	    		}
				  	        	    	})
				  	        	    	.setTitle(R.string.warning_title)
				  	        	    	.setIcon(R.drawable.ic_launcher)
				  	        	    	.create();
					  	        	builder1.show();
								}
	        				}
	        			})
	        			.create();
	        	    opciones.show();
        		}
        		return true;
        	}
        }); 

    }

}

