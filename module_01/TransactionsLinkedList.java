package module_01;

import java.util.UUID;

class Node {
	Transaction info;
	Node next;
	Node prev;
}

class List {
	Node head;
	Node tail;
}

public class TransactionsLinkedList implements TransactionsList {
	List transList;

	public TransactionsLinkedList() {
		this.transList = new List();
	}

	@Override
	public void addTransaction(Transaction t) {
		Node newTrans = new Node();
		if (this.transList.head == null) {
			this.transList.tail = newTrans;
			newTrans.next = null;
		}
		else
			newTrans.next = this.transList.head;
		newTrans.info = t;
		newTrans.prev = null;
		this.transList.head = newTrans;
	}

	@Override
	public boolean removeById(UUID id) throws TransactionNotFoundException {
		Node i = this.transList.head;
		while (i != null && i.next != null) {
			if (i.next.info.getId().equals(id)) {
				i.next = i.next.next;
				return true;
			}
			i = i.next;
		}
		if (i.info.getId().equals(id))
			this.transList = null;
		throw new TransactionNotFoundException();
	}

	@Override
	public int	getTransactionNumber() {
		Node i = this.transList.head;
		int output = 0;
		while (i != null) {
			output++;
			i = i.next;
		}
		return output;
	}

	@Override
	public Transaction[] toArray() {
		Transaction[] output = new Transaction[this.getTransactionNumber()];
		Node i = this.transList.head;
		int j = 0;
		while (i != null) {
			output[j++] = i.info;
			i = i.next;
		}
		return output;
	}

}
