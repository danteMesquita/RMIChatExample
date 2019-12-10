package com.rmi.main;

import java.rmi.RemoteException;
import java.util.ArrayList;

import com.rmi.shared.objects.Message;
import com.rmi.shared.objects.User;

public  class UpdateUserMessages implements Runnable {

	IRmiMethods _rmiService;
	User _user;
	
	UpdateUserMessages(IRmiMethods rmi, User user){
		_rmiService = rmi;
		_user = user;
	}
	
	@Override
	public  void run() {
		boolean loop = true;
		while(loop) { 
			try {
				refreshUserMessages(_rmiService, _user);	
				Thread.sleep(2500);
			}catch(Exception ex) {
				ex.printStackTrace();
			}	
		}
	}
	
	private void refreshUserMessages(IRmiMethods remoteObject, User user) throws RemoteException {
		try {
			
			ArrayList<Message> currentUserMessages = null;
			currentUserMessages = remoteObject.getPendingMessagesByUser(user.getId());
			if(currentUserMessages.size()>0) {
				for(Message msg : currentUserMessages) {
					System.out.println(msg.toString());
				}	
			}
		}catch(Exception ex) {
			System.out.println("Ocorreu um erro inesperado. Código do erro: 04.1");
		}

	}
}
