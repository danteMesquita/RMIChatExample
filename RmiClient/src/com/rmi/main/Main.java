package com.rmi.main;

import java.rmi.Naming;
import java.rmi.registry.LocateRegistry;

import javax.swing.JOptionPane;

import com.rmi.shared.objects.User;

public class Main {
	private static IRmiMethods remoteObject;
	
	public static void main(String[] args) throws Exception {
		try {
			remoteObject = (IRmiMethods) Naming.lookup("//localhost/MyServer");
			String userName = JOptionPane.showInputDialog("login: ");
			User newuser = new User(userName);
			
			if(remoteObject.login(newuser)) {
				System.out.println("Login realizado com sucesso!");
			}else {
				System.out.println("Falha ao realizar login");
			}
				
//			JOptionPane.showMessageDialog(null, responseFromServer);
						
		}catch(Exception ex) {
			ex.printStackTrace();
		}
	}
}
