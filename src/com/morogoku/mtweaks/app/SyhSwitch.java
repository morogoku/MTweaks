package com.morogoku.mtweaks.app;

import com.morogoku.mtweaks.app.R;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Switch;

public class SyhSwitch extends SyhControl implements OnClickListener{

	protected SyhSwitch(Activity activityIn) {
		super(activityIn);
	}

	private Switch _switch;
	public String label;
	
	@Override
	public void createInternal() {		
		
		//Assumption: valueFromScript is set correctly. 

/*
 		//OK: Move this to xml
		_switch = new Switch(context);
		_switch.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
		_switch.setTextColor(Color.WHITE);
		_switch.setGravity(Gravity.CENTER);
		_switch.setText(label);
		_switch.setChecked(false);  
		_switch.setOnClickListener(this);
		controlLayout.addView(_switch);
*/
		//create CheckBox from xml template
		//View temp = LayoutInflater.from(context).inflate(R.layout.template__switch, controlLayout, false);
		//_switch = (Switch) temp.findViewById(R.id.SyhSwitch);
		
		_switch = (Switch)LayoutInflater.from(context).inflate(R.layout.template_switch, controlLayout, false);
		_switch.setText(label);
		_switch.setOnClickListener(this);
		
		//--_switch.setChecked(convertFromScriptFormatToControlFormat(valueFromScript));  
		applyScriptValueToUserInterface();
		
		controlLayout.addView(_switch);
	}

	@Override
	public void onClick(View v) {
		//-- This not true >>>  this.valueInput = Boolean.toString(_switch.isChecked());
		this.valueFromUser = convertFromControlFormatToScriptFormat(_switch.isChecked());
		this.vci.valueChanged();
	}

	@Override
	protected void applyScriptValueToUserInterface() {
		//-- This not true >>> boolean hardware = Boolean.parseBoolean(this.valueHardware);

		if (_switch != null)
		{
			boolean hardware = convertFromScriptFormatToControlFormat(valueFromScript);
			_switch.setChecked(hardware);
		}		
		valueFromUser = valueFromScript;
	}

	protected Boolean convertFromScriptFormatToControlFormat(String input) {
		boolean hardware = input.equals("on");
		return hardware;
	}

	protected String convertFromControlFormatToScriptFormat(Boolean input) {
		String scriptVal = (input) ? ("on"): ("off");
		return scriptVal;
	}

	
	@Override
	protected String getDefaultValue() {
		return "off";
	}


}
