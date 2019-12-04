package com.rmi.main;

import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.util.ArrayList;
import java.util.Scanner;

import javax.swing.JOptionPane;

import com.rmi.shared.objects.User;

public class Main {
	private static IRmiMethods remoteObject;
	
	
	public static void main(String[] args) throws Exception {
		try {
			Scanner scanner = new Scanner(System.in);
			remoteObject = (IRmiMethods) Naming.lookup("//localhost/MyServer");
			
			System.out.println("Digite seu nome para realizar login: ");
			String userName = scanner.nextLine().trim();
			User newuser = new User(userName);
						
			if(remoteObject.login(newuser)) {
				//Start a thread
				printInitialmenu(newuser);
				
				
				
			}else {
				System.out.println("Falha ao realizar login");
			}
									
		}catch(Exception ex) {
			ex.printStackTrace();
		}
	}
		
	private static void printInitialmenu(User user) {
		try {
			System.out.println(user.getUserName() + ", operação realizada com sucesso!");
			
			boolean continuousLoop = true;
			while(continuousLoop) {
				Scanner scanner = new Scanner(System.in);
				System.out.println("");
				System.out.println("#################################");
				System.out.println("A qualquer momento digite: ");
				System.out.println("/GET_ALL - Para listar todos usuários online");
				System.out.println("/LOGOFF - Para sair do chat");
				System.out.println("/REFRESH - Para atualizar as mensagens pendentes");
				System.out.println("/MSG - Para enviar mensagem para um usuário específico.");
				System.out.println("#################################");
				System.out.println("");
				String option = scanner.nextLine().trim();
				
				switch(option) {
					case "/GET_ALL":
						printOnAllOnlineUsers(remoteObject.getOnlineUsersList());
						break;
					case "/LOGOFF":
						if(printLogoffAction(remoteObject, user)) continuousLoop = false;
						break;
					case "/REFRESH":
						
						break;
					case "/MSG":
						break;
					default:
						System.out.println("#################################");
						break;
				}
				
			}
		}catch(Exception ex) {
			System.out.println("Ocorreu um erro no método printInitialmenu(String userName)");
			ex.printStackTrace();
		}
		
	}
	
	private static void printOnAllOnlineUsers(ArrayList<User> userList) {
		System.out.println("Imprimindo todos usuários on line: (total: " + userList.size() + ")");
		for(User usr : userList) {
			System.out.println("___________");
			System.out.println("id: " + usr.getId());
			System.out.println("name: " + usr.getUserName());
			System.out.println("___________");
		}
	}
	
	private static boolean printLogoffAction(IRmiMethods remoteObject, User user) throws RemoteException {
		
		if(remoteObject.logoff(user)) {
			System.out.println("Usuário deslogado com sucesso");
			return true;
		}
		
		System.out.println("Houve um problema ao deslogar. Tenta novamente mais tarde. Ou feche o terminal");
		return false;
	}

	private static void RefreshUserMessages(IRmiMethods remoteObject, User user) throws RemoteException {
		ArrayList<User> users = remoteObject.getPendingMessagesByUser(user);
				
	}
}


