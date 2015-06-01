package util;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import net.Client;
import config.Config;

public class FileUtil {
	static {
		if (!getTempDir().exists()) {
			getTempDir().mkdir();
		}
		if (!getStandDir().exists()) {
			getStandDir().mkdir();
		}
	}

	private static File UserDir;

	public static void init(String ID) {
		UserDir = new File(getTempDir(), "Player" + ID);
		DeleteFolder(UserDir);
		UserDir.mkdir();
	}

	public static File getUserDir() {
		return UserDir;
	}

	public static File getTempDir() {
		return new File(Config.TempDir);
	}

	public static File getStandDir() {
		return new File(Config.StandDir);
	}

	public static void writeToFile(File file, String content) {
		FileWriter writer = null;
		try {
			file.createNewFile();
			writer = new FileWriter(file, true);
			writer.write(content);
			writer.flush();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				if (writer != null) {
					writer.close();
				}
			} catch (IOException e) {

			}
		}
	}

	public static boolean DeleteFolder(File file) {
		boolean flag = false;
		// 判断目录或文件是否存在
		if (!file.exists()) { // 不存在返回 false
			return flag;
		} else {
			// 判断是否为文件
			if (file.isFile()) { // 为文件时调用删除文件方法
				return deleteFile(file);
			} else { // 为目录时调用删除目录方法
				return deleteDirectory(file);
			}
		}
	}

	/**
	 * 删除目录（文件夹）以及目录下的文件
	 * 
	 * @param sPath
	 *            被删除目录的文件路径
	 * @return 目录删除成功返回true，否则返回false
	 */
	public static boolean deleteDirectory(File dirFile) {
		// 如果dir对应的文件不存在，或者不是一个目录，则退出
		if (!dirFile.exists() || !dirFile.isDirectory()) {
			return false;
		}
		boolean flag = true;
		// 删除文件夹下的所有文件(包括子目录)
		File[] files = dirFile.listFiles();
		for (int i = 0; i < files.length; i++) {
			// 删除子文件
			if (files[i].isFile()) {
				flag = deleteFile(files[i]);
				if (!flag)
					break;
			} // 删除子目录
			else {
				flag = deleteDirectory(files[i]);
				if (!flag)
					break;
			}
		}
		if (!flag)
			return false;
		// 删除当前目录
		if (dirFile.delete()) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 删除单个文件
	 * 
	 * @param sPath
	 *            被删除文件的文件名
	 * @return 单个文件删除成功返回true，否则返回false
	 */
	public static boolean deleteFile(File file) {
		boolean flag = false;
		if (file.isFile() && file.exists()) {
			file.delete();
			flag = true;
		}
		return flag;
	}
}
