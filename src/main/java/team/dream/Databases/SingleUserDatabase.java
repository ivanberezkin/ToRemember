package team.dream.Databases;

import team.dream.shared.User;
import tools.jackson.core.type.TypeReference;
import tools.jackson.databind.ObjectMapper;

import java.io.File;
import java.util.List;

public class SingleUserDatabase {
    private static final SingleUserDatabase userDB = new SingleUserDatabase();
    private static List<User> userListInDB;
    private final ObjectMapper mapper = new ObjectMapper();
    private final String filename = "src/main/resources/UserList.json";

    private SingleUserDatabase() {
        userListInDB = readUserListFromFileJson();

        IO.println("SingleUserDB: Users in list: " + userListInDB.size());

    }

    public User findExistingUser(String username) {
        for (User u : userListInDB) {
            if (username.equalsIgnoreCase(u.getUserName())) {
                return u;
            }
        }
        return null;
    }

    public void addNewUser(String username){
        // det här kan vi ersätta med en factory som antingen anropas härifrån eller där denna metod anropas ifrån.
        userListInDB.add(new User(username));
        writeUserListToFileJson();
    }

    public static SingleUserDatabase getInstance() {
        return userDB;
    }

    private void writeUserListToFileJson() {
        mapper.writerWithDefaultPrettyPrinter().writeValue(new File(filename).toPath().toFile(), userListInDB);
        mapper.writerWithDefaultPrettyPrinter().writeValueAsString(userListInDB);
    }

    private List<User> readUserListFromFileJson() {
        return mapper.readValue(new File(filename), new TypeReference<List<User>>() {
        });

    }


}
