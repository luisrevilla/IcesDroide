/*
 * Created by Luis Revilla on 06.07.2012
 * mail: luis.revilla@gmail.com
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.ices.IcesDroide;
 
import android.app.Activity;
import android.os.Bundle;
import android.content.Intent;
import android.view.Window;
import android.view.View; 
import android.view.View.OnClickListener; 
import android.widget.Button;
import android.widget.TabHost;
import android.content.res.Resources; 
import android.content.pm.ActivityInfo;
import android.widget.TabHost.OnTabChangeListener; 
import android.widget.EditText;
import android.content.Context;
import android.content.SharedPreferences;
import android.widget.RadioGroup;
import android.widget.RadioButton;
import android.widget.Toast;
import android.net.Uri;


public class ControlMenuA extends Activity {    
    private Button botonA1=null;
    private Button botonC1=null;
    private Button botonC2=null;    
	SharedPreferences prefsIces;
	String servidorices;	
	String servidorportices;
	String servidormontajeices;    
	String servidoruserices;
	String servidorpasswdices;
	String servidorMp3Ogg;
	String servidorPathFileAudio;
	EditText edittextA;
	String textoedittextA;
	EditText edittextB;
	String textoedittextB;
	EditText edittextC;
	String textoedittextC;
	EditText edittextD;
	String textoedittextD;	
	EditText edittextE;
	String textoedittextE;
	EditText edittextF;
	String textoedittextF;	
	EditText edittextG;
	String textoedittextG;		
	int defaultMp3Ogg=0;	
	SharedPreferences.Editor editorIces;
	private Context ContextoPrincipal;
        
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menuservidor);        
        ContextoPrincipal=this;        
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            String parametro = extras.getString(IcesDroide.MiClaveA);        
        }
	prefsIces = getSharedPreferences(IcesDroide.demoPreferenciasIcesDroide,Context.MODE_PRIVATE);
	servidorices = prefsIces.getString(IcesDroide.demopreficeshtservidorices,IcesDroide.demoservername);	
	servidorportices = prefsIces.getString(IcesDroide.demopreficeshtservidorportices,IcesDroide.demoserverport);
	servidormontajeices = prefsIces.getString(IcesDroide.demopreficeshtservidormontajeices,IcesDroide.demoservermontaje);
	servidoruserices = prefsIces.getString(IcesDroide.demopreficeshtservidoruserices,IcesDroide.demoserveruser);
	servidorpasswdices = prefsIces.getString(IcesDroide.demopreficeshtservidorpasswdices,IcesDroide.demoserverpasswd);
	servidorMp3Ogg = prefsIces.getString(IcesDroide.demopreficeshtservidormp3oggices,IcesDroide.demoservermp3oggices);
	servidorPathFileAudio = prefsIces.getString(IcesDroide.demopreficeshtserverpathfilices,IcesDroide.demoserverpathfile);		
	editorIces = prefsIces.edit();   	        
	edittextA = (EditText)findViewById(R.id.edittext_A);
	textoedittextA = edittextA.getText().toString();
	edittextA.setText(servidorices);
	edittextB = (EditText)findViewById(R.id.edittext_B);
	textoedittextB = edittextB.getText().toString();
	edittextB.setText(servidorportices);            
	edittextC = (EditText)findViewById(R.id.edittext_C);
	textoedittextC = edittextC.getText().toString();
	edittextC.setText(servidormontajeices);  
	edittextD = (EditText)findViewById(R.id.edittext_D);
	textoedittextD = edittextD.getText().toString();
	edittextD.setText(servidoruserices);  
	edittextE = (EditText)findViewById(R.id.edittext_E);
	textoedittextE = edittextE.getText().toString();
	edittextE.setText(servidorpasswdices);  
	edittextG = (EditText)findViewById(R.id.edittext_G);
	textoedittextG = edittextG.getText().toString();
	edittextG.setText(servidorPathFileAudio); 	   
		botonA1 = (Button) findViewById(R.id.boton_A1);         
		botonA1.setOnClickListener(new View.OnClickListener() 
         {
             public void onClick(View v) 
             {
                Bundle bundleE = new Bundle();
                //bundleE.putString(IcesDroide.MiClaveA,"Informacio que le estos enviando a la siguiente vista FileExplorer..");
                Intent myIntentE = new Intent(ContextoPrincipal,FileExplorer.class);
                myIntentE.putExtras(bundleE);
                startActivityForResult(myIntentE, 1);                 
             }
         }); 
        botonC1 = (Button) findViewById(R.id.boton_C1);
        botonC1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                 Bundle bundle = new Bundle();
                    //bundle.putString(IcesDroide.MiClaveA, "Enviando parametros a la vista que la activo, cancelar");
                    Intent mIntent = new Intent();
                    mIntent.putExtras(bundle);                     
					if (getParent() == null) {
						setResult(Activity.RESULT_OK, mIntent);
					} else {
						getParent().setResult(Activity.RESULT_OK, mIntent);
					}                                        
                    finish();
            }
        });        
        botonC2 = (Button) findViewById(R.id.boton_C2);
        botonC2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
				textoedittextA = edittextA.getText().toString();
				if(textoedittextA.length()>0)
				{										
					editorIces.putString("servidorices",textoedittextA);
					editorIces.commit();					
				}
				textoedittextB = edittextB.getText().toString();
				if(textoedittextB.length()>0)
				{						
                    int  puertocastB=0;
                    try {
                        puertocastB = Integer.parseInt(textoedittextB);
                    } catch (NumberFormatException exception) {
						textoedittextB="8000";
                    }																			
					editorIces.putString(IcesDroide.demopreficeshtservidorportices,textoedittextB);
					editorIces.commit();					
				}				
				textoedittextC = edittextC.getText().toString();
				if(textoedittextC.length()>0)
				{										
					editorIces.putString(IcesDroide.demopreficeshtservidormontajeices,textoedittextC);
					editorIces.commit();					
				}				
				textoedittextD = edittextD.getText().toString();
				if(textoedittextD.length()>0)
				{										
					editorIces.putString(IcesDroide.demopreficeshtservidoruserices,textoedittextD);
					editorIces.commit();					
				}								
				textoedittextE = edittextE.getText().toString();
				if(textoedittextE.length()>0)
				{										
					editorIces.putString(IcesDroide.demopreficeshtservidorpasswdices,textoedittextE);
					editorIces.commit();					
				}	
				textoedittextG = edittextG.getText().toString();				
				if(textoedittextG.length()>0)
				{										
					editorIces.putString(IcesDroide.demopreficeshtserverpathfilices,textoedittextG);
					editorIces.commit();					
				}					
				if(defaultMp3Ogg==0)
				{
					editorIces.putString(IcesDroide.demopreficeshtservidormp3oggices,"0");
					editorIces.commit();					
				}else
				{
					editorIces.putString(IcesDroide.demopreficeshtservidormp3oggices,"1");
					editorIces.commit();										
				}	
                 Bundle bundle = new Bundle();
                    //bundle.putString(IcesDroide.MiClaveA, "Enviando parametros a la vista que la activo, aplicar");
                    Intent mIntent = new Intent();
                    mIntent.putExtras(bundle);                     
					if (getParent() == null) {
						setResult(Activity.RESULT_OK, mIntent);
					} else {
						getParent().setResult(Activity.RESULT_OK, mIntent);
					}                                        
                    finish();
            }
        });        
		RadioGroup rg = (RadioGroup)findViewById(R.id.gruporb); 
		final RadioButton radioMp3 = (RadioButton) findViewById(R.id.radioA);
		final RadioButton radioOgg = (RadioButton) findViewById(R.id.radioB);		
        defaultMp3Ogg=0;
        try {
			 defaultMp3Ogg = Integer.parseInt(servidorMp3Ogg);
            } catch (NumberFormatException exception) {
			 defaultMp3Ogg=0;
            }		
		if(defaultMp3Ogg==0){
			radioMp3.setChecked(true);
			radioOgg.setChecked(false);
		}else{
			radioMp3.setChecked(false);
			radioOgg.setChecked(true);			
		}				
		rg.setOnCheckedChangeListener(
			new RadioGroup.OnCheckedChangeListener() {
        public void onCheckedChanged(RadioGroup group, int checkedId) {
            if (radioMp3.getId() == checkedId) 
            {
				defaultMp3Ogg=0;				
			}
            if (radioOgg.getId() == checkedId) 
            {
				defaultMp3Ogg=1;
			}			
        }
});	                           
    }
protected void onActivityResult(int requestCode,int resultCode,Intent outputIntent)
{ 
   super.onActivityResult(requestCode, resultCode, outputIntent);
   parseResult(this, requestCode, resultCode, outputIntent);
}
public void parseResult(ControlMenuA activity, int requestCode , int resultCode , Intent outputIntent)
{
   if (requestCode != 1){return;}
   if (resultCode != Activity.RESULT_OK){return;} 
        Bundle extrasB = outputIntent.getExtras();
        if (extrasB != null) {
            String parametroB = extrasB.getString(IcesDroide.MiClaveA);                    
            servidorPathFileAudio = prefsIces.getString(IcesDroide.demopreficeshtserverpathfilices, IcesDroide.demoserverpathfile);
            edittextG.setText(servidorPathFileAudio);
        }      
}    
}
