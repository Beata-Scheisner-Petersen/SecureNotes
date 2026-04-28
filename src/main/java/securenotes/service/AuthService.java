package securenotes.service;

import org.mindrot.jbcrypt.BCrypt;
import securenotes.logging.Logger;
import securenotes.model.User;
import securenotes.repository.interfaces.IUserRepository;
import securenotes.repository.mysql.mySqlBannedWordRepository;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AuthService {
    private final IUserRepository userRepository;
    private final mySqlBannedWordRepository bannedWordRepository = new mySqlBannedWordRepository();
    private final List<String> blacklistedContent = bannedWordRepository.getBannedWords();
    private final Exception exception = new Exception();

    public AuthService(IUserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User login(String username, String password) {
        User user = userRepository.findByUsername(username);

        if (user == null) {
            Logger.log("Login failed on user:" + username, exception);
            return null;
        }

        if (BCrypt.checkpw(password, user.getPassword())) {
            return user;
        } else {
            Logger.log("Login failed on user: " + user, exception);
            return null;
        }
    }

    public boolean registerNewUser (String username, String password) {
        if (userRepository.findByUsername(username) != null) {
            Logger.log("Register new user failed. User already exist", exception);
            System.out.println("User already exist");
            return false;
        } else if ((username.length() < 5 || username.length() > 15)) {
            System.out.println("Username needs to be between 5 - 15 characters");
            return false;
        } else if (!isValidUsername(username)) {
            return false;
        } else if ((password.length() < 8 || password.length() > 15)) {
            System.out.println("Password needs to be between 8 - 15 characters");
            return false;
        } else if (!isValidPassword(password)) {
            return false;
        } else {
            User user = new User(username, BCrypt.hashpw(password, BCrypt.gensalt()));
            userRepository.save(user);
            return true;
        }
    }

    private boolean isValidUsername(String username) {
        Pattern specialCharacters = Pattern.compile("[^a-z0-9_]", Pattern.CASE_INSENSITIVE);
        Matcher matcherForUsername = specialCharacters.matcher(username);

        if (username.isBlank() || matcherForUsername.find()) {
            System.out.println("Username can't contain special characters or be empty");
            Logger.log("username is empty or have special character", exception);
            return false;
        } else if (isBannedWord(username)) {
            System.out.println("Username can't contain banned words");
            Logger.log("username contains banned word", exception);
            return false;
        }

        return true;
    }

    private boolean isBannedWord(String input) {
        for (String word : blacklistedContent) {
            if (input.toLowerCase().matches(word)) {
                return true;
            }
        }
        return false;
    }

    private boolean isValidPassword(String password) {
        Pattern specialCharacters = Pattern.compile("[^a-z0-9]", Pattern.CASE_INSENSITIVE);
        Pattern uppercaseCharacters = Pattern.compile("[A-Z]");
        Pattern lowercaseCharacters = Pattern.compile("[a-z]");
        Pattern numberCharacter = Pattern.compile("[0-9]");

        Matcher matcherForPassword = specialCharacters.matcher(password);
        Matcher matcherCheckForUppercase = uppercaseCharacters.matcher(password);
        Matcher matcherCheckForLowercase = lowercaseCharacters.matcher(password);
        Matcher matcherCheckForNumbers = numberCharacter.matcher(password);

        if (password.isBlank()) {
            Logger.log("Password can't be blank", exception);
            System.out.println("Password can't be blank");
            return false;
        } else if (!matcherForPassword.find()) {
            Logger.log("Password don't have special character", exception);
            System.out.println("Password don't have special character");
            return false;
        } else if (!matcherCheckForUppercase.find()) {
            Logger.log("Password don't contains uppercase letter", exception);
            System.out.println("Password don't contains uppercase letter");
            return false;
        } else if (!matcherCheckForLowercase.find()) {
            Logger.log("Password don't contains lowercase letter", exception);
            System.out.println("Password don't contains lowercase letter");
            return false;
        } else if (!matcherCheckForNumbers.find()) {
            Logger.log("Password don't contains a number", exception);
            System.out.println("Password don't contains a number");
            return false;
        } else if (isBannedWord(password)) {
            Logger.log("Password contains banned word", exception);
            System.out.println("Password contains banned word");
            return false;
        }
        return true;
    }

    public boolean changePassword(User user, String oldPassword, String newPassword) {
        if (!BCrypt.checkpw(oldPassword, user.getPassword())) {
            Logger.log("Old password is wrong", exception);
            return false;
        }
        if ((newPassword.length() < 8 || newPassword.length() > 15) || !isValidPassword(newPassword)) {
            Logger.log("Invalid password format", exception);
            return false;
        }
        user.setPassword(BCrypt.hashpw(newPassword, BCrypt.gensalt()));
        userRepository.changePassword(user);
        return true;

    }
}
