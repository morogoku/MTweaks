package com.morogoku.mtweaks.app;

import java.util.ArrayList;
import java.util.List;

import com.morogoku.mtweaks.app.R;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.text.Html;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;


public final class SyhSpinnerZ extends SyhControl implements OnItemSelectedListener{
	
	SyhSpinnerZ(Activity activityIn) {
		super(activityIn);
	}

	private Spinner spnnr = null;
	private List<String> nameList = new ArrayList<String>();
	private List<String> valueList = new ArrayList<String>();
	
	private int findValueInValueList(String value)
	{
		int index = -1;
		for (int i = 0; i < valueList.size(); i++)
		{
			if (valueList.get(i).equalsIgnoreCase(value))
			{
				index = i;
				break;
			}
		}
		return index;
	}
	
	private void setSpinnerFromHardwareValue()
	{
		//Log.w("spinner", this.name +" setSpinnerFromHardwareValue!");
		if (spnnr != null)
		{
			int index = findValueInValueList(valueFromScript);
			if (-1 == index) index = 0;
			spnnr.setSelection(index);
		}
		valueFromUser = valueFromScript;		
	}
	

	private void rayafinal() {
		
		TextView paneSeparatorBlank = new TextView(context);
		paneSeparatorBlank.setHeight(5);
        //--paneSeparatorBlank.setBackgroundColor(Color.BLACK);
        paneSeparatorBlank.setText("");
        controlLayout.addView(paneSeparatorBlank); 
		
		TextView paneSeparatorLine = new TextView(context);
		paneSeparatorLine.setHeight(2);
        paneSeparatorLine.setBackgroundColor(Color.LTGRAY);
        paneSeparatorLine.setText("");
        controlLayout.addView(paneSeparatorLine);  
        
		TextView paneSeparatorBlankAfterLine = new TextView(context);
		paneSeparatorBlankAfterLine.setHeight(10);
        //--paneSeparatorBlank.setBackgroundColor(Color.BLACK);
        paneSeparatorBlankAfterLine.setText("");
        controlLayout.addView(paneSeparatorBlankAfterLine);
	}
	
	@Override
	public void createInternal() {
		
		//Assumption: valueFromScript is set correctly.
		
		if (this.name.equalsIgnoreCase("FLL Tuning"))
		{
			Log.e("e","e");
		}

		//-- Set spinner programmatically
		//spnnr = new Spinner(context);
		//spnnr.setBackgroundColor(Color.argb(100, 143, 188, 143));
		
		//Create spinner from xml template
		
		/*
		 IMPORTANT NOTE:
		 	In summary to change the text size for a Spinner either:

			Create a custom TextView layout.
			Change the text size with the android:textSize attribute.
			Change the text color with android:textColor in the new style file.
			
			Or:
			
			Create a custom style.
			Use android:TextAppearance.Widget.TextView.SpinnerItem as the parent style.
			Change the text size with the android:textSize attribute.
			
			Or:
			
			Customize the theme
			
			http://androidcookbook.com/Recipe.seam;jsessionid=0443546CEE776318BF6D21552A9D1864?recipeId=4012
		*/
		
	
		spnnr = (Spinner) LayoutInflater.from(context).inflate(R.layout.template_spinner, controlLayout, false);
		
		ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(context, R.layout.template_spinner_item, nameList); //custom spinner
		//-- ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(context, android.R.layout.simple_spinner_item, nameList);
		//-- CustomArrayAdapter<String> dataAdapter = new CustomArrayAdapter<String>(context, nameList);
		
		//-- dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_item); //no radio buttons
		dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item); //radio buttons
		//-- dataAdapter.setDropDownViewResource(R.layout.template_spinner_dropdown_item); //custom dropdowns

		spnnr.setAdapter(dataAdapter);
		spnnr.setOnItemSelectedListener(this);
		setSpinnerFromHardwareValue();
		controlLayout.addView(spnnr);
		

        rayafinal();
		
		TextView tv2 = new TextView(context);
        controlLayout.addView(tv2);
        
        rayafinal();
        
        TextView tv3 = new TextView(context);
        controlLayout.addView(tv3); 
		
		//final TextView tv2 = (TextView) temp.findViewById(R.id.textZ);
		//tv2.setText("Kernel version: " + System.getProperty("os.version"));
		
