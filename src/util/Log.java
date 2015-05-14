package util;

import java.io.File;

public class Log {
	private static File dir = new File("Log");
	private static final String defaultTag = "Log";
	{
		FileUtil.DeleteFolder(dir);
		dir.mkdirs();
	}
	
	public static void setDefaultDir(String dirPath){
		dir = new File(dirPath);
		FileUtil.DeleteFolder(dir);
		dir.mkdirs();
	}
	
	public static void Log(String content){
		Log(defaultTag,content);
	}
	
	public static void Log(String tag,String content){
		FileUtil.writeToFile(new File(dir, tag), content+"\n");
	}

	
}
