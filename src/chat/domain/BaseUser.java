package chat.domain;

public abstract class BaseUser implements User {
    private final int id;
    private final String name;

    protected BaseUser(int id, String name) {
        this.id = id;
        this.name = name;
    }

    @Override
    public int getId() {
        return id;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return id + ". " + name + " (" + getRole() + ")";
    }
}
