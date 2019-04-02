package org.eclipse.wb.swt;


//START: Capturing Cam Picture Part
import java.awt.image.BufferedImage;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.imageio.ImageIO;

import com.github.sarxos.webcam.Webcam;
//END: Capturing Cam Picture Part

public class CapturePicture extends Thread {
	
	public int Seconds=0;
	private boolean running = true;
	public void run(){
		try
		{
			while(running)
			{			
			TakePicture();
			sleep(Seconds*1000);
			}
		}
		catch(Exception ex)
		{
			
		}
		}

	private void TakePicture()
	{
		try
		{
			SimpleDateFormat simpleDateFormat = new SimpleDateFormat("YYYY-MM-dd-hh-mm-ss");
	    	String CapturedDateTime = simpleDateFormat.format(new Date());
			Webcam webcam = Webcam.getDefault(); // to open the defaulted camera 
			webcam.open();
			BufferedImage image = webcam.getImage();
			ImageIO.write(image, "JPG", new File("E:/screen/Cam-"+ CapturedDateTime +".jpg"));
			webcam.close();	
			
		}
		catch(Exception ex)
		{
			
		}
	}
public void Shutdown()
{
	running=false;
}
}
 