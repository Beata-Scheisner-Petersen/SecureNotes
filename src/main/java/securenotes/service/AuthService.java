package securenotes.service;

import org.mindrot.jbcrypt.BCrypt;
import securenotes.logging.ErrorLogger;
import securenotes.model.AuthSession;
import securenotes.model.User;
import securenotes.repository.mysql.mySqlBannedWordRepository;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AuthService {
    private final IUserRepository userRepository;
    private final mySqlBannedWordRepository bannedWordRepository = new mySqlBannedWordRepository();
    private final List<String> blacklistedContent = bannedWordRepository.getBannedWords();
    private FormatDateTimeService dateTimeService;

    public AuthService(IUserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public AuthSession login(String username, String password) {
        User user = userRepository.findByUsername(username);
        if (user == null) {
            return null;
        }

        if (user.getUsername().equals(username) && BCrypt.checkpw(password, user.getPassword())) {
            return new AuthSession(user.getId(), user.getUsername(), dateTimeService.FormatDateTime());
        } else {
            Exception exception = new Exception();
            ErrorLogger.log("Login failed", exception);
            System.out.println("Wrong username or password");
            return null;
        }
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
        } else if ((username.length() < 5 || username.length() > 15 || !isValidUsername(username))) {
            System.out.println("Error: invalid choice of username");
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
