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
        } else if ((username.length() < 5 || username.length() > 15 || !isValidUsername(username))) {
            return false;
        } else if ((password.length() < 8 || password.length() > 15) || !isValidPassword(password)) {
            System.out.println("Error: invalid choice of password");
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

        if (username.isBlank() || matcherForUsername.find() || isBannedWord(username)) {
            return false;
        }

        return true;
    }

    private boolean isBannedWord(String input) {
        for (String word : blacklistedContent) {
            if (input.toLowerCase().contains(word)) {
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

        if (password.isBlank() || !matcherForPassword.find() || !matcherCheckForUppercase.find() || !matcherCheckForLowercase.find() ||
                !matcherCheckForNumbers.find() || isBannedWord(password)) {
            return false;
        }
        return true;
    }
}
