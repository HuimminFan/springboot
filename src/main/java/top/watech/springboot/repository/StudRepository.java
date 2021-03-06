package top.watech.springboot.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import top.watech.springboot.entity.Student;


/**
 * Created by wuao.tp on 2018/7/18.
 */
public interface StudRepository extends JpaRepository<Student,Integer> {
    Student getByUsernameIsAndPwdIs(String username, String pwd);
}
