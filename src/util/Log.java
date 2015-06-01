package util;

import java.io.File;
import java.io.IOException;

public class Log {
	private static String defaultTag = "Log";
	private static File Log ;
	
	public static void init(){
		//Log = new File(FileUtil.getUserDir(), "Log");
		Log = FileUtil.getUserDir();
	}
	
	public static void Log(String content){
		Log(defaultTag,content);
	}
	
	public static void Log(String tag,String content){
		File file = new File(Log,tag);
		try {
			file.createNewFile();
			FileUtil.writeToFile(file, content+"\n");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
