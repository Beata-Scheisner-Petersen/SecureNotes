package securenotes.service;

import org.mindrot.jbcrypt.BCrypt;
import securenotes.logging.ErrorLogger;
import securenotes.model.AuthSession;
import securenotes.model.User;
import securenotes.repository.mysql.mySqlUserRepository;
public class AuthService {
    private final mySqlUserRepository userRepository;

    public AuthService(mySqlUserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public AuthSession login(String username, String password) {
        User user = userRepository.findByUsername(username);
        if (user == null) {
            return null;
        }

        if (user.getUsername().equals(username) && user.getPassword().equals(hashedPassword(password))) {
            return new AuthSession(user.getId(), user.getUsername());
        } else {
            Exception exception = new Exception();
            ErrorLogger.log("Login failed", exception);
            System.out.println("Wrong username or password");
            return null;
        }
    }

    private String hashedPassword (String password) {
        return BCrypt.hashpw(password, BCrypt.gensalt());
    }

    public void logout (AuthSession session) {
        if (session != null) {
            session.logout();
        }
    }

    public boolean registerNewUser (String username, String password) {
        if (userRepository.findByUsername(username) != null) {
            System.out.println("User already exist");
            return false;
        }

        User user = new User(username, hashedPassword(password));
        userRepository.save(user);
        return true;
    }
}
