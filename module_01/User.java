package module_01;

public final class User {
	int	id;
	String name;
	int balance;
	TransactionsList transList;
	
	public User(String name, int balance) {
		this.balance = balance;
		this.name = name;
		this.id = UserIdsGenerator.getInstance().generateId();
		this.transList = new TransactionsLinkedList();
	}
	
	int getId() {
		return this.id;
	}
	
	String getName() {
		return this.name;
	}
	
	int getBalance() {
		return this.balance;
	}
	
	void addSubBalance(int addSub) {
		this.balance += addSub;
	}
	
	TransactionsList getTransList() {
		return this.transList;
	}
}
