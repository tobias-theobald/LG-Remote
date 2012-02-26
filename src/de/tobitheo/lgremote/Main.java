package de.tobitheo.lgremote;

import java.io.IOException;
import java.net.UnknownHostException;
import java.util.Scanner;

public class Main {

	private static String authKey = "DDJOFF";

	public static void main(String[] args) throws UnknownHostException,
			IOException, InterruptedException {
		Connection conn = new Connection();
		conn.mDnsDiscovery();
		if (authKey == null) {
			conn.showAuthKey();
			System.out.println("Key should be displayed now");
		} else
			System.out.println(conn.authenticate(authKey));

		int start = 0;
		Scanner in = new Scanner(System.in);
		String s = in.nextLine();
		while (s != "") {
			conn.sendKeyCode(Integer.parseInt(s));
			s = in.nextLine();
		}
			
		
	}
}
