package chat.domain;

public class AdminUser extends BaseUser {
    public AdminUser(int id, String name) {
        super(id, name);
    }

    @Override
    public UserRole getRole() {
        return UserRole.ADMIN;
    }

    @Override
    public boolean canBroadcast() {
        return true;
    }
}
