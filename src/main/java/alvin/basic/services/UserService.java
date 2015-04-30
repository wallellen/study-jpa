package alvin.basic.services;

import alvin.basic.entities.User;
import alvin.basic.entities.UserInfo;
import alvin.basic.repositories.UserRepository;
import com.google.inject.persist.Transactional;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.List;
import java.util.Optional;

@Singleton
public class UserService {
    private UserRepository userRepository;

    @Inject
    public UserService(UserRepository userRepository1) {
        this.userRepository = userRepository1;
    }

    @Transactional
    public void save(User user) {
        userRepository.save(user);
    }

    @Transactional
    public void save(UserInfo userInfo) {
        userRepository.em().persist(userInfo);
    }

    @Transactional
    public void delete(User user) {
        userRepository.delete(user);
    }

    public Optional<User> findById(int id) {
        return userRepository.find(id);
    }

    @Transactional
    public void saveAll(List<User> users) {
        users.forEach(userRepository::save);
    }

    public List<User> findAll() {
        return userRepository.findAll();
    }
}
