package user;

import file.FileUser;
import javafx.collections.FXCollections;
import javafx.collections.ObservableArray;
import javafx.collections.ObservableList;
import javafx.scene.control.MenuItem;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

public class Users {

    FileUser userFile = new FileUser();

    //Create some lists to hold user information
    List<User> users = new ArrayList<>();

    public Users() {
        userFile.getuserList().forEach((v)-> {
            users.add(v);
        });
    }

    //Return the NYUsers list
    public List<User> getUsers() {
        return users;
    }


    public List<String> getuserString() {
        ArrayList<String> strings = new ArrayList<>();
        getUsers().forEach((user) -> strings.add(user.toString()));
        return strings;
    }

    public List<MenuItem> menuitems() {
        ArrayList<MenuItem> items = new ArrayList<>();
        getUsers().forEach((v)-> items.add(new MenuItem(v.getName())));
        return items;
    }

    //delete a user from the text file.
    public void deleteUser(User user) {
        if(userFile.remove(user.getId(), user.getName())) {
            System.out.println("Usercontrol: \""+user.getName()+"\" was deleted");
        } else {
            System.out.println("Could not delete user: \""+user.getName()+"\"");
        }
        users.remove(user);

    }
    //Insert user to the text file.
    public void insertUser(String input) {
        int id = 1;
        User lastElement = null;
        if (!users.isEmpty()) {
            lastElement = users.get(users.size() - 1);
            id = lastElement.getId() + 1;
        }

        //insert new user
        userFile.appendToFile(id + " " + name)

        users.clear();
        userFile.getuserList().forEach((v)-> users.add(v));
    }

    //edit/update a user
    public void editUser(int id, String oldName, String newName) {
        User oldUser = new User(oldName, id);
        User newUser = new User(newName, id);

        userFile.edit(oldUser, newUser)
    }
}
