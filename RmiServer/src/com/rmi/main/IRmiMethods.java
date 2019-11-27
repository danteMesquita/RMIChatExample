package com.rmi.main;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;

import com.rmi.models.Message;
import com.rmi.models.User;

public interface IRmiMethods extends Remote {
	public boolean sendMessage(Message msg) throws RemoteException;
	public ArrayList<User> getOnlineUsersList() throws RemoteException;
	public boolean login(User user) throws RemoteException;
	public boolean logoff(User user) throws RemoteException; 
	public ArrayList<User> getPendingMessagesByUser(User user) throws RemoteException;
}
