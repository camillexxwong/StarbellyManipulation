package imageCompare;

import java.io.*;


public class FileWriting{
    private String pathfile;
    private File file;
    private PrintWriter prtWriter;

    public FileWriting(String name){
    	pathfile=name;
    }

    public void initialize() throws IOException{
		file=new File(pathfile);
		if(file.exists()) file.delete();//delete if it already exists
		file.createNewFile(); //create a new file
		prtWriter=new PrintWriter(new BufferedWriter(new FileWriter(file, true)));
    }

    public void initialize_UTF8() throws IOException{
    	file=new File(pathfile);
    	if(file.exists()) file.delete();//delete if it already exists
    	file.createNewFile(); //create a new file
    	prtWriter=new PrintWriter(file,"UTF-8");
   }
    
    public void initialize_UTF16() throws IOException{
    	file=new File(pathfile);
    	if(file.exists()) file.delete();//delete if it already exists
    	file.createNewFile(); //create a new file
    	prtWriter=new PrintWriter(file,"UTF-16");
    }
    
    public void appendContent(String content){
    	prtWriter.println(content.replaceAll("\n", System.getProperty("line.separator")));
    	prtWriter.flush();
    }
    
    public void close(){
    	prtWriter.close();
    }	
    
    void finalized(){
		file=null;
		prtWriter=null;
    }
	
}	
