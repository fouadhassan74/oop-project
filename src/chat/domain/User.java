package chat.domain;

public interface User {
    int getId();

    String getName();

    UserRole getRole();

    boolean canBroadcast();
}
