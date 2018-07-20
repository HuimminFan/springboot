package top.watech.springboot.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.watech.springboot.entity.User;
import top.watech.springboot.entity.Weibo;
import top.watech.springboot.repository.UserRepository;
import top.watech.springboot.repository.WeiboRepository;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by fhm on 2018/7/19.
 */
@RestController
public class WeiboController {

    @Autowired
    WeiboRepository weiboRepository;
    @Autowired
    UserRepository userRepository;

    //测试http://localhost:8080/getWeibo1/aa
    @GetMapping("/getWeibo1/{username}")
    public Weibo getUserWeibo(@PathVariable("username") String username){
        return this.weiboRepository.searchUserWeibo(username).get(1);
    }

    //测试http://localhost:8080/getWeibo2/aa
    //pageNo从0开始
    @GetMapping("/getWeibo2/{username}")
    public List<Weibo> weiboList(@PathVariable("username") String username){
        return this.weiboRepository.searchUserWeibo(username,new Sort(new Sort.Order(Sort.Direction.DESC,"weiboId")));
    }

    //测试http://localhost:8080/getWeibo3/aa
    @GetMapping("/getWeibo3")
    public Page<Weibo> simpleSearch(String username, String weiboText, int pageNo, int pageSize){
        User user = this.userRepository.getByUsernameIs(username);
        return this.weiboRepository.findByUserIsAndWeiboTextContaining(user,weiboText,new PageRequest(pageNo,pageSize));
    }


    @RequestMapping("/searchWeibo")
    public Page<Weibo> searchWeibo(final String username, final String weiboText, final Date startDate, final Date endDate, int pageNo, int pageSize) {
        Page<Weibo> page = this.weiboRepository.findAll(new Specification<Weibo>() {
            @Override
            public Predicate toPredicate(Root<Weibo> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                List<Predicate> predicates = new LinkedList<>();

                if (!StringUtils.isEmpty(username)) {
                    //Join有两种方式
//                    Join<Weibo,User> userJoin = root.join("user",JoinType.INNER);
//                    predicates.add(criteriaBuilder.equal(userJoin.get("username"), username));
                    predicates.add(criteriaBuilder.equal(root.get("user").get("username"),username));
                }
                if (!StringUtils.isEmpty(weiboText)) {
                    predicates.add(criteriaBuilder.like(root.get("weiboText"), "%" + weiboText + "%"));
                }
                if(startDate!=null){
                    predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("createDate").as(Date.class),startDate));
                }
                if(endDate != null){
                    predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get("createDate").as(Date.class),endDate));
                }
                return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
            }
        },new PageRequest(pageNo,pageSize));
        return page;
    }
}
