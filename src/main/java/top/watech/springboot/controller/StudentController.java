package top.watech.springboot.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import top.watech.springboot.entity.ReqUser;
import top.watech.springboot.entity.RespCode;
import top.watech.springboot.entity.RespEntity;
import top.watech.springboot.entity.Student;
import top.watech.springboot.repository.StudRepository;


/**
 * Created by wuao.tp on 2018/7/9.
 */
@RestController
public class StudentController{
    @Autowired
    StudRepository studRepository;

    //测试数据//http://localhost:8080/student/1
    @GetMapping("/student/{id}")
    public Student getStudent(@PathVariable("id") Integer id){
        Student student = studRepository.findById(id).get();
        return student;
    }

    //测试数据 http://localhost:8080/student?lastName=haha&email=aa
    @PostMapping("/student")
    public Student insertStudent(Student student){
        Student save = studRepository.save(student);
        return save;
    }


    @PostMapping("/login")
    public RespEntity login(@RequestBody ReqUser reqUser){
        Student student = new Student();
        if (reqUser!=null)
        {
            student.setUsername(reqUser.getUsername());
            student.setPwd(reqUser.getPwd());
        }
        Student student1 = studRepository.getByUsernameIsAndPwdIs(student.getUsername(), student.getPwd());
        if (student1!=null){
           return new RespEntity(RespCode.SUCCESS,student1);
       }else {
            RespCode respCode=RespCode.WARN;
            respCode.setMsg("用户或密码错误");
            respCode.setCode(-1);
            return new RespEntity(respCode,student1);
       }

    }


}
