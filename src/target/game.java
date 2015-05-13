package target;

import java.awt.Frame;
import java.awt.Window;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

import javax.swing.JOptionPane;

public class game {

	public static void main(String[] args) {
		System.out.println("fuck!!!!!!!!!!!!!!!!!!!");
		
		try{
			Socket socket=new Socket(args[0],Integer.parseInt(args[1]));
			BufferedReader sin=new BufferedReader(new InputStreamReader(System.in));
			PrintWriter os=new PrintWriter(socket.getOutputStream());
			BufferedReader is=new BufferedReader(new InputStreamReader(socket.getInputStream()));
			String readline;
			while(!readline.equals("bye")){
				os.println(readline);
				os.flush();
				System.out.println("Client:"+readline);
				System.out.println("Server:"+is.readLine());
		}catch(Exception e) {
		}
	}

}
