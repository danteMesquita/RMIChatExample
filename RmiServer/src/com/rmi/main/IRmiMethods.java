package com.rmi.main;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;

import com.rmi.shared.objects.Message;
import com.rmi.shared.objects.User;

public interface IRmiMethods extends Remote {
	public boolean sendMessage(Message msg) throws RemoteException;
	public ArrayList<User> getOnlineUsersList() throws RemoteException;
	public boolean login(User user) throws RemoteException;
	public boolean logoff(User user) throws RemoteException; 
	public ArrayList<Message> getPendingMessagesByUser(String userId) throws RemoteException;
	public User getUserById(String userId) throws RemoteException;
}
