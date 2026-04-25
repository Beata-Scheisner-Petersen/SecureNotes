package securenotes.repository.interfaces;

import securenotes.model.User;

public interface IUserRepository {
    User findByUsername(String username);
    void save(User user);
}
