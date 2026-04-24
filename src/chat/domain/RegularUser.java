package chat.domain;

public class RegularUser extends BaseUser {
    public RegularUser(int id, String name) {
        super(id, name);
    }

    @Override
    public UserRole getRole() {
        return UserRole.REGULAR;
    }

    @Override
    public boolean canBroadcast() {
        return false;
    }
}
