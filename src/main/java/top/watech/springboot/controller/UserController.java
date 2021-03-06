package top.watech.springboot.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import top.watech.springboot.entity.User;
import top.watech.springboot.repository.UserRepository;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by fhm on 2018/7/19.
 */
@RestController
public class UserController {
    @Autowired
    private UserRepository userRepository;
    @GetMapping("/users/{username}")
    public List<User> findUser1(@PathVariable("username") String username){
        List<User> users = this.userRepository.findByUsernameContaining(username);
        return users;
    }

    @GetMapping("/specuser")
    public User findUser2(String username,String userpwd){
        User user = userRepository.getByUsernameIsAndUserpwdIs(username,userpwd);
        return user;
    }

    @GetMapping("/insert")
    public User userSave(User user){
        User save = userRepository.save(user);
//        userRepository.getByUsernameIsAndUserpwdIs()
        return save;
    }

    public void saveAll(){
        List<User> users = new ArrayList<>();

        for (int i = 'a';i<='z';i++){
            User user = new User();
            user.setUsername();
        }
    }
}
