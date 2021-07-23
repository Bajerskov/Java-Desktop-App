package user;

import java.util.Objects;

public class User {
    private String name;
    private int id;

    public User(String name, int id) {
        this.name = name;
        this.id = id;
    }

    public User(String raw) {
        //we trust our own input
        String[] strings = raw.split(" ");
        this.name = strings[1];
        this.id = Integer.parseInt(strings[0]);
    }

    public String getName() {
        return name;
    }

    public int getId() {
        return id;
    }

    public String saveString() {
        return id + " " + name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return id == user.id;
    }

    @Override
    public int hashCode() {

        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return this.id + ". "+this.name;
    }
}
