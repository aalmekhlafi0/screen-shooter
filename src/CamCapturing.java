import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

//START: Capturing Cam Picture Part
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.imageio.ImageIO;

import com.github.sarxos.webcam.Webcam;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.widgets.Text;
import org.jnativehook.GlobalScreen;
import org.eclipse.swt.widgets.Label;
//END: Capturing Cam Picture Part

public class CamCapturing {

	protected Shell shlCameraCapturing;
	private Text text;

	/**
	 * Launch the application.
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			CamCapturing window = new CamCapturing();
			window.open();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Open the window.
	 */
	public void open() {
		Display display = Display.getDefault();
		createContents();
		shlCameraCapturing.open();
		shlCameraCapturing.layout();
		while (!shlCameraCapturing.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
	}

	/**
	 * Create contents of the window.
	 */
	protected void createContents() {
		shlCameraCapturing = new Shell();
		shlCameraCapturing.setSize(387, 267);
		shlCameraCapturing.setText("Camera Capturing");
		
			
		Button btnCapture = new Button(shlCameraCapturing, SWT.NONE);
		btnCapture.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				try
				{
				TakeImage();
									
				TakeImage();
					
						
				}
				catch(Exception ex)
				{}
			}
		});
		btnCapture.setBounds(62, 27, 126, 25);
		btnCapture.setText("Start Capturing");
		
		Label lblNewLabel = new Label(shlCameraCapturing, SWT.NONE);
		lblNewLabel.setBounds(198, 72, 96, 81);
		lblNewLabel.setText("New Label");
		
		text = new Text(shlCameraCapturing, SWT.BORDER);
		text.setBounds(22, 69, 166, 109);


	}	

protected void TakeImage()
{
	Webcam webcam;
	try
	{
		SimpleDateFormat simpleDateFormat;
		String CapturedDateTime;
		webcam = Webcam.getDefault();
		webcam.open();
		BufferedImage image;
		simpleDateFormat = new SimpleDateFormat("YYYY-MM-dd-hh-mm-ss");
		CapturedDateTime = simpleDateFormat.format(new Date());				
		image = webcam.getImage();
		ImageIO.write(image, "JPG", new File("E:/screen/Cam-"+CapturedDateTime +".jpg"));				
		webcam.close();
	}
	catch(Exception ex) {
		String error = ex.getMessage();					
	}
}
}
