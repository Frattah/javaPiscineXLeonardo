package module_01;

import java.util.UUID;

class TransactionNotFoundException extends Exception {}

public interface TransactionsList {
	
	void addTransaction(Transaction t);
	
	boolean removeById(UUID id) throws TransactionNotFoundException;
	
	Transaction[] toArray();
	
	int	getTransactionNumber();
}
