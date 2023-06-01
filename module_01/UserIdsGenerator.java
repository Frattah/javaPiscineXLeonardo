package module_01;

public final class UserIdsGenerator {
	private static UserIdsGenerator INSTANCE = null;
	int	lastId;

	private UserIdsGenerator() {
		this.lastId = 0;
	}

	int generateId() {
		this.lastId++;
		return this.lastId;
	}

	public static UserIdsGenerator getInstance() {
		if(INSTANCE == null) {
			INSTANCE = new UserIdsGenerator();
		}
		return INSTANCE;
	}
}
