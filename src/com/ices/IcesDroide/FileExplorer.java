/*
 * Created by Luis Revilla on 06.18.2012
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
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.DialogInterface;
import android.os.Bundle;
import android.content.Intent;
import android.util.Log; 
import android.app.Activity;
import android.view.Window;
import android.view.View;
import android.widget.Button;
import android.content.res.Resources; 
import android.content.pm.ActivityInfo;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.content.Context;
import android.content.SharedPreferences;

public class FileExplorer extends ListActivity {
 private List<String> item = null;
 private List<String> path = null;
 private TextView myPath;
 SharedPreferences prefsIces;
 SharedPreferences.Editor editorIces;
 String servidoricesFileBrwPath;
 private Button botonF1=null;
 private String absolutepatharchivo=null;
 private String pathpadrearchivo=null;
 
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.mainfilexplorer);        
        prefsIces = getSharedPreferences(IcesDroide.demoPreferenciasIcesDroide,Context.MODE_PRIVATE);
        servidoricesFileBrwPath= prefsIces.getString(IcesDroide.demopreficeshtservidorpathices, "/");	
        editorIces = prefsIces.edit();          
        myPath = (TextView)findViewById(R.id.path);
        getDir(servidoricesFileBrwPath);                   
        botonF1 = (Button) findViewById(R.id.boton_F1);
        botonF1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {				
				if(absolutepatharchivo==null)
				{}else
				{
					editorIces.putString(IcesDroide.demopreficeshtserverpathfilices,absolutepatharchivo);
					editorIces.commit();	
					absolutepatharchivo=null;				
				}				
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
    }
  
    private void getDir(String dirPath)
    {
     myPath.setText(getString(R.string.ubicacion)+dirPath); 
     myPath.setTextColor(0xffffffff); 
     myPath.setBackgroundColor(0xff555555);  
     item = new ArrayList<String>();
     path = new ArrayList<String>();     
     File f = new File(dirPath);
     File[] files = f.listFiles();
     File file;
     int i;
     String ComparaPathExt;
     if(!dirPath.equals(servidoricesFileBrwPath))
     {
      item.add("../");
      path.add(f.getParent());           
     }
     for(i=0; i < files.length; i++)
     {
       file = files[i];
       if(file.isDirectory())
       {       
        path.add(file.getPath());
        item.add(file.getName() + "/");
       }
     }
     for(i=0; i < files.length; i++)
     {
       file = files[i];
       if(file.isDirectory())
       {}
       else
       {
		ComparaPathExt=file.getName().toLowerCase();
		
		   if( ComparaPathExt.endsWith(".mp3")||ComparaPathExt.endsWith(".ogg")||ComparaPathExt.endsWith(".oga")  )
		   {
			   path.add(file.getPath());		   
			   item.add(file.getName());  			   
		   }
       }
     }     
     ArrayAdapter<String> fileList =
     new ArrayAdapter<String>(this, R.layout.row, item);
     setListAdapter(fileList);
    }
 @Override
 protected void onListItemClick(ListView l, View v, int position, long id) 
 {
  File file = new File(path.get(position)); 
  if (file.isDirectory())
  {
   if(file.canRead())
    getDir(path.get(position));
   else
   {
    new AlertDialog.Builder(this)
    .setIcon(R.drawable.icon)
    .setTitle(file.getName() +" | "+ getString(R.string.noleer))
    .setPositiveButton(getString(R.string.aceptar), 
    new DialogInterface.OnClickListener() {
      
  @Override
  public void onClick(DialogInterface dialog, int which) 
  {}
  }).show();
   }
  }
  else
  {
	absolutepatharchivo=file.getAbsolutePath();  
	pathpadrearchivo=file.getParent();
   new AlertDialog.Builder(this)
    .setIcon(R.drawable.icon)
    .setTitle(file.getName())
    .setPositiveButton(getString(R.string.aceptar), 
      new DialogInterface.OnClickListener() {     
       @Override

       public void onClick(DialogInterface dialog, int which) 
       {
       }
      }).show();
  }
 }
}
