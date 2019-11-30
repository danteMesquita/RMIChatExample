package com.rmi.main;


import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;

import com.rmi.shared.objects.Message;
import com.rmi.shared.objects.User;


public class Main extends UnicastRemoteObject implements IRmiMethods  {
	
	ArrayList<User> onLineUsers = new ArrayList<User>();
	ArrayList<Message> allMessages = new ArrayList<Message>();
	
	private static final long serialVersionUID = 1L;
	
	public Main() throws RemoteException {
		super();
	}
	
	@Override
	public boolean login(User user) throws RemoteException {
		try {
		
			onLineUsers.add(user);
			return true;
		}catch(Exception ex) {
			ex.printStackTrace();
			return false;
		}
	}
	
	@Override
	public boolean logoff(User user) throws RemoteException {
		try {
			
			onLineUsers.remove(user);
			return true;
		}catch(Exception ex) {
			ex.printStackTrace();
			return false;
		}
	}
		
	@Override
	public ArrayList<User> getOnlineUsersList() throws RemoteException {
		return this.onLineUsers;
	}
		
	@Override
	public boolean sendMessage(Message msg) throws RemoteException {
		try {
			allMessages.add(msg);
			return true;
		}catch(Exception ex) {
			ex.printStackTrace();
			return false;
		}
	}
	
	@Override
	public ArrayList<User> getPendingMessagesByUser(User user) throws RemoteException {
		// TODO Auto-generated method stub
		return null;
	}
	
	public static void main(String[] args) {
		try {
			Naming.rebind("MyServer", new Main());
			System.err.println("Server is ready");
		}catch(Exception ex) {
			System.err.println("Erro: " + ex.toString());
			ex.printStackTrace();
		}
	}
		
}
