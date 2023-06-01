package module_01;

public class UsersArrayList implements UserList {
	static final int DEFAULT_ARRAY_SIZE = 10;
	int usersNumber;
	User[] users;
	
	public UsersArrayList() {
		this.users = new User[DEFAULT_ARRAY_SIZE];
		this.usersNumber = 0;
	}
	
	@Override
	public void addUser(User us) {
		if (this.getUsersNumber() == this.users.length) {
			User[] newUsers = new User[this.users.length * 3/2];
			for(int i = 0; i < this.getUsersNumber(); i++)
				newUsers[i] = this.users[i];
			this.users = newUsers;
		}
		this.users[this.getUsersNumber()] = us;
		this.usersNumber++;
	}

	@Override
	public User getUserById(int id) throws UserNotFoundException {
		for (int i = 0; i < this.getUsersNumber(); i++)
			if (this.users[i].getId() == id)
				return this.users[i];
		throw new UserNotFoundException();
	}

	@Override
	public User getUserByIndex(int index) {
		if (index < 0 || index >= this.getUsersNumber())
			return null;
		return this.users[index];
	}
	
	@Override
	public User[] getUsersList() {
		return this.users;
	}
	
	@Override
	public boolean existId(int id) {
		for (int i = 0; i < this.getUsersNumber(); i++)
			if (this.users[i].getId() == id)
				return true;
		return false;
	}

	@Override
	public int getUsersNumber() {
		return this.usersNumber;
	}	
}
