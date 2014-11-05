package imageCompare;

import java.io.*;
import java.awt.*;

import javax.swing.*;
import java.awt.event.*;

import imageCompare.*;

public class ImageCompare {
	static JFileChooser chooser;
	// static FileDialog fopen;
	static Frame window;
	static TextField tf1;
	static TextField tf2;
	static TextField tf3;
	static TextField tf4;
	static Label lb4;
	static TextArea ta5;
	static int counter;
	static int folderLevel = 0;
	static File currentDirectoryPath = null;
	
	// add radio button here
	static JRadioButton singleButton = new JRadioButton("Produce a single ResultSummary.xml", true);
	static JRadioButton twoButton    = new JRadioButton("Produce up to two layers of ResultSummary.xml", false);
	static JRadioButton everyButton = new JRadioButton("Produce ResultSummary.xml for every subfolder", false);

	public static void main(String[] args) {
		System.out.println("Image Comparison Test Started!");
		int len = args.length;
		if (len == 0) {
			window = new Frame("Image Comparison Test (Based on MicroStrategy Integrity Manager)");
			window.setSize(800, 600);
			window.setLayout(new GridLayout(7, 1));
			window.addWindowListener(new MyWindowListener());

			Panel p1 = new Panel();
			p1.setLayout(new GridLayout(1, 3));
			Button b1 = new Button("Choose Baseline");
			tf1 = new TextField();
			Label lb1 = new Label("Baseline can be either file or folder");
			p1.add(b1);
			p1.add(tf1);
			p1.add(lb1);
			window.add(p1);
			b1.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					if (currentDirectoryPath != null && currentDirectoryPath.getPath().length()> 0)
						chooser = new JFileChooser(currentDirectoryPath.getPath());
					else
						chooser = new JFileChooser();
				    chooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
				    chooser.setFileFilter(new PNGFileAndDirectoryFilter());
				    int returnVal = chooser.showOpenDialog(window);
				    if(returnVal == JFileChooser.APPROVE_OPTION) {
				    	File selectedFile = chooser.getSelectedFile();
				    	currentDirectoryPath = chooser.getCurrentDirectory();
				    	if (selectedFile != null && selectedFile.getName() != null)
							tf1.setText(selectedFile.getPath());
				    }

					/*
					fopen = new FileDialog(window, "Please choose a baseline file", FileDialog.LOAD);
					fopen.setVisible(true);
					if (fopen.getFile() != null && !fopen.getFile().isEmpty()) {
						tf1.setText(fopen.getDirectory() + fopen.getFile());
					}
					*/
				}
			});

			Panel p2 = new Panel();
			p2.setLayout(new GridLayout(1, 3));
			Button b2 = new Button("Choose Output");
			tf2 = new TextField();
			Label lb2 = new Label("Output can be either file or folder");
			p2.add(b2);
			p2.add(tf2);
			p2.add(lb2);
			window.add(p2);
			b2.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					if (currentDirectoryPath != null && currentDirectoryPath.getPath().length()> 0)
						chooser = new JFileChooser(currentDirectoryPath.getPath());
					else
						chooser = new JFileChooser();
				    chooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
				    chooser.setFileFilter(new PNGFileAndDirectoryFilter());
				    int returnVal = chooser.showOpenDialog(window);
				    if(returnVal == JFileChooser.APPROVE_OPTION) {
				    	File selectedFile = chooser.getSelectedFile();
				    	currentDirectoryPath = chooser.getCurrentDirectory();
				    	if (selectedFile != null && selectedFile.getName() != null){
							tf2.setText(selectedFile.getPath());
				    	}
				    }
				}
			});
			
			Panel p3 = new Panel();
			p3.setLayout(new GridLayout(1, 2));
			TextArea ta3 = new TextArea("Please specify the absolute file path for ResultSummary.xml.\n"
					+ "If blank, Output folder will be used to store the ResultSummay.xml");
			ta3.setEditable(false);
			tf3 = new TextField();
			p3.add(ta3);
			p3.add(tf3);
			window.add(p3);

			Panel p6 = new Panel();
			p6.setLayout(new GridLayout(1, 2));
			TextArea ta4 = new TextArea("Tolerance [0.0~1.0]:\nIf the difference ratio of two images is less than tolerance,\nthe two images are deemed as matched.");
			ta4.setEditable(false);
			tf4 = new TextField();
			tf4.setText("0.0");
			p6.add(ta4);
			p6.add(tf4);
			window.add(p6);		
			
			Panel p4 = new Panel();
			p4.setLayout(new GridLayout(1, 2));
			Button b4 = new Button("Compare Start!");
			b4.setBackground(new Color(255, 0, 0));
			b4.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					if (tf1.getText().isEmpty() || tf2.getText().isEmpty()) {
						lb4.setText("Please make sure you have selected files!");
						return;
					}
					lb4.setText("Test Starting...");
					String args[];
					if (singleButton.isSelected()) {
						if (!tf3.getText().isEmpty()) {
							args = new String[8];
							args[6] = new String("-r");
							args[7] = new String(tf3.getText());
						} else {
							args = new String[6];
						}
					}
					else if(twoButton.isSelected())
					{
						args = new String[6];
						args[6] = new String("-r");
						args[7] = new String ("2");
					}
					else {
						args = new String[6];
						args[6] = new String("-r");
						args[7] = new String ("n");						
					}
					args[0] = new String("-b");
					args[1] = new String(tf1.getText());
					args[2] = new String("-o");
					args[3] = new String(tf2.getText());
					args[4] = new String("-t");
					args[5] = new String(tf4.getText());
					executeTest(args);
					return;
				}
			});
			lb4 = new Label("Click to start test!");
			p4.add(b4);
			p4.add(lb4);
			window.add(p4);

			Panel p5 = new Panel();
			p5.setLayout(new GridLayout(3, 1));

			ButtonGroup bgroup1 = new ButtonGroup();
			bgroup1.add(singleButton);
			bgroup1.add(twoButton);
			bgroup1.add(everyButton);

			p5.add(singleButton);
			p5.add(twoButton);			
			p5.add(everyButton);
			
			window.add(p5);
				
			ta5 = new TextArea();
			ta5.setText("This tool is used to do pixel-by-pixel comparison of two files or folders.\n"
					+ "It's build upon Integrity Manager and generate ResultSummary.xml file.\n"
					+ "You should use Integrity Manager to open the ResultSummary.xml file to view test result.\n"
					+ "To seek help, please contact Wang, Xiaolei or mailto:xiawang@microstrategy.com");
			ta5.setEditable(false);
			window.add(ta5);
			
			window.setVisible(true);
		} else if (len < 4) {
			printUsage();
			return;
		} else {
			executeTest(args);
			return;
		}

	}

	public static void executeTest(String args[]) {
		counter = 0;
		String baseline = new String();
		String output = new String();
		String result = new String();
		double tolerance = 0.0;
		try {
			for (int i = 0; i < args.length; i++) {
				if (args[i].compareToIgnoreCase("-b") == 0) {
					baseline = args[i + 1];
					i++;
					continue;
				} else if (args[i].compareToIgnoreCase("-o") == 0) {
					output = args[i + 1];
					i++;
					continue;
				} else if (args[i].compareToIgnoreCase("-r") == 0) {
					result = args[i + 1];
					i++;
					continue;
				} else if (args[i].compareToIgnoreCase("-t") == 0) {
					tolerance = Double.parseDouble(args[i + 1]);
					i++;
					continue;
				}
			}
		} catch (NullPointerException ex) {
			printUsage();
			return;
		}
		if (baseline.length() == 0 || output.length() == 0) {
			printUsage();
			return;
		}

		File f1 = new File(baseline);
		File f2 = new File(output);
		if (!f1.exists()) {
			System.out.println("Error! The specified baseline file with path \"" + f1.getAbsolutePath() + "\" does not exits!");
			return;
		} else if (!f2.exists()) {
			System.out.println("Error! The specified output file with path \"" + f2.getAbsolutePath() + "\" does not exits!");
			return;
		}

		if ((f1.isDirectory() && !f2.isDirectory()) || (!f1.isDirectory() && f2.isDirectory())) {
			System.out.println("Error! Can not compare a file with a directory!");
			return;
		}

		if (result.length() == 0) {
			String resultFolder = new String();
			if (f2.isDirectory()) {
				resultFolder = f2.getAbsolutePath();
			} else {
				resultFolder = f2.getParentFile().getAbsolutePath();
			}
			result = resultFolder + File.separator + "ResultSummary.xml";
		}
		if (ta5 != null)
			ta5.setText("");
		IMResultSummaryWriter imWriter = null;
		doCompare(imWriter, f1, f2, result, tolerance);

		System.out.println("Image Comparison Test Finished!\nThere are " + counter + " PNG images get compared!");
		if (lb4 != null)
			lb4.setText("Image Comparison Test Finished!\nThere are " + counter + " PNG images get compared!");
		if (ta5 != null)
			ta5.append("Image Comparison Test Finished!\nThere are " + counter + " PNG images get compared!\n");
	}

	public static void doCompare(IMResultSummaryWriter imWriter, File f1, File f2, String result, double tolerance) {
		folderLevel ++;
		
		IMResultSummaryWriter curIMWriter = null;
		
	/*	boolean createNewSummary = false;
		if ( (!result.equalsIgnoreCase("n") && !result.equalsIgnoreCase("2") && folderLevel == 1 || result.equalsIgnoreCase("2") && folderLevel == 2) 
				&& containsPNGFilesRecursively(f1) && containsPNGFilesRecursively(f2)
			|| (result.equalsIgnoreCase("n") || result.equals("2") && folderLevel == 1) 
				&& containsPNGFiles(f1) && containsPNGFiles(f2))
			createNewSummary = true;
	*/
		

		
		if (! result.equalsIgnoreCase("2") && ! result.equalsIgnoreCase("n") && folderLevel == 1
			|| result.equalsIgnoreCase("2") && folderLevel <= 2
			|| result.equalsIgnoreCase("n")) {
			String resultFile = "";
			if (result.equalsIgnoreCase("2") || result.equalsIgnoreCase("n")) {
				String resultFolder = new String();
				
				if (f2.isDirectory()) {
					resultFolder = f2.getAbsolutePath();
				} else {
					resultFolder = f2.getParentFile().getAbsolutePath();
				}
				resultFile = resultFolder + File.separator + "ResultSummary.xml";
			}
			else 
				resultFile = result;
			//if (createNewSummary == true)
			curIMWriter = new IMResultSummaryWriter(resultFile, "SomeServerName", "34952", "SomeLogin", "SomeProject");
		}
		else 
			curIMWriter = imWriter;
			
		if (f1.isDirectory() && f2.isDirectory()) {
			File f1s[] = f1.listFiles(new ExtensionFileFilter("png"));			// PNGFilter only accepts .png file
			for (int i = 0; i < f1s.length; i++) {
				if (f1s[i].isFile() /*&& f1s[i].getName().toLowerCase().endsWith("png")*/) {
					String fname1 = f1s[i].getAbsolutePath();
					String fname2 = f2.getAbsolutePath() + File.separator + f1s[i].getName();
					compareImg(curIMWriter, fname1, fname2, tolerance);
				}
			}
			f1s = f1.listFiles(new DirectoryFilter());
			for (int i = 0; i < f1s.length; i++) {
				String fname1 = f1s[i].getAbsolutePath();
				String fname2 = f2.getAbsolutePath() + File.separator + f1s[i].getName();
				doCompare(curIMWriter, new File(fname1), new File(fname2), result, tolerance);
			}
		} else {
			String fname1 = f1.getAbsolutePath();
			String fname2 = f2.getAbsolutePath();
			if (fname1.toLowerCase().endsWith(".png") && fname2.toLowerCase().endsWith(".png"))
				compareImg(curIMWriter, fname1, fname2, tolerance);
			else if (lb4 != null)
				lb4.setText("Images weren't compared!\nPlease make sure you have selected two PNG images!");
		}
			
		if (! result.equalsIgnoreCase("2") && ! result.equalsIgnoreCase("n") && folderLevel == 1
				|| result.equalsIgnoreCase("2") && folderLevel <= 2
				|| result.equalsIgnoreCase("n")) {
			// if (createNewSummary == true)
			curIMWriter.finishIMResultSummary();
		}
		
		folderLevel --;
	}
