package meng.xing.service;

import meng.xing.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface UserService {
    boolean register(User user);
    public User findUserByUsername(String username);
    public Page<User> findAllUsers(Pageable pageable);
}
