package com.rmi.main;

import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;

import com.rmi.shared.objects.Message;
import com.rmi.shared.objects.User;

public class Main extends UnicastRemoteObject implements IRmiMethods {

	ArrayList<User> onLineUsers = new ArrayList<User>();
	ArrayList<Message> allMessages = new ArrayList<Message>();

	private static final long serialVersionUID = 1L;

	public Main() throws RemoteException {
		super();
	}

	@Override
	public boolean login(User user) throws RemoteException {
		try {
			for(User currentUser : onLineUsers) {
				if(currentUser.getUserName().equals(user.getUserName())) {
					return false;
				}
			}
			
			onLineUsers.add(user);
			return true;
		} catch (Exception ex) {
			ex.printStackTrace();
			return false;
		}
	}

	@Override
	public boolean logoff(User user) throws RemoteException {
		try {
			for(User currentUser : onLineUsers ) {
				if(currentUser.getId().equals(user.getId())) {
					onLineUsers.remove(currentUser);
					return true;
				}
			}
						
			return false;
		} catch (Exception ex) {
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
		} catch (Exception ex) {
			ex.printStackTrace();
			return false;
		}
	}

	@Override
	public ArrayList<Message> getPendingMessagesByUser(String userId) throws RemoteException {
		ArrayList<Message> returnMessageList = new ArrayList<Message>();
		
		for (Message msg : allMessages) {
			User user = msg.getToUser();
			
			if(user.getId().equals(userId) && !msg.isUserAlredyReceive()) {
				msg.setUserAlredyReceive(true);
				returnMessageList.add(msg);
			}

		}

		return returnMessageList;
	}

	@Override
	public User getUserById(String userId) throws RemoteException {
		for (User usr : onLineUsers) {
			if (usr.getId().equals(userId)) {
				return usr;
			}
		}

		return null;
	}

	public static void main(String[] args) {
		try {
			Naming.rebind("MyServer", new Main());
			System.err.println("Server is ready");
		} catch (Exception ex) {
			System.err.println("Erro: " + ex.toString());
			ex.printStackTrace();
		}
	}
}
