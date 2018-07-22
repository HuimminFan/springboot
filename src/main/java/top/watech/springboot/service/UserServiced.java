package top.watech.springboot.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import top.watech.springboot.entity.User;
import top.watech.springboot.repository.UserRepository;

import java.util.List;

@Service
public class UserServiced {

    @Autowired
    UserRepository userRepository;

    @Transactional
    public void saveUsers(List<User> users){
        userRepository.saveAll(users);
    }
}