/*	
	public static boolean containsPNGFilesRecursively(File file) {
		if (file.isDirectory()) {
			File files[] = file.listFiles(new ExtensionFileFilter("png"));
			if (files.length > 0)
				return true;
			files = file.listFiles(new DirectoryFilter());
			for (int i = 0; i < files.length; i++) {
				if (containsPNGFilesRecursively(files[i]))
					return true;
			}
			return false;
		} else
			return true;
	}
	
	public static boolean containsPNGFiles(File file) {
		if (file.isDirectory()) {
			File files[] = file.listFiles(new ExtensionFileFilter("png"));
			if (files.length > 0)
				return true;
			else 
				return false;
		} else
			return true;
	}	
*/	
	
	public static boolean compareImg(IMResultSummaryWriter imWriter, String fname1, String fname2, double tolerance) {
		System.out.println("Now comparing " + fname1 + " with " + fname2 + " ... ");
		if (ta5 != null)
			ta5.append("Now comparing " + fname1 + " with " + fname2 + " ... \n");
		
		counter++;
		File f2 = new File(fname2);
		try {
			imWriter.appendIMResultExec(f2.getName(), "newObjectID", f2.getAbsolutePath(), fname1, fname2, tolerance);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			imWriter.appendIMResultFail(f2.getName(), "newObjectID", f2.getAbsolutePath(), fname1 + "\n\n" + e.getMessage(), fname2 + "\n\n" + e.getMessage());
		}
		return true;
	}

	public static void printUsage() {
		System.out
				.println("To use this tool, you should type command with parameter as \n -b %BASELINEFOLDER -o %OUTPUTFOLDER -t %TOLERANCE% -r %RESULTFILE \n The last two parameters can be omitted.");
	}

}

class MyWindowListener extends WindowAdapter {
	public void windowClosing(WindowEvent e) {
		System.out.println("Now exit the program!");
		e.getWindow().setVisible(false);
		System.exit(0);
	}
}

class PNGFileAndDirectoryFilter extends javax.swing.filechooser.FileFilter {

	@Override
	public boolean accept(File file) {
		if(file.isDirectory( )) {
			return true;
		}
		String name = file.getName( );
		return name.toLowerCase().endsWith(".png");
	}

	@Override
	public String getDescription() {
		return "Only PNG files and Directories are expected";
	}
	
}

class ExtensionFileFilter implements FileFilter {

	private String extension;

	public ExtensionFileFilter(String extension) {
		this.extension = extension;
	}

	public boolean accept(File file) {
		if(file.isDirectory( )) {
			return false;
		}
		String name = file.getName();
		return name.toLowerCase().endsWith("."+this.extension.toLowerCase());
	}
} 

class DirectoryFilter implements FileFilter {
	public boolean accept(File file) {
		return file.isDirectory();
	}
}
