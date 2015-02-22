/*
 * Created by Luis Revilla on 07.14.2012
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
import android.view.View; 
import android.view.View.OnClickListener; 
import android.widget.Button;


import android.content.Context;

public class About extends Activity {    
    private Button botonA1=null;
    private Button botonA2=null;
    private Button botonC1=null;
    private Button botonC2=null;
	private Context ContextoPrincipal;        
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.acerca);        
        ContextoPrincipal=this;       
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            String parametro = extras.getString(IcesDroide.MiClaveA);        
        }              
        botonA1 = (Button) findViewById(R.id.boton_A1);
        botonA1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                 Bundle bundle = new Bundle();
                    //bundle.putString(IcesDroide.MiClaveA, "Enviando parametros a la vista que la activo, cancelar.");
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
protected void onActivityResult(int requestCode,int resultCode,Intent outputIntent)
{ 
   super.onActivityResult(requestCode, resultCode, outputIntent);parseResult(this, requestCode, resultCode, outputIntent);
}
public void parseResult(About activity, int requestCode , int resultCode , Intent outputIntent)
{
   if (requestCode != 1){return;}
   if (resultCode != Activity.RESULT_OK){return;}    
        Bundle extrasB = outputIntent.getExtras();
        if (extrasB != null) {String parametroB = extrasB.getString(IcesDroide.MiClaveA);}      
}    
}
