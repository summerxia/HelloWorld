package com.test;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class WriteToFile {
	public static boolean createNewFile(String path){
		boolean isSuccess = false;
		String filePathTurn = path.replaceAll("\\\\", "/");
		int index = filePathTurn.lastIndexOf("/");
		String dir = filePathTurn.substring(0,index);
		
		File fileDir = new File(dir);
		isSuccess = fileDir.mkdirs();
		
		File file = new File(filePathTurn);
		try{
			isSuccess = file.createNewFile();
			
		}catch(IOException e){
			e.printStackTrace();
			isSuccess = false;
		}
		
		return isSuccess;
	}
	
	
	public static boolean writeIntoFile(String content,String path,boolean isAppend){
		boolean isSuccess = true;
		
		int index = path.lastIndexOf("/");
		String dir = path.substring(0,index);
		File dirs = new File(dir);
		isSuccess = dirs.mkdirs();
		
		File file = new File(path);
		try{
			isSuccess = file.createNewFile();
		}catch(IOException e){
			e.printStackTrace();
			isSuccess = false;
		}
		
		FileWriter writer = null;
		try{
			writer = new FileWriter(file,isAppend);
			writer.write(content);
			writer.flush();
		}catch(IOException e){
			isSuccess = false;
			e.printStackTrace();
		}finally{
			try {
				writer.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		
		return isSuccess;
	}
}