        String s;
        String lang = Utils.executeRootCommandInThread("getprop persist.sys.language");
        //Si esta en español
        if (lang.startsWith("es")){
        	s = "<br>" +
        	"<b>PERFIL ZZMOOVE ACTIVO:</b> " + 
        		Utils.executeRootCommandInThread("cat /sys/devices/system/cpu/cpufreq/zzmoove/profile_number") + " " +
        		Utils.executeRootCommandInThread("cat /sys/devices/system/cpu/cpufreq/zzmoove/profile") +"<br><br>" +
        	"<i>Este es el perfil zzmoove activo ahora mismo leido directamente del kernel, "
        	+ "lo muestro aqui por si se cambia el selector cuando no esta activo el ZZMOOVE "
        	+ "entonces puede que luego no coincida cuando se active. "
        	+ "Si pasa esto selecionar otro perfil y aplicar."
        	+ "Para ver el cambio en PERFIL ZZMOOVE ACTIVO salir y entrar de nuevo en MTweaks</i><br>";

        }
        //Si es cualquier otro
        else {
        	s = "<br>" +
            "<b>ZZMOOVE ACTIVATED PROFILE:</b> " + 
            	Utils.executeRootCommandInThread("cat /sys/devices/system/cpu/cpufreq/zzmoove/profile_number") + " " +
            	Utils.executeRootCommandInThread("cat /sys/devices/system/cpu/cpufreq/zzmoove/profile") +"<br><br>" +
            "<i>This is the current active zzmoove profile, read directly from the kernel, "
            + "I show it here in case the selector switch is changed when ZZMOOVE is not active, "
            + "then it may not match when is activated. "
            + "If this happen, choose another profile and then aply. "
            + "To see the change in ZZMOOVE ACTIVATED PROFILE, update or restart the MTweaks app.</i><br>";

        }
        	

		String s2 = "<br>" +
		"<b><u>LISTADO DE PERFILES ZZMOOVE</u></b><br><br>" +
 		"<b>* Default:</b> set governor defaults. <br><br>" +
		"<b>* Yank Battery:</b> old untouched setting (a very good battery/performance balanced setting DEV-NOTE: highly recommended!)<br><br>" +
		"<b>* Yank Battery Extreme:</b> old untouched setting (like yank battery but focus on battery saving) <br><br>" +
		"<b>* ZaneZam Battery:</b> old untouched setting (a more 'harsh' setting strictly focused on battery saving DEV-NOTE: might give some lags!) <br><br>" +
		"<b>* ZaneZam Battery Plus:</b> NEW! reworked 'faster' battery setting (DEV-NOTE: recommended too!  ) <br><br>" +
		"<b>* ZaneZam Optimized:</b> old untouched setting (balanced setting with no focus in any direction DEV-NOTE: relict from back in the days, even though some people still like it!)<br><br>" +
		"<b>* ZaneZam Moderate:</b> NEW! setting based on 'zzopt' which has mainly (but not strictly only!) 2 cores online.<br><br>" +
		"<b>* ZaneZam Performance:</b> old untouched setting (all you can get from zzmoove in terms of performance but still has the fast down scaling/hotplugging behaving)<br><br>" +
		"<b>* ZaneZam InZane:</b> NEW! based on performance with new auto fast scaling active. a new experience!<br><br>" +
		"<b>* ZaneZam Gaming:</b> NEW! based on performance with new scaling block enabled to avoid cpu overheating during gameplay<br><br>" +
		"<b>* ZaneZam Relax:</b> NEW! based on moderate (except hotplug settings) with relaxed sleep settings (to react audio/bluetooth/wakeup issues)<br>";
		tv2.setText(Html.fromHtml(s));
		tv3.setText(Html.fromHtml(s2));
	}
	
	public void addNameAndValue(String name, String value){
		nameList.add(name);
		valueList.add(value);
	}
	
	public void clearNameAndValues(){
		nameList.clear();
		valueList.clear();
	}
	
	@Override
	public void onItemSelected(AdapterView<?> parent, View view, 
            int pos, long id) 
	{
		//Log.w("spinner", this.name +" onItemSelected!");
		valueFromUser = valueList.get(pos);
		//-- ((TextView)parent.getChildAt(0)).setTextColor(Color.WHITE);//TODO: Change selected text color

		if (isChanged())
    	{
    		this.vci.valueChanged();//TODO: changing text color back to black!!!
    	}
    }
	
	@Override
	public void onNothingSelected(AdapterView<?> arg0) {
		// TODO Auto-generated method stub
	}
	
	static class CustomArrayAdapter<T> extends ArrayAdapter<T>
	{
	    public CustomArrayAdapter(Context ctx, List<T> objects)
	    {
	        super(ctx, android.R.layout.simple_spinner_item, objects);
	    }

	    //other constructors
	    @Override
	    public View getDropDownView(int position, View convertView, ViewGroup parent)
	    {
	        View view = super.getView(position, convertView, parent);

	        //we know that simple_spinner_item has android.R.id.text1 TextView:         

	        /* if(isDroidX) {*/
	            TextView text = (TextView)view.findViewById(android.R.id.text1);
	            text.setTextColor(Color.RED);//choose your color :)         
	        /*}*/

	        return view;
	    }
	}

	@Override
	protected void applyScriptValueToUserInterface() {
		setSpinnerFromHardwareValue();	
	}

	
	@Override
	protected String getDefaultValue() {
		return valueList.get(0);
	}

	
}
