package team.dream.ServerSide;

import team.dream.shared.User;

import java.util.ArrayList;

public class SingleUserDatabase {
    private static final SingleUserDatabase userDB = new SingleUserDatabase();
    private static ArrayList<User> userListInDB;

    private SingleUserDatabase(){

        //TODO ska populera userList on start, läsa från fil. men skapar fake tillvidare
        userListInDB = new ArrayList<>();
        userListInDB.add(new User("Ivan"));
        userListInDB.add(new User("Pelle"));
        userListInDB.add(new User("Jenny"));
        IO.println("SingleuserDB: Users in list: " + userListInDB.size());
    }

    public User findExistingUser(String username){
        for(User u : userListInDB){
            if(username.equalsIgnoreCase(u.getUserName())){
                return u;
            }
        }
        return null;
    }

    public static SingleUserDatabase getUserDB(){
        return userDB;
    }



}
