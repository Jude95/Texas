package util;

import java.io.File;

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
		FileUtil.writeToFile(new File(Log,tag), content+"\n");
	}

}
