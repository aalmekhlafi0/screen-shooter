import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;

import java.awt.KeyboardFocusManager;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.FileReader;

import org.eclipse.wb.swt.CapturePicture;
import org.eclipse.wb.swt.CaptureScreenShot;
import org.eclipse.wb.swt.KeyInputListener;
import org.eclipse.wb.swt.SWTResourceManager;
import org.eclipse.wb.swt.SendEmail;
import org.eclipse.swt.events.ShellAdapter;
import org.eclipse.swt.events.ShellEvent;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.widgets.Text;

public class MainScreen {

	protected Shell shlScreenshotApplication;

	/**
	 * Launch the application.
	 * @param args
	 */
	//Start: AppSettings Variables
	String Seconds=""; 
	String Email ="";
	//End: AppSettings Variables
	
	//Start: Camera Capturing Class
	CapturePicture StartCapt;
	//End: Camera Capturing Class
	
	//Start: ScreenShot Class
	CaptureScreenShot CSS;
	KeyInputListener keyboardLister;
	private Text txt_Status;
	//End: ScreenShot Class
	
	
	
	public static void main(String[] args) {
		try {
					    
			MainScreen window = new MainScreen();
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
		shlScreenshotApplication.open();
		shlScreenshotApplication.layout();
		while (!shlScreenshotApplication.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
	}

	/**
	 * Create contents of the window.
	 */
	protected void createContents() {
		shlScreenshotApplication = new Shell();
		shlScreenshotApplication.addShellListener(new ShellAdapter() {
			@Override
			public void shellActivated(ShellEvent e) {
				
			}
			@Override
			public void shellClosed(ShellEvent e) {
				//CSS.destroy();
				//StartCapt.destroy();
			}
		});
		shlScreenshotApplication.setSize(599, 325);
		shlScreenshotApplication.setText("ScreenShot Application");
		
		ScrolledComposite scrolledComposite = new ScrolledComposite(shlScreenshotApplication, SWT.BORDER | SWT.H_SCROLL | SWT.V_SCROLL);
		scrolledComposite.setBounds(52, 133, 500, 143);
		scrolledComposite.setExpandHorizontal(true);
		scrolledComposite.setExpandVertical(true);
		
		txt_Status = new Text(scrolledComposite, SWT.BORDER | SWT.READ_ONLY | SWT.WRAP | SWT.V_SCROLL);
		txt_Status.setFont(SWTResourceManager.getFont("Segoe UI", 9, SWT.ITALIC));
		txt_Status.setForeground(SWTResourceManager.getColor(SWT.COLOR_RED));
		txt_Status.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		scrolledComposite.setContent(txt_Status);
		scrolledComposite.setMinSize(txt_Status.computeSize(SWT.DEFAULT, SWT.DEFAULT));
		
		Button btnNewButton = new Button(shlScreenshotApplication, SWT.NONE);
		btnNewButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				//Open AppSettings Winow
				AppSettingsWindow window = new AppSettingsWindow();
				window.open();
			}
		});
		btnNewButton.setBounds(52, 10, 157, 45);
		btnNewButton.setText("Application Settings");
		
