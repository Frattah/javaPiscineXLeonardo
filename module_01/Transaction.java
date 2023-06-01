package module_01;

import java.util.UUID;

enum transferCategory {
	debits, credits;
}

public final class Transaction {
	UUID id;
	int senderId;
	int recipientId;
	transferCategory type;
	int amount;
	
	public Transaction(int senderId, int recipientId, int amount, transferCategory type) {
		this.senderId = senderId;
		this.recipientId = recipientId;
		this.amount = amount;
		this.type = type;
	}
	
	UUID getId() {
		return this.id;
	}
	
	public void setId() {
		this.id = UUID.randomUUID();
	}
	
	public void setId(UUID id) {
		this.id = id;
	}
	
	transferCategory getType() {
		return type;
	}
	
	public String view(TransactionService server) throws UserNotFoundException {
		StringBuilder out = new StringBuilder();
		out.append((this.getType().equals(transferCategory.debits) ? "To " : "From "));
		if (this.getType().equals(transferCategory.debits))
			out.append(server.getUsers().getUserById(recipientId).getName());
		else
			out.append(server.getUsers().getUserById(senderId).getName());
		out.append("(id = " + senderId + ") - ");
		out.append(amount + " ");
		out.append("with id = " + this.getId().toString() + "\n");
		return out.toString();
	}
}
