

import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

import com.ibm.icu.text.SimpleDateFormat;

import org.eclipse.swt.widgets.Label;

import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FilenameFilter;
import java.util.Calendar;
import java.util.Date;

import javax.imageio.ImageIO;
import javax.imageio.ImageTypeSpecifier;
import javax.swing.ImageIcon;
import javax.swing.text.html.ImageView;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.DateTime;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Image;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.ui.forms.widgets.Hyperlink;
import org.eclipse.wb.swt.SWTResourceManager;
import org.eclipse.ui.forms.widgets.ImageHyperlink;

public class SearchImages {

	protected Shell shlSearch;
	private final FormToolkit formToolkit = new FormToolkit(Display.getDefault());

	
	int position = 0;
	Label lbl_Images;
	File[] imagesList;

	 //Launch the application.
	 //param args
	
	public static void main(String[] args) {
		try {
			SearchImages window = new SearchImages();
			window.open();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// Open the window.
	
	public void open() {
		Display display = Display.getDefault();
		createContents();
		shlSearch.open();
		shlSearch.layout();
		while (!shlSearch.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
	}

	/**
	 * Create contents of the window.
	 */
	protected void createContents() {
		shlSearch = new Shell();
		shlSearch.setSize(706, 596);
		shlSearch.setText("Search");
		
		Label lbl_NoOfFiles = new Label(shlSearch, SWT.CENTER);
		lbl_NoOfFiles.setAlignment(SWT.CENTER);
		lbl_NoOfFiles.setFont(SWTResourceManager.getFont("Segoe UI", 10, SWT.BOLD));
		Label lblEnterDateAnd = new Label(shlSearch, SWT.NONE);
		lblEnterDateAnd.setBounds(31, 10, 148, 15);
		lblEnterDateAnd.setText("Enter Date and Time:");
		
		Label lblNewLabel = new Label(shlSearch, SWT.NONE);
		lblNewLabel.setBounds(31, 40, 78, 24);
		lblNewLabel.setText("Date:");
		
		DateTime dt_Date = new DateTime(shlSearch, SWT.BORDER | SWT.LONG);
		dt_Date.setBounds(115, 31, 235, 24);
		
		Label lblNewLabel_1 = new Label(shlSearch, SWT.NONE);
		lblNewLabel_1.setBounds(28, 76, 69, 15);
		lblNewLabel_1.setText("Time:");
		
		Combo Time_HR = new Combo(shlSearch, SWT.NONE);
		Time_HR.setItems(new String[] {"01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12"});
		Time_HR.setBounds(115, 68, 42, 23);
		Time_HR.select(0);
		
		Combo Time_Min = new Combo(shlSearch, SWT.NONE);
		Time_Min.setItems(new String[] {"00", "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31", "32", "33", "34", "35", "36", "37", "38", "39", "40", "41", "42", "43", "44", "45", "46", "47", "48", "49", "50", "51", "52", "53", "54", "55", "56", "57", "58", "59"});
		Time_Min.setBounds(173, 68, 42, 23);
		Time_Min.select(0);
		
		lbl_Images = new Label(shlSearch, SWT.NONE);
		lbl_Images.setBounds(10, 107, 650, 400);
		formToolkit.adapt(lbl_Images, true, true);
		
		Button btn_Next = new Button(shlSearch, SWT.NONE);
		
		Button btn_Previous = new Button(shlSearch, SWT.NONE);
		btn_Previous.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				position= position-1;
				
				ShowImage(position);lbl_NoOfFiles.setText((position+1)+"/"+imagesList.length);
				
				if (position== 0)
				{
					btn_Previous.setEnabled(false);
				}
				else
				{
					btn_Previous.setEnabled(true);
					btn_Next.setEnabled(true);
				}
				
			}
		});
		btn_Previous.setEnabled(false);
		btn_Previous.setBounds(206, 513, 128, 38);
		formToolkit.adapt(btn_Previous, true, true);
		btn_Previous.setText("< Previous");
		
		Combo com_ImgType = new Combo(shlSearch, SWT.NONE);
		com_ImgType.setItems(new String[] {"Screenshots", "Laptop photos"});
		com_ImgType.setBounds(368, 31, 159, 23);
		formToolkit.adapt(com_ImgType);
		formToolkit.paintBordersFor(com_ImgType);
		com_ImgType.select(0);
		
		
		btn_Next.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				position= position+1;
				if(position <= imagesList.length-1)
				{
					btn_Next.setEnabled(true);
					btn_Previous.setEnabled(true);
					ShowImage(position);
					lbl_NoOfFiles.setText((position+1)+"/"+imagesList.length);
				}
				else
				{
					position= position-1;
					btn_Next.setEnabled(false);
				}				
			}
		});
		btn_Next.setEnabled(false);
		btn_Next.setText("Next  >");
		btn_Next.setBounds(368, 513, 128, 38);
		formToolkit.adapt(btn_Next, true, true);
		
		Button btn_First = new Button(shlSearch, SWT.NONE);
		btn_First.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				position=0;
				btn_Previous.setEnabled(false);
				ShowImage(position);
				lbl_NoOfFiles.setText((position+1)+"/"+imagesList.length);
			}
		});
		btn_First.setText("<< First");
		btn_First.setEnabled(false);
		btn_First.setBounds(46, 513, 128, 38);
		formToolkit.adapt(btn_First, true, true);
		
		Button btn_Last = new Button(shlSearch, SWT.NONE);
		btn_Last.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				position= imagesList.length-1;
				btn_Next.setEnabled(false);
				ShowImage(position);
				lbl_NoOfFiles.setText((position+1)+"/"+imagesList.length);
			}
		});
		btn_Last.setText("Last >>");
		btn_Last.setEnabled(false);
		btn_Last.setBounds(527, 513, 128, 38);
		formToolkit.adapt(btn_Last, true, true);
		
		
		Button btnSearch = new Button(shlSearch, SWT.NONE);
		btnSearch.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				try
				{
					String queryString ="";
					
					SimpleDateFormat dateFormate = new  SimpleDateFormat("YYYY-MM-dd");
					
					Calendar cal = Calendar.getInstance();
					cal.set(Calendar.DAY_OF_MONTH, dt_Date.getDay());
					cal.set(Calendar.MONTH, dt_Date.getMonth());
					cal.set(Calendar.YEAR, dt_Date.getYear());
					
					queryString = dateFormate.format(cal.getTime());
					queryString = queryString + "-"+Time_HR.getText();// +"-"+Time_Min.getText();
					//System.out.println(queryString);
					
					
					File imagesDirectory = new File("E:/screen/");
					imagesList = imagesDirectory.listFiles(new FilenameFilter() {
						
						@Override
						public boolean accept(File dir, String name) {
							// TODO Auto-generated method stub
							if(com_ImgType.getSelectionIndex() ==0)
							{
							return name.contains("img" +dateFormate.format(cal.getTime()) +"-" + Time_HR.getText());// && name.contains(".jpg");
							}
							else
							{
								return name.contains("Cam-" +dateFormate.format(cal.getTime()) +"-" + Time_HR.getText());// && name.contains(".jpg");
							}
						}
					});
					
					if(imagesList.length == 0)
					{
						lbl_Images.setText("No Images in the selected Date or Time.");
						btn_First.setEnabled(false);
						btn_Previous.setEnabled(false);
						btn_Next.setEnabled(false);
						btn_Last.setEnabled(false);
					}
					else
					{
						ShowImage(position);
						lbl_NoOfFiles.setText((position+1) +"/"+ imagesList.length);
						btn_First.setEnabled(true);
						btn_Previous.setEnabled(false);
						btn_Next.setEnabled(true);
						btn_Last.setEnabled(true);
						
					}				
										
				}
				catch(Exception ex)
				{
					System.out.println(ex);
				}
			}
		});
		
				
		btnSearch.setBounds(246, 66, 75, 25);
		btnSearch.setText("Search");
		
		Button btnCancel = new Button(shlSearch, SWT.NONE);
		btnCancel.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				shlSearch.close();
			}
		});
		btnCancel.setBounds(357, 66, 75, 25);
		btnCancel.setText("Cancel");
		
		
		lbl_NoOfFiles.setForeground(SWTResourceManager.getColor(SWT.COLOR_RED));
		lbl_NoOfFiles.setBounds(577, 76, 83, 15);
		formToolkit.adapt(lbl_NoOfFiles, true, true);
		
		

	}
	
	private void ShowImage(int index)
	{
		try
		{
			File imageFile = new File(imagesList[index].getPath());
						
			Image image = SWTResourceManager.getImage(imageFile.getPath());
			Image NewSizesImage = resizeImage(image, lbl_Images.getBounds().width, lbl_Images.getBounds().height);// Resize The Image to fit the Space on the form
			
			lbl_Images.setImage(NewSizesImage);
		}
		catch(Exception ex)
		{
			System.out.println(ex);
		}
	}
	
	private Image resizeImage(Image OrigionalImage, int NewWidth, int NewHeight)
	{
		Image scaledImage = new Image(Display.getDefault(), NewWidth, NewHeight);
		GC gc = new GC(scaledImage);
		gc.setAntialias(SWT.ON);
		gc.setInterpolation(SWT.HIGH);
		gc.drawImage(OrigionalImage, 0, 0, 
				OrigionalImage.getBounds().width, OrigionalImage.getBounds().height, 
		0, 0, NewWidth, NewHeight);
		gc.dispose();		
		
		return scaledImage;
	}
}
