import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class CompareContent {
	
	public static  void main(String[] args)throws IOException
	{
		String folderPath = "D:\\AutomationTest";
		File srcFile = new File(folderPath);
		boolean bFile = srcFile.exists();
		if (!bFile || !srcFile.isDirectory() || !srcFile.canRead()) {
		try {
				srcFile.createNewFile();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}else{
					File[] file = srcFile.listFiles();
					for(int i=0;i<file.length;i++){
						//System.out.println(file[i].getName());
						CompareFileContent(file[i].getName());
				}
				
			}
	}
	
	public static void CompareFileContent(String fileName) throws IOException{
		
		String filePath_Left = "D:\\AutomationTest"+"\\" + fileName;
		String filePath_Right = "D:\\AutomationTest1"+"\\" + fileName;
		
		//read from file_1
		File file=new File(filePath_Left);
		if(!file.exists()||file.isDirectory())
				throw new FileNotFoundException();
     
		BufferedReader br=new BufferedReader(new FileReader(file));
		String temp=null;
		StringBuffer sb=new StringBuffer();
		temp=br.readLine();
		while(temp!=null){
			sb.append(temp+" ");
			temp=br.readLine();
		}
		
		//System.out.println("File1:" + sb.toString() + "\n");
		//read from file_2
		File file1 = new File(filePath_Right);
		if(!file.exists()||file.isDirectory())
			throw new FileNotFoundException();
 
		BufferedReader br1=new BufferedReader(new FileReader(file1));
		String temp1=null;
		StringBuffer sb1=new StringBuffer();
		temp1=br1.readLine();
		while(temp1!=null){
			sb1.append(temp1+" ");
			temp1=br1.readLine();
		}
		
		//System.out.println("File2:" + sb1.toString() + "\n");
		if (sb.toString().equals(sb1.toString()))
		{
			System.out.println(fileName + ":    OK");
		}
		
	}
}
