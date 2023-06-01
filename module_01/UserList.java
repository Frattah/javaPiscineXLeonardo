package module_01;

class UserNotFoundException extends Exception {}

public interface UserList {
	
	void addUser(User u);
	
	User getUserById(int id) throws UserNotFoundException;
	
	User getUserByIndex(int index);
	
	int getUsersNumber();

	boolean existId(int id);

	User[] getUsersList();
}
