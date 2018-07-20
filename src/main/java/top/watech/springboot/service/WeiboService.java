package top.watech.springboot.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import top.watech.springboot.entity.User;
import top.watech.springboot.entity.Weibo;
import top.watech.springboot.repository.WeiboRepository;

import java.util.Date;
import java.util.List;

//声明式事务
public class WeiboService {
    @Autowired
    private WeiboRepository weiboRepository;

    @Transactional(readOnly = false,isolation = Isolation.READ_COMMITTED)
    public List<Weibo> importWeiboList(List<Weibo> weibos, User user){
        int index = 0;
        Date nowDateTime = new Date(System.currentTimeMillis());
        for (Weibo weiboItem: weibos) {
            weiboItem.setUser(user);
            weiboItem.setCreateDate(nowDateTime);
            if(5<=index++){
                throw new RuntimeException("Weibo out of limit!!!");
            }
            this.weiboRepository.save(weiboItem);
        }
        return weibos;
    }

}
