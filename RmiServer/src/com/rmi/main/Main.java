package com.rmi.main;

import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;

import com.rmi.models.Message;
import com.rmi.models.User;

public class Main extends UnicastRemoteObject implements IRmiMethods  {
	/*
	 [ ] - Ao abrir o client abre no teminal perguntando qual o nome do usuário.
	 [ ] - Tem que ser atualizado a cada 2,5 segundos as mensagens daquele usuário.
	 [ ] -  
	 */
	ArrayList<User> onLineUsers = new ArrayList<User>();
	
	private static final long serialVersionUID = 1L;
	
	public Main() throws RemoteException {
		super();
	}
	
	@Override
	public boolean login(User user) throws RemoteException {
		
		
		return false;
	}
	
	@Override
	public boolean logoff(User user) throws RemoteException {
		// TODO Auto-generated method stub
		return false;
	}
		
	@Override
	public ArrayList<User> getOnlineUsersList() throws RemoteException {
		// TODO Auto-generated method stub
		return null;
	}
		
	@Override
	public boolean sendMessage(Message msg) throws RemoteException {
		// TODO Auto-generated method stub
		return false;
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
