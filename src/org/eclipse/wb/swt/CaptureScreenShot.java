package org.eclipse.wb.swt;

import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.imageio.ImageIO;


public class CaptureScreenShot extends Thread   {
	
	public int Seconds=0;
	private boolean running = true;
	
public void run()
{
	
		try
		{
			while(running)
			{			
			TakeScreenShot();
			sleep(Seconds*1000);
			}
		}
		catch(Exception ex)
		{
			
		}
	
}
private void TakeScreenShot()
{
	try
	{
		 Robot robot = new Robot();
		 SimpleDateFormat simpleDateFormat = new SimpleDateFormat("YYYY-MM-dd-hh-mm-ss");
		 String dateAsString1 = simpleDateFormat.format(new Date());
		 BufferedImage bi=robot.createScreenCapture(new Rectangle(Toolkit.getDefaultToolkit().getScreenSize()));		 
         ImageIO.write(bi, "jpg", new File("E:/screen/img"+dateAsString1+".jpg"));
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
