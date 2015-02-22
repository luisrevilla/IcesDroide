/*
 * Created by Luis Revilla on 07.12.2012
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
import android.widget.TextView;
import android.os.Bundle;
import android.content.Intent;
import android.content.Context;
import android.app.Activity; 
import android.os.Bundle; 
import android.os.Environment; 
import android.view.View; 
import android.view.View.OnClickListener; 
import android.widget.Button; 
import android.widget.ImageButton;
import android.widget.TextView;
import android.content.SharedPreferences;
import android.os.Handler;
import android.widget.RelativeLayout;
import android.view.ViewGroup.LayoutParams;

public class IcesDroide extends Activity
{
	private Button buttonA=null;
	private int buttonA_Activo=0;
	private Button imagebuttonA=null;
	private ImageButton imagebuttonB=null;
	private int imagebuttonG_Activo=0;
	private int imagebuttonH_Activo=0;	
	final public static String MiClaveA = "1q2w3e4r5r";	
	private Context ContextoPrincipal;	
	private SharedPreferences prefsIces;	
	public String StringPathDirStoreExterno=null;
	public String StringPathDirStoreInterno=null;		
	SharedPreferences.Editor editorIces;	
	TextView textoPrincipal=null;
	String servidorices=null;
	String servidorportices=null;
	String servidormontajeices=null;
	String servidoruserices=null;
	String servidorpasswdices=null;
	String servidorMp3Ogg=null;
	String servidorPathFileAudio=null;
    int  puertocastB= 8000;
	int  Mp3OggB= 0;
	String Mp3OggVorbis=null;	
	final public static String demoservername = "www.example-serverstreaming.com";	
	final public static String demoserverport = "8000";	
	final public static String demoservermontaje = "/montaje.mp3";	
	final public static String demoserveruser = "source";	
	final public static String demoserverpasswd = "12345";	
	final public static String demoservermp3oggices = "0";	
	final public static String demoserverpathfile = "/mnt/sdcad/path/audio.mp3";	
	final public static String demoPreferenciasIcesDroide = "PreferenciasIcesDroide";		
	final public static String demopreficeshtserverpathfilices = "servidorpathfileices";	
	final public static String demopreficeshtservidorices = "servidorices";	
	final public static String demopreficeshtservidorportices = "servidorportices";	
	final public static String demopreficeshtservidormontajeices = "servidormontajeices";	
	final public static String demopreficeshtservidoruserices = "servidoruserices";	
	final public static String demopreficeshtservidorpasswdices = "servidorpasswdices";	
	final public static String demopreficeshtservidormp3oggices = "servidormp3oggices";		
	final public static String demopreficeshtservidorpathices = "/";		
	final Handler VerificHandler=new Handler();	
	
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        System.loadLibrary("icesshout");
        setContentView(R.layout.main);      
        prefsIces = getSharedPreferences(demoPreferenciasIcesDroide,Context.MODE_PRIVATE);
        ContextoPrincipal=this;                      
        
        
        editorIces = prefsIces.edit();    
        InitThread();
        textoPrincipal = (TextView) findViewById(R.id.texto_A); 
        UpdateTextoPrincipal(0);
        imagebuttonA = (Button) findViewById(R.id.imagebutton_A);         
        imagebuttonA.setOnClickListener(new View.OnClickListener() 
         {
             public void onClick(View v) 
             {
                 if(imagebuttonG_Activo==0)
                 {
					servidorPathFileAudio  = prefsIces.getString(demopreficeshtserverpathfilices,demoserverpathfile); 
					if(LoadFile(servidorPathFileAudio)==1)
					{ 
						servidorices = prefsIces.getString(demopreficeshtservidorices,demoservername);	
						servidorportices = prefsIces.getString(demopreficeshtservidorportices,demoserverport);
						servidormontajeices = prefsIces.getString(demopreficeshtservidormontajeices,demoservermontaje);
						servidoruserices = prefsIces.getString(demopreficeshtservidoruserices,demoserveruser);
						servidorpasswdices = prefsIces.getString(demopreficeshtservidorpasswdices,demoserverpasswd);	
						servidorMp3Ogg = prefsIces.getString(demopreficeshtservidormp3oggices,demoservermp3oggices);															
						puertocastB= 8000;
						try {
                         puertocastB = Integer.parseInt(servidorportices);
						} catch (NumberFormatException exception) {
						puertocastB=8000;
						}					
						Mp3OggB= 0;
						try {
                         Mp3OggB = Integer.parseInt(servidorMp3Ogg);
						} catch (NumberFormatException exception) {
						 Mp3OggB =0;
						}									
						if(InitIcecast(servidorices,puertocastB,servidormontajeices,servidoruserices,servidorpasswdices,Mp3OggB)>0)
						{					
						  imagebuttonA.setText(R.string.desconectar);
						  imagebuttonG_Activo=1;
						  ReadStreaming(); 			
						}else
						{
						  UpdateTextoPrincipal(2);
						}						
					}else
					{
						UpdateTextoPrincipal(1);
					}
				 }else
                 if(imagebuttonG_Activo==1)
                 {              
					imagebuttonA.setText(R.string.conectar);
					imagebuttonG_Activo=0;
					StopStreaming(); 
				 }                                                   
             }
         });        
         imagebuttonB = (ImageButton) findViewById(R.id.imagebutton_B);         
         imagebuttonB.setOnClickListener(new View.OnClickListener() 
         {
             public void onClick(View v) 
             {               
                Bundle bundle = new Bundle();
                //bundle.putString(MiClaveA,"Informacio que le estos enviando a la siguiente vista data.A..");
                Intent myIntent = new Intent(v.getContext(),ControlMenuA.class);
                myIntent.putExtras(bundle);
                startActivityForResult(myIntent, 1);
             }
         });  
         buttonA = (Button) findViewById(R.id.button_A);         
         buttonA.setOnClickListener(new View.OnClickListener() 
         {
             public void onClick(View v) 
             {                
                Bundle bundle = new Bundle();
                //bundle.putString(MiClaveA,"Informacio que le estos enviando a la siguiente vista data.A..");
                Intent myIntent = new Intent(v.getContext(),About.class);
                myIntent.putExtras(bundle);
                startActivityForResult(myIntent, 1);
             }
         });                           
    }

protected void onActivityResult(int requestCode,int resultCode,Intent outputIntent)
{ 
   super.onActivityResult(requestCode, resultCode, outputIntent);
   parseResult(this, requestCode, resultCode, outputIntent);

}

public void parseResult(IcesDroide activity, int requestCode , int resultCode , Intent outputIntent)
{
   if (requestCode != 1){
      return;
   }
   if (resultCode != Activity.RESULT_OK){
      return;
   }
        Bundle extrasB = outputIntent.getExtras();
        if (extrasB != null) {
            String parametroB = extrasB.getString(IcesDroide.MiClaveA);        
            UpdateTextoPrincipal(0);
        }       
}

private void UpdateTextoPrincipal(int ParmtrA)
{
	if(ParmtrA==0)
	{
	servidorices = prefsIces.getString(demopreficeshtservidorices,demoservername);	
	servidorportices = prefsIces.getString(demopreficeshtservidorportices,demoserverport);
	servidormontajeices = prefsIces.getString(demopreficeshtservidormontajeices,demoservermontaje);
	servidoruserices = prefsIces.getString(demopreficeshtservidoruserices,demoserveruser);
	servidorpasswdices = prefsIces.getString(demopreficeshtservidorpasswdices,demoserverpasswd);	
	servidorMp3Ogg = prefsIces.getString(demopreficeshtservidormp3oggices,demoservermp3oggices);
	servidorPathFileAudio  = prefsIces.getString(demopreficeshtserverpathfilices,demoserverpathfile);	
	Mp3OggB= 0;
    try {
      Mp3OggB = Integer.parseInt(servidorMp3Ogg);
    } catch (NumberFormatException exception) {
	Mp3OggB =0;
    }										
    if(Mp3OggB==0)
		Mp3OggVorbis="MP3";
	else
		Mp3OggVorbis="OGG Vorbis";
	textoPrincipal.setText(getString(R.string.estatus)+"\n"+getString(R.string.server)+servidorices+":"+servidorportices+servidormontajeices+"\n"+getString(R.string.usuario)+servidoruserices+" | "+getString(R.string.passwd)+servidorpasswdices+"\n"+getString(R.string.format)+Mp3OggVorbis+"\n"+getString(R.string.fila)+servidorPathFileAudio);
	}
	if(ParmtrA==1)
	{
		servidorPathFileAudio  = prefsIces.getString(demopreficeshtserverpathfilices,demoserverpathfile);			
		textoPrincipal.setText(getString(R.string.estatus)+"\n"+getString(R.string.filano)+servidorPathFileAudio);
	}	
	if(ParmtrA==2)
	{
		textoPrincipal.setText(getString(R.string.estatus)+"\n"+getString(R.string.serverror));
	}	
	if(ParmtrA==3)
	{
	textoPrincipal.setText(getString(R.string.estatus)+"\n"+getString(R.string.server)+servidorices+":"+servidorportices+servidormontajeices+"\n"+getString(R.string.usuario)+servidoruserices+" | "+getString(R.string.passwd)+servidorpasswdices+"\n"+getString(R.string.format)+Mp3OggVorbis+"\n"+getString(R.string.fila)+servidorPathFileAudio+"\n"+getString(R.string.playporcentj)+RetPorcetjPlay()+"%");
	}		
}

	public void RecibeEventA(String parameventA) 
	{ 
    } 
        
        
	final Runnable CicloVerifc=new Runnable(){
		public void run(){
        if(imagebuttonG_Activo==1)
        {              
			UpdateTextoPrincipal(3);
		} 				
        if( (imagebuttonG_Activo==1)&&(EventA()==1) )
        {              
			imagebuttonA.setText(R.string.conectar);
			imagebuttonG_Activo=0;			
		} 				
		}
	};

	protected void InitThread(){
		Thread VThread= new Thread(){		
		public void run() {
		while(true)
		{
			try{
				Thread.sleep(3000);
			}
			catch (InterruptedException e){
			}
			VerificHandler.post(CicloVerifc);			
		}
		}	
		};
		VThread.start();
	}
	
	
	@Override
	protected void onResume() {
		super.onResume();
	}

	@Override
	protected void onPause() {
		super.onPause();
	}

    @Override 
	protected void onStop() {
         super.onStop();

     }     

    @Override 
	protected void onRestart() {
         super.onRestart();

     } 

    @Override 
	protected void onStart() {
         super.onStart();

     } 

    @Override 
	protected void onDestroy() {
         super.onDestroy();

     } 	
	           
	public native int InitIcecast(String urlservercast,int puertocast,String montajecast,String usuariocast,String passwdcast,int Mp3OggB);
    public native int LoadFile(String pathfilamp3ogg);
    public native int ReadStreaming();
    public native int StopStreaming();
    public native int EventA();
    public native int RetPorcetjPlay();
}
