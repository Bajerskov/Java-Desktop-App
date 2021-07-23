package file;

import user.User;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashSet;
import java.util.Set;

public class FileUser extends Files {

    Set<User> userList;


    public FileUser() {
        super("user/userlist.txt");

    }

    public Set<User> getuserList() {
        return userList;
    }

    @Override
    public Object splitLine(String currentline) {
        if (userList == null)
            userList = new HashSet<User>();

        String[] splitString =  currentline.split(" ");
        int id = Integer.parseInt(splitString[0]);
        String name = splitString[1].replaceAll("_", " ");
        this.userList.add(new User(name, id));
        return null;
    }



    public boolean edit(User oldUser, User newUser) {
        String replace = oldUser.getId() + " " + oldUser.getName();
        String add = oldUser.getId() + " " + newUser.getName();
        fileAction update = fileAction.Update;

        return super.update(update, replace, add);
    }

    public boolean remove(int id, String name) {
        String find = id + " " + name;
        fileAction delete = fileAction.Delete;

        return super.update(delete, find);
    }

    public boolean appendToFile(String user) {
        try(BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(super.getFile(), true));
            PrintWriter printWriter = new PrintWriter(bufferedWriter))
        {
            printWriter.println(user);
        } catch (IOException e) {
            System.out.println(e.getMessage());
            return false;
        }

        userList.clear();
        read();

        return true;
    }
}
