package app.user;

public class UserController {

    private UserDao userDao;

    public UserController(app.user.UserDao userDao) {
        this.userDao = userDao;
    }

    // Authenticate the user by hashing the inputted password using the stored salt,
    // then comparing the generated hashed password to the stored hashed password
    public  boolean authenticate(String username, String password) {
        if (username.isEmpty() || password.isEmpty()) {
            return false;
        }
        User user = userDao.getUserByUsername(username);
        if (user == null) {
            return false;
        }
        String hashedPassword = org.mindrot.jbcrypt.BCrypt.hashpw(password, user.getSalt());
        return hashedPassword.equals(user.getHashedPassword());
    }

    // This method doesn't do anything, it's just included as an example
    public  void setPassword(String username, String oldPassword, String newPassword) {
        if (authenticate(username, oldPassword)) {
            String newSalt = org.mindrot.jbcrypt.BCrypt.gensalt();
            String newHashedPassword = org.mindrot.jbcrypt.BCrypt.hashpw(newSalt, newPassword);
            // Update the user salt and password
        }
    }
}
