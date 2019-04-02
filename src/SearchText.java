import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.wb.swt.SWTResourceManager;

import com.ibm.icu.text.SimpleDateFormat;

import org.eclipse.swt.custom.ScrolledComposite;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FilenameFilter;
import java.nio.file.Files;
import java.util.Calendar;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.DateTime;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.custom.StyledText;
import org.eclipse.jface.text.TextViewer;

public class SearchText {

	protected Shell shlSearchText;

	
	int position = 0;
	Label lbl_Text;
	File[] TextFilesList;
	
	/**
	 * Launch the application.
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			SearchText window = new SearchText();
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
		shlSearchText.open();
		shlSearchText.layout();
		while (!shlSearchText.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
	}

	/**
	 * Create contents of the window.
	 */
	protected void createContents() {
		shlSearchText = new Shell();
		shlSearchText.setSize(706, 596);
		shlSearchText.setText("Search Text");
		
		Label label = new Label(shlSearchText, SWT.NONE);
		label.setText("Enter Date and Time:");
		label.setBounds(117, 10, 148, 15);
		
		Label label_1 = new Label(shlSearchText, SWT.NONE);
		label_1.setText("Date:");
		label_1.setBounds(137, 31, 62, 24);
		
		DateTime dt_Date = new DateTime(shlSearchText, SWT.BORDER | SWT.LONG);
		dt_Date.setBounds(211, 31, 235, 24);
		
		Button button = new Button(shlSearchText, SWT.NONE);
		button.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				shlSearchText.close();
			}
		});
		button.setText("Cancel");
		button.setBounds(453, 61, 75, 25);
		
		Combo combo = new Combo(shlSearchText, SWT.NONE);
		combo.setItems(new String[] {"00", "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31", "32", "33", "34", "35", "36", "37", "38", "39", "40", "41", "42", "43", "44", "45", "46", "47", "48", "49", "50", "51", "52", "53", "54", "55", "56", "57", "58", "59"});
		combo.setBounds(269, 63, 42, 23);
		combo.select(0);
		
		Combo Time_HR = new Combo(shlSearchText, SWT.NONE);
		Time_HR.setItems(new String[] {"01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12"});
		Time_HR.setBounds(211, 63, 42, 23);
		Time_HR.select(0);
		
		Label label_2 = new Label(shlSearchText, SWT.NONE);
		label_2.setText("Time:");
		label_2.setBounds(137, 66, 69, 15);
		
		Label lbl_NoOfFiles = new Label(shlSearchText, SWT.CENTER);
		lbl_NoOfFiles.setForeground(SWTResourceManager.getColor(SWT.COLOR_RED));
		lbl_NoOfFiles.setFont(SWTResourceManager.getFont("Segoe UI", 10, SWT.BOLD));
		lbl_NoOfFiles.setAlignment(SWT.CENTER);
		lbl_NoOfFiles.setBounds(561, 71, 83, 15);
		
		
		Button btn_Next = new Button(shlSearchText, SWT.NONE);
		Button btn_First = new Button(shlSearchText, SWT.NONE);
		Button btn_Last = new Button(shlSearchText, SWT.NONE);
		
		Button btn_Previous = new Button(shlSearchText, SWT.NONE);
		btn_Previous.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				
position= position-1;
				
				ShowText(position);
				lbl_NoOfFiles.setText((position+1)+"/"+TextFilesList.length);
				
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
		btn_Previous.setText("< Previous");
		btn_Previous.setEnabled(false);
		btn_Previous.setBounds(234, 511, 128, 38);
		
		
		btn_Next.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				position= position+1;
				if(position <= TextFilesList.length-1)
				{
					btn_Next.setEnabled(true);
					btn_Previous.setEnabled(true);
					ShowText(position);
					lbl_NoOfFiles.setText((position+1)+"/"+ TextFilesList.length);
				}
				else
				{
					position= position-1;
					btn_Next.setEnabled(false);
				}
			}
		});
		btn_Next.setText("Next  >");
		btn_Next.setEnabled(false);
		btn_Next.setBounds(396, 511, 128, 38);
		
		lbl_Text = new Label(shlSearchText, SWT.NONE);
		lbl_Text.setBounds(10, 105, 650, 400);
		
		Button button_1 = new Button(shlSearchText, SWT.NONE);
		button_1.addSelectionListener(new SelectionAdapter() {
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
					
					
					File textsDirectory = new File("E:/screen/");
					TextFilesList = textsDirectory.listFiles(new FilenameFilter() {
						
						@Override
						public boolean accept(File dir, String name) {
							// TODO Auto-generated method stub
							
							return name.contains(dateFormate.format(cal.getTime()) +"-" + Time_HR.getText());// && name.contains(".jpg");
							
						}
					});
					
					if(TextFilesList.length == 0)
					{
						lbl_Text.setText("No Images in the selected Date or Time.");
						btn_First.setEnabled(false);
						btn_Previous.setEnabled(false);
						btn_Next.setEnabled(false);
						btn_Last.setEnabled(false);
					}
					else
					{
						ShowText(position);
						lbl_NoOfFiles.setText((position+1) +"/"+ TextFilesList.length);
						btn_First.setEnabled(true);
						btn_Previous.setEnabled(false);
						btn_Next.setEnabled(true);
						btn_Last.setEnabled(true);
						
					}
				}
				catch(Exception ex)
				{
					
				}
			}
		});
		button_1.setText("Search");
		button_1.setBounds(342, 61, 75, 25);
		
		
		btn_First.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				position=0;
				btn_Previous.setEnabled(false);
				ShowText(position);
				lbl_NoOfFiles.setText((position+1)+"/"+TextFilesList.length);
			}
		});
		btn_First.setText("<< First");
		btn_First.setEnabled(false);
		btn_First.setBounds(75, 511, 128, 38);
		
		
		btn_Last.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				position= TextFilesList.length-1;
				btn_Next.setEnabled(false);
				ShowText(position);
				lbl_NoOfFiles.setText((position+1)+"/"+TextFilesList.length);
			}
		});
		btn_Last.setText("Last >>");
		btn_Last.setEnabled(false);
		btn_Last.setBounds(552, 509, 128, 38);
		
		}
	
	private void ShowText(int index)
	{
		try
		{
			File textFile = new File(TextFilesList[index].getPath());
						
			 FileReader reader = new FileReader(textFile);
			 BufferedReader BR = new BufferedReader(reader);
			StringBuilder strBuilder = new StringBuilder();
			 String Line = BR.readLine();
			
			while(Line !=null)
			{
				strBuilder.append(Line).append("\n");
				Line = BR.readLine();
			}
			
			lbl_Text.setText(strBuilder.toString());
		}
		catch(Exception ex)
		{
			System.out.println(ex);
		}
	}
}
