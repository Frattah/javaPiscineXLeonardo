package module_01;

import java.util.UUID;

class IllegalTransactionException extends Exception {};

public class TransactionService {
	UserList users;
	
	public TransactionService() {
		this.users = new UsersArrayList();
	}
	
	void addUser(User u) {
		users.addUser(u);
	}
	
	UserList getUsers() {
		return this.users;
	}
	
	int getBalanceById(int id) throws UserNotFoundException {
		return users.getUserById(id).getBalance();
	}
	
	void addTransaction(int senderId, int recipientId, int amount) throws IllegalTransactionException, UserNotFoundException {
		User sender = this.users.getUserById(senderId);
		User recipient = this.users.getUserById(recipientId);
		if (sender.getBalance() < amount || amount <= 0 || senderId == recipientId)
			throw new IllegalTransactionException();
		Transaction transSender = new Transaction(senderId, recipientId, amount, transferCategory.debits);
		transSender.setId();
		sender.getTransList().addTransaction(transSender);
		Transaction transRecipient = new Transaction(senderId, recipientId, amount, transferCategory.credits);
		transRecipient.setId(transSender.getId());
		recipient.getTransList().addTransaction(transRecipient);
		sender.addSubBalance(-amount);
		recipient.addSubBalance(amount);
	}
	
	Transaction[] getTransfers(int id) throws UserNotFoundException {
		return this.users.getUserById(id).getTransList().toArray();
	}
	
	void removeTransaction(int userId, UUID transId) throws TransactionNotFoundException, UserNotFoundException {
		this.users.getUserById(userId).getTransList().removeById(transId);
	}
	
//	Transaction[] checkValidity() {
//		Transaction[] transList;
//		int	transNumb;
//		TransactionsList out = new TransactionsLinkedList();
//		for (int i = 0; i < this.users.getUsersNumber(); i++) {
//			transList = this.users.getUsersList()[i].getTransList().toArray();
//			transNumb = this.users.getUsersList()[i].getTransList().getTransactionNumber();
//			for (int j = 0; j < transNumb; j++) {
//				if (!this.users.existId(transList[j].recipientId) || !this.users.existId(transList[j].senderId))
//					out.addTransaction(transList[j]);
//				if (transList[j].getType().equals(transferCategory.credits) && ) {
//					
//				}
//			}
//		}
//	}
}
