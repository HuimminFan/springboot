package top.watech.springboot.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.watech.springboot.bean.Student;

/**
 * Created by wuao.tp on 2018/7/9.
 */
@RestController
@RequestMapping("/test")
public class HelloController {
    @Autowired
    private Student student;
    @RequestMapping("/hello")
    public Student hello(){
        return student;
    }
}
