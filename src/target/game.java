package target;


import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;


public class game {
	static String ID ;
	static long launchTime;
	public static void main(String[] args) {
		ID = args[4];
		System.out.println(ID+".start");
		launchTime = System.currentTimeMillis();
		try{
			Socket socket=new Socket(args[0],Integer.parseInt(args[1]),InetAddress.getByName(args[2]),Integer.parseInt(args[3]));
			PrintWriter os=new PrintWriter(socket.getOutputStream());
			BufferedReader is=new BufferedReader(new InputStreamReader(socket.getInputStream()));
			os.print("reg:ID jude");
			String line;
			
			while(true){
				
				if((line = is.readLine())!=null){
					System.out.println(line);
					break;
				}
				if(System.currentTimeMillis()-launchTime>5000){
					System.out.println(ID+".wait for respones time out");
					break;
				}
				
			}
			os.close(); 
			is.close(); 
			socket.close(); 
		}catch(Exception e) {
			System.out.println("Error"+e); 
		}
	}

}
