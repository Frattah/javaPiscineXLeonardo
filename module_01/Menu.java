package module_01;

import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.UUID;

class NegativeBalanceException extends Exception {}

public class Menu {
	TransactionService server;
	boolean exit;
	boolean devMode;
	final String menuStr = "1. Add user\n" +
			"2. View user balances\n" +
			"3. Perform a transfer\n" +
			"4. View all transactions for a specific user\n" +
			"5. DEV - remove a trasfer by ID\n" +
			"6. DEV - check transfer validity\n" +
			"7. Finish execution\n" +
			"-> ";

	private Menu() {
		this.server = new TransactionService();
		this.exit = false;
		this.devMode = false;
	}

	public static void main(String[] argc) {
		Menu menu = new Menu();
		Scanner scan;
		int cmdCode = 0;
		if (argc.length > 0 && "--profile=dev".equals(argc[0])) {
			menu.devMode = true;
		}
		do {
			System.out.print(menu.menuStr);
			scan = new Scanner(System.in);
			try {
				if (scan.hasNext())
					cmdCode = scan.nextInt();
			} catch (InputMismatchException e) {
				System.err.println("Error: Command code must be an integer!");
				continue;
			}
			switch (cmdCode) {


			/*
			 * 	ADD USER
			 * 	Declaring an username and an initial balance you can create
			 *  a new user, system give him a procedural userId
			 *  Error if:
			 *  			- balance not an integer
			 *  			- negative balance
			 * 
			 */
			case 1:
				String name = null;
				int balance = 0;
				System.out.print("Enter a user name and a balance\n-> ");
				scan = new Scanner(System.in);
				if (scan.hasNext()) {
					name = scan.next();
				}
				try {
					if (scan.hasNext())
						balance = scan.nextInt();
					if (balance < 0) throw new NegativeBalanceException();
					User newUser = new User(name, balance);
					menu.server.addUser(newUser);
					System.out.println("User with id = " + newUser.id + " is added");
				} catch (InputMismatchException e) {
					System.err.println("Error: balance must be an integer!");
				} catch (NegativeBalanceException e) {
					System.err.println("Error: balance can't be negative!");
				}
				break;



				/*
				 * 
				 * 	VIEW USER BALANCES
				 * 	Using userId you can view his money amount
				 *  Error if:
				 *  			- userId not found
				 * 				- userId not an integer
				 * 
				 */
			case 2:
				System.out.print("Enter a user ID\n-> ");
				scan = new Scanner(System.in);
				if (scan.hasNext()) {
					try {
						int id = scan.nextInt();
						System.out.println(menu.server.getUsers().getUserById(id).getName() + " - " + menu.server.getBalanceById(id));
					} catch (UserNotFoundException e) {
						System.err.println("Error: Id not found!");
					} catch (InputMismatchException e) {
						System.err.println("Error: user ID must be an integer!");
					}
				}
				break;
				
				
				

				/*
				 * 
				 * 	PERFORM A TRANSFER
				 *  A sender user can give some money to a recipient user.
				 *  Error if:
				 *  			- amount <= 0
				 *  			- balance < amount
				 *  			- senderId or recipientId doesn't exist
				 *  			- senderId == recipientId
				 *  			- senderId or recipientId not numbers
				 *  
				 */
			case 3:
				System.out.print("Enter a sender ID, a recipient ID, and a transfer amount\n-> ");
				scan = new Scanner(System.in);
				int senderId = 0;
				int recipientId = 0;
				int amount = 0;
				try {
					if (scan.hasNext()) {
						senderId = scan.nextInt();
					}
					if (scan.hasNext()) {
						recipientId = scan.nextInt();
					}
					if (scan.hasNext()) {
						amount = scan.nextInt();
					}
					menu.server.addTransaction(senderId, recipientId, amount);
					System.out.println("The transfer is completed");
				} catch (UserNotFoundException e1) {
					System.err.println("Error: User not found!");
				} catch (IllegalTransactionException e2) {
					if (amount <= 0 || senderId == recipientId) System.err.println("Error: Forbidden transaction");
					else System.err.println("Error: Insufficient balance!");
				} catch (InputMismatchException e) {
					System.err.println("Error: IDs must be a integers!");
				}
				break;


				
				
				
				/*
				 * 	VIEW ALL TRANSACTION FOR A SPECIFIC USER
				 * 	Using user's id you can view all his transaction
				 *  Error if:
				 *  			- userId not found
				 * 
				 */
			case 4:
				System.out.print("Enter a user ID\n-> ");
				scan = new Scanner(System.in);
				int id;
				if (scan.hasNext()) {
					id = scan.nextInt();
					try {
						Transaction[] transList = menu.server.getTransfers(id);
						for (int i = 0; i < transList.length && transList[i] != null; i++) {
							System.out.print(transList[i].view(menu.server));
						}
					} catch (UserNotFoundException e) {
						System.err.println("Error: User not found!");
					}
				}
				break;
				
				
				


				/*
				 * 
				 * 	(DEV OPTION) REMOVE A TRANSFER
				 * 	Using userId and transId a developer can remove a
				 *  transaction from user's list
				 * 	Error if:
				 * 				- userId or transId do not exist
				 * 				- no developer mode is enable
				 * 				- userId not an integer
				 */
			case 5:
				if(!menu.devMode) {
					System.err.println("Error: Forbidden function!");
					break;
				}
				System.out.print("Enter a user ID and a transfer ID\n-> ");
				scan = new Scanner(System.in);
				String transId = null;
				int	userId = 0;
				try {
					if (scan.hasNext()) {
						userId = scan.nextInt();
					}
					if (scan.hasNext()) {
						transId = scan.next();
					}
					menu.server.removeTransaction(userId, UUID.fromString(transId));
				} catch (InputMismatchException e) {
					System.err.println("Error: User ID must be an integer!");
				} catch (UserNotFoundException e) {
					System.err.println("Error: User not found!");
				} catch (TransactionNotFoundException e) {
					System.err.println("Error: Transaction not found!");
				}
				break;
				
				
				
				/*
				 * 
				 * 	(DEV OPTION) CHECK TRANSFER VALIDITY
				 *  A developer can verify if all transactions are valid:
				 *  they must have:
				 *  				- positive balance
				 *  				- senderId and recipientId associated with a real user	
				 * 					- sender transaction debit flag
				 * 					- recipient transaction credit flag
				 * 
				 */
			case 6:
				if(!menu.devMode) {
					System.err.println("Error: Forbidden function!");
					break;
				}
				break;
				
				
				/*
				 * 
				 * 	FINISH EXECUTION
				 *  Program exit and delete all information about users
				 *  and transaction
				 * 
				 */
			case 7:
				menu.exit = true;
				break;
				
				
				/*
				 * 	UNKNOWN COMMAND
				 * 	Default option for all other command
				 * 
				 */
			default:
				System.err.println("Unknown Command!");
				break;
				
				
			}
		} while(!menu.exit);
		scan.close();
	}
}
