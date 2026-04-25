package securenotes.repository.interfaces;

import securenotes.model.User;

public interface IUserRepository {
    User findById(int id);
    User findByUsername(String username);
    void save(User user);
}
