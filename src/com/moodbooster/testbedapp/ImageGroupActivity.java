package com.moodbooster.testbedapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;
import android.view.View;
import android.view.View.OnClickListener;


public class ImageGroupActivity extends Activity {
	
	private RadioGroup imageGroup;
	private RadioButton selectedButton;
	private Button submitButton;
	
	/** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState)
    {    	  
        super.onCreate(savedInstanceState);
        setContentView(R.layout.image_group);
        
        //call method to handle button press
        addListenerOnButton();
    }
        
    public void addListenerOnButton()
    {
    	//define radio group and submit button
    	imageGroup = (RadioGroup)findViewById(R.id.imageGroup);
    	submitButton = (Button)findViewById(R.id.submitButton);
    	
    	submitButton.setOnClickListener (new OnClickListener()
    	{
    		public void onClick(View v)
    		{
    			//get radio button selected by user
		    	int selectedId = imageGroup.getCheckedRadioButtonId();
		    	selectedButton = (RadioButton)findViewById(selectedId);
		    	
		    	//create toast as confirmation notice to user
		    	Toast.makeText(ImageGroupActivity.this,
		    			selectedButton.getText(), Toast.LENGTH_SHORT).show();
		    	
		    	//move to next screen
		    	Intent testBed = new Intent(v.getContext(), TestbedAppActivity.class);
		    	startActivity(testBed);
		    	
    		}   //end onClick
    		
    	});  //end setOnClickListener
    	
    }   //end addListenerOnButton


}   //end class