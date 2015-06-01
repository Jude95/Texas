package util;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class MyLog {
	public static final String folder = "record";
	static {
		File dir = new File(folder);
		if (!dir.exists()) {
			dir.mkdir();
		}
	}

	public static void d(String fileName, String content) {
		FileWriter fileWriter = null;
		BufferedWriter bufWriter = null;
		try {
			File file = new File(folder,fileName);
			if (!file.exists()) {
				file.createNewFile();
			}
			fileWriter = new FileWriter(file, true);
			bufWriter = new BufferedWriter(fileWriter);
			bufWriter.write(content + "\n");
			bufWriter.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				if (bufWriter != null) {
					bufWriter.close();
				}
				if (fileWriter != null) {
					fileWriter.close();
				}

			} catch (IOException e) {
				e.printStackTrace();
			}

		}
	};

}
