package com.rmi.main;

import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Scanner;

import javax.swing.JOptionPane;

import com.rmi.shared.objects.Message;
import com.rmi.shared.objects.User;

public class Main {
	private static IRmiMethods remoteObject;
	
	public static void main(String[] args) throws Exception {
		try {
			boolean loop = true;
			while(loop) {
				Scanner scanner = new Scanner(System.in);
				remoteObject = (IRmiMethods) Naming.lookup("//localhost/MyServer");
				System.out.println("Digite seu nome para realizar login: ");
				String userName = scanner.nextLine().trim();		
				User newuser = new User(userName);
							
				if(remoteObject.login(newuser)) {
					consoleClear();
					printInitialmenu(newuser);
					loop = false;
		
				}else {
					consoleClear();
					System.out.println("Já existe um usuário com esse nome, tente novamente.");
				}	
			}
		}catch(Exception ex) {
			System.out.println("Ocorreu um erro inesperado. Código do erro: 00");
			ex.printStackTrace();
		}
	}
		
	private static void printInitialmenu(User user) {
		try {
			consoleClear();
			System.out.println(user.getUserName() + ", operação realizada com sucesso! Seu id é: " + user.getId());
			
			boolean continuousLoop = true;
			while(continuousLoop) {
				consoleClear();
				System.out.println("");
				System.out.println("#################################");
				System.out.println("A qualquer momento digite: ");
				System.out.println("/GET_ALL - Para listar todos usuários online");
				System.out.println("/LOGOFF - Para sair do chat");
				System.out.println("/REFRESH - Para atualizar as mensagens pendentes");
				System.out.println("/MSG - Para enviar mensagem para um usuário específico.");
				System.out.println("/START-THREAD - Para enviar mensagem para um usuário específico.");
				System.out.println("#################################");
				System.out.println("");
				Scanner scanner = new Scanner(System.in);
				String option = scanner.nextLine();
								
				switch(option) {
					case "/GET_ALL":
						printOnAllOnlineUsers(remoteObject.getOnlineUsersList());
						break;
					case "/LOGOFF":
						if(printLogoffAction(remoteObject, user)) continuousLoop = false;
						break;
					case "/REFRESH":
						refreshUserMessages(remoteObject, user);
						break;
					case "/MSG":
						sendMessage(remoteObject, user);
						break;
					case "/START-THREAD":
						startThread(remoteObject, user);
						break;
					default:
						consoleClear();
						System.out.println("Comando digitado não existe, tente novamente...");
						break;
				}
			}
		}catch(Exception ex) {
			System.out.println("Ocorreu um erro inesperado. Código do erro: 01");
			ex.printStackTrace();
		}
	}
	
	private static void printOnAllOnlineUsers(ArrayList<User> userList) {
		try {
			consoleClear();
			System.out.println("Imprimindo todos usuários on line: (total: " + userList.size() + ")");
			for(User usr : userList) {
				System.out.println("___________");
				System.out.println("id: " + usr.getId());
				System.out.println("name: " + usr.getUserName());
				System.out.println("___________");
			}
			
		}catch(Exception ex) {
			System.out.println("Ocorreu um erro inesperado. Código do erro: 02");
		}
	}
	
	private static boolean printLogoffAction(IRmiMethods remoteObject, User user) throws RemoteException {
		try {
			consoleClear();
			if(remoteObject.logoff(user)) {
				System.out.println("Usuário deslogado com sucesso");
				return true;
			}
			
			System.out.println("Houve um problema ao deslogar. Tenta novamente mais tarde. Ou feche o terminal");
			return false;
			
		}catch(Exception ex) {
			System.out.println("Ocorreu um erro inesperado. Código do erro: 03");
			return false;
		}		
	}

	private static void refreshUserMessages(IRmiMethods remoteObject, User user) throws RemoteException {
		try {
			consoleClear();
			System.out.println("Listando mensagens do usuário: ");
			ArrayList<Message> currentUserMessages = null;
			currentUserMessages = remoteObject.getPendingMessagesByUser(user.getId());
			if(currentUserMessages.size()>0) {
				for(Message msg : currentUserMessages) {
					System.out.println(msg.toString());
				}
			}else {
				System.out.println("Você ainda não possui mensagens... isso é bem triste...");
			}
			
		}catch(Exception ex) {
			System.out.println("Ocorreu um erro inesperado. Código do erro: 04");
		}
	}
	
	private static void sendMessage(IRmiMethods remoteObject, User user) {
		try {
			consoleClear();
			System.out.println("Digite o id do usuário que deseja enviar a mensagem: ");
			Scanner scanner = new Scanner(System.in);
			String destinyUserId = scanner.nextLine().trim();
				
			User destinyUser = null;
			destinyUser = remoteObject.getUserById(destinyUserId);
			
			if(destinyUser != null) {
				Message message = new Message();
				message.setFromUser(user);
				message.setToUser(destinyUser);
				
				System.out.println("Digite a mensagem: ");				
				Scanner scanner2 = new Scanner(System.in);
				String textOfMessage = scanner2.nextLine();
					
				message.setMessage(textOfMessage.trim());
				remoteObject.sendMessage(message);
				System.out.println("Mensagem enviada com sucesso!");
				return;
			}else {
				System.out.println("Não foi encontrado usuário logado com esse id. Veja se o usuário que deseja está logado utilizando o comando: /GET_ALL");
				return;
			}
			
		}catch(Exception ex) {
			System.out.println("Ocorreu um erro inesperado. Código do erro: 05");
			ex.printStackTrace();
		}		
	}
	
	private static void startThread(IRmiMethods remoteObject2, User user) {
		try {
			UpdateUserMessages refreshRunable = new UpdateUserMessages(remoteObject, user);
			Thread refreshUserMessagesThread = new Thread(refreshRunable);
			refreshUserMessagesThread.setName(user.getId());
			refreshUserMessagesThread.start();
		}catch(Exception ignore) {}
	}
	
	private static void consoleClear() {
		try {
		 //new ProcessBuilder("clear").inheritIO().start().waitFor();
		}catch(Exception ex) {
			
		}
	}
}