		Button btnNewButton_1 = new Button(shlScreenshotApplication, SWT.NONE);
		btnNewButton_1.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				//Open Search Window
			SearchImages window = new SearchImages();
			window.open();
			}
		});
		btnNewButton_1.setBounds(219, 10, 157, 45);
		btnNewButton_1.setText("Search Image");
		
		Button btn_ScreenShot = new Button(shlScreenshotApplication, SWT.NONE);
		btn_ScreenShot.setBounds(52, 71, 157, 45);
		btn_ScreenShot.setText("Start Screen Shotting");
		
		Button btn_LaptopCam = new Button(shlScreenshotApplication, SWT.NONE);
		btn_LaptopCam.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				//Call Camera Capturing Class
				try
				{
					if(btn_LaptopCam.getText() == "Activate Laptop Camera")
					{
						StartCapt= new CapturePicture();
						StartCapt.Seconds= Integer.parseInt(Seconds);
						StartCapt.start();
						btn_LaptopCam.setText("Stop Laptop Camera");
						txt_Status.setText(txt_Status.getText()+"Start Camera Capturing every " +Seconds+ " Seconds..\n");
						keyboardLister = new KeyInputListener();
						keyboardLister.start();						
					}
					else
					{
						StartCapt.Shutdown();
						StartCapt.stop();
						keyboardLister.Shutdown();
						keyboardLister.stop();
						btn_LaptopCam.setText("Activate Laptop Camera");
						txt_Status.setText(txt_Status.getText()+"Stop Camera Capturing..\n");
					}
				}
				catch(Exception ex) {
					txt_Status.setText(txt_Status.getText()+ ex +"..\n");					
				}
			}
		});
		btn_LaptopCam.setBounds(386, 71, 166, 45);
		btn_LaptopCam.setText("Activate Laptop Camera");
		btn_ScreenShot.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				try
				{
					if(btn_ScreenShot.getText()=="Start Screen Shotting")
					{
						CSS= new CaptureScreenShot();
					CSS.Seconds=Integer.parseInt(Seconds);
					CSS.start();
					txt_Status.setText(txt_Status.getText()+"Start Screenshots every " +Seconds+ " Seconds..\n");
					btn_ScreenShot.setText("Stop Screen Shot");
					
					
					}
					else
					{
						CSS.Shutdown();
						CSS.stop();
						
						btn_ScreenShot.setText("Start Screen Shotting");
						txt_Status.setText(txt_Status.getText()+"ScreenShoting Stopped..\n");
						
					}
				}
				catch(Exception ex)
				{
					
				}
			}
		});
		
		//After creating Contents Start Reading App Settings and Check Disk Space
		String Status="";
		Status = ReadAppSettings(); //Read App Settings
		txt_Status.setText(txt_Status.getText()+ Status + "\n");
		Status = CheckDiskSpace();
		txt_Status.setText(txt_Status.getText()+ Status + "\n");
		
		Button btn_startText = new Button(shlScreenshotApplication, SWT.NONE);
		btn_startText.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				
				try
				{
					if(btn_startText.getText()=="Start Keyboard Detecting")
					{
						
					txt_Status.setText(txt_Status.getText()+"Start Keyboard Detecting.\n");
					btn_startText.setText("Stop Keyboard Detecting");
					keyboardLister = new KeyInputListener();
					keyboardLister.start();
					
					}
					else
					{
						
						keyboardLister.Shutdown();
						keyboardLister.stop();
						btn_startText.setText("Start Keyboard Detecting");
						txt_Status.setText(txt_Status.getText()+"Keyboard Detecting Stopped..\n");
						
					}
				}
				catch(Exception ex)
				{
					
				}
			}
		});
		btn_startText.setText("Start Keyboard Detecting");
		btn_startText.setBounds(219, 71, 157, 45);
		
		Button btn_searchText = new Button(shlScreenshotApplication, SWT.NONE);
		btn_searchText.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				SearchText searchWindow = new SearchText();
				searchWindow.open();
			}
		});
		btn_searchText.setText("Search Text");
		btn_searchText.setBounds(386, 10, 166, 45);
	}
	
	private String ReadAppSettings()
	{
		String Status ="";
		try
		{			
			//Get the number of seconds to screenshot
			File configFile = new File("config.properties"); //call config file
			FileReader reader = new FileReader(configFile); //to do the object reader 
			 java.util.Properties props = new java.util.Properties();				 
			 props.load(reader); //to put the values in the props object
			 
			 Seconds = props.getProperty("NoOfSecends");
			 Email=props.getProperty("EmailAdd");
			 Status = "Application Settings have been read successfully....\n";
		}
		catch(Exception ex)
		{
			Status=ex.getMessage()+"\n";
		}
		return Status;
	}

	private String CheckDiskSpace()
	{
		String Status="";
		try
		{
		File EDrive = new File("E:");
		long freeSpace = EDrive.getFreeSpace();// built-in function
		System.out.println("Free Space:" + freeSpace/(1024*1024) +" MB.");
		Status = "Free Disk Space:" + freeSpace/(1024*1024) +" MB. \n";
		if(freeSpace <= 10000) //Less than 1GB
		{
			SendEmail sendMail = new SendEmail();//Class that sends email 
			sendMail.toEmail=Email; //get the email from the app sittings
			sendMail.SendMailProcess();
			Status=Status + "Warning Email Sent successfully....\n";
		}
	
	}   
	catch(Exception ex)
		{
		Status=ex.getMessage()+"\n";
		}
		return Status;
	}
}
