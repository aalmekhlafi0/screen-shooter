import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Label;

import java.awt.KeyboardFocusManager;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.Properties;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Text;
import org.eclipse.wb.swt.KeyInputListener;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.DisposeListener;
import org.eclipse.swt.events.DisposeEvent;

public class AppSettingsWindow {

	protected Shell shlApplicationSettings;
	private Text txt_Seconds;
	private Text txt_Email;
	private Button btnNewButton;

	/**
	 * Launch the application.
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			AppSettingsWindow window = new AppSettingsWindow();
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
		shlApplicationSettings.open();
		shlApplicationSettings.layout();
		while (!shlApplicationSettings.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
	}

	/**
	 * Create contents of the window.
	 */
	protected void createContents() {
		shlApplicationSettings = new Shell();
		shlApplicationSettings.addDisposeListener(new DisposeListener() {
			public void widgetDisposed(DisposeEvent arg0) {
			
			}
		});
		shlApplicationSettings.setSize(374, 188);
		shlApplicationSettings.setText("Application Settings");
		
		Label lblNewLabel = new Label(shlApplicationSettings, SWT.WRAP);
		lblNewLabel.setBounds(21, 24, 134, 30);
		lblNewLabel.setText("Number of Seconds to Screenshot:");
		
		txt_Seconds = new Text(shlApplicationSettings, SWT.BORDER);
		txt_Seconds.setToolTipText("Enter Numbers only");
		txt_Seconds.setBounds(161, 21, 187, 21);
		
		Label lblEmailAddress = new Label(shlApplicationSettings, SWT.NONE);
		lblEmailAddress.setBounds(21, 75, 117, 21);
		lblEmailAddress.setText("Email Address:");
		
		Button btnSave = new Button(shlApplicationSettings, SWT.NONE);
		btnSave.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				try
				{
					File configFile = new File("config.properties");
					FileWriter writer = new FileWriter(configFile);
					Properties props = new Properties();
					props.setProperty("NoOfSecends", txt_Seconds.getText());
					props.setProperty("EmailAdd", txt_Email.getText());
					props.store(writer, "");
					writer.close();
					
					shlApplicationSettings.close();
				}
				catch(Exception ex)
				{}
			}
				
		});
		btnSave.setBounds(68, 114, 109, 25);
		btnSave.setText("Save");
		
		txt_Email = new Text(shlApplicationSettings, SWT.BORDER);
		txt_Email.setBounds(161, 72, 187, 21);
		
		File configFile = new File("config.properties");
		try
		{//
			 FileReader reader = new FileReader(configFile);
			 java.util.Properties props = new java.util.Properties();				 
			 props.load(reader);
			 
			 String Seconds = props.getProperty("NoOfSecends");
			 txt_Seconds.setText(Seconds);
			 String email = props.getProperty("EmailAdd");
			 txt_Email.setText(email);
			 
			 btnNewButton = new Button(shlApplicationSettings, SWT.NONE);
			 btnNewButton.addSelectionListener(new SelectionAdapter() {
			 	@Override
			 	public void widgetSelected(SelectionEvent e) {
			 		shlApplicationSettings.close();
			 	}
			 });
			 btnNewButton.setBounds(197, 114, 109, 25);
			 btnNewButton.setText("Cancel");
			 
			 reader.close();
		}
		catch(Exception ex)
		{
		}
		

	}
}
