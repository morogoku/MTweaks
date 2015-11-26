package com.morogoku.mtweaks.app;

import com.morogoku.mtweaks.app.R;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager.NameNotFoundException;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.net.Uri;

public class SyhExtrasTab extends SyhTab implements OnClickListener {
     public SyhExtrasTab(Context context, Activity activity) {
		super(context, activity);
		this.name = "Info";
	}

          
	@Override
	public View getCustomView(ViewGroup parent)
	{
 		 View v = LayoutInflater.from(mContext).inflate(R.layout.syh_extrastab, parent, false);
     	 
    	 final TextView tv = (TextView) v.findViewById(R.id.textViewAppVersion);
 		 try 
 		 {
 	    	final String appVersion = mContext.getPackageManager().getPackageInfo(mContext.getPackageName(), 0).versionName;
			tv.setText(Html.fromHtml("<b>MTweaks</b> v" + appVersion));
 		 } 
 		 catch (NameNotFoundException e) 
 		 {
			tv.setText("App Version: Not found!");
 		 }
 		 
 		 final TextView ker = (TextView) v.findViewById(R.id.kernel_ver_txt);
  	     ker.setText(Utils.executeRootCommandInThread("cat /proc/version"));
  	     
 		 final TextView asv = (TextView) v.findViewById(R.id.asv_txt);
   	     asv.setText(Utils.executeRootCommandInThread("cat /sys/devices/system/cpu/cpu0/cpufreq/asv_level | cut -c 12-"));
   	     
   	     final TextView modelo = (TextView) v.findViewById(R.id.modelo_txt);
	     modelo.setText(android.os.Build.MODEL);
	     
	     final TextView rom_name = (TextView) v.findViewById(R.id.rom_name_txt);
	     rom_name.setText(android.os.Build.DISPLAY);
	     
	     final TextView rom = (TextView) v.findViewById(R.id.rom_txt);
	     rom.setText(android.os.Build.VERSION.INCREMENTAL);
	     
	     final TextView modem = (TextView) v.findViewById(R.id.modem_txt);
	     modem.setText(android.os.Build.getRadioVersion());
	     
	     final TextView boot = (TextView) v.findViewById(R.id.boot_txt);
	     boot.setText(android.os.Build.BOOTLOADER);
	     
	     final TextView and = (TextView) v.findViewById(R.id.android_txt);
	     and.setText(android.os.Build.VERSION.RELEASE);
	     
	     final TextView hwserial = (TextView) v.findViewById(R.id.hwserial_txt);
	     hwserial.setText(android.os.Build.SERIAL);
 		 
		 final Button button5 = (Button) v.findViewById(R.id.kernelweb);
         button5.setOnClickListener(this);
  		 
    	 //final TextView tv2 = (TextView) v.findViewById(R.id.textViewKernelVersion);
    	 //tv2.setText("Kernel version: " + System.getProperty("os.version"));

    	 //String s = "";
    	 //s += "\n Kernel Version: " + System.getProperty("os.version");
    	 //s += "\n Kernel Version: " + Utils.executeRootCommandInThread("cat /proc/version");
    	 //s += "\n " + Utils.executeRootCommandInThread("cat /sys/devices/system/cpu/cpu0/cpufreq/asv_level");
    	 //s += "\n Modelo: " + android.os.Build.MODEL;
    	 //s += "\n ROM Version: " + android.os.Build.VERSION.INCREMENTAL;
    	 //s += "\n Banda Base: " + android.os.Build.getRadioVersion();
    	 //s += "\n Bootloader: " + android.os.Build.BOOTLOADER;
    	 //s += "\n Versi�n Android: " + android.os.Build.VERSION.RELEASE;
       	 //s += "\n Nivel API ROM: " + android.os.Build.VERSION.SDK_INT;
       	 //s += "\n Hardware Serial: " + android.os.Build.SERIAL;
      	 //s += "\n Device: " + android.os.Build.DEVICE;
    	 //s += "\n Model (and Product): " + android.os.Build.MODEL + " ("+ android.os.Build.PRODUCT + ")";   
    	 //tv2.setText(s);
    	 		 
    	 
 		 return v;
	}
	
	
	@Override
	public void onClick(View v) {
		switch(v.getId()) {
	        case R.id.kernelweb:
	            Context objContext;
	            objContext= mContext;
	            Intent i = new Intent(android.content.Intent.ACTION_VIEW, Uri.parse("http://www.esp-desarrolladores.com/showthread.php?t=4173"));
	            objContext.startActivity(i);
	            break;
		}   
	}

}

