package meng.xing.api;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 测试API
 */
@RestController
@RequestMapping("/")
public class TestController {
    /**
     * 默认界面，不需要权限，SecurityConfigure类里面配置
     * @return
     */
    @GetMapping("/")
    public String index() {
        return "hello word!";
    }

    /**
     * 用来测试security
     * 请先获取从auth下的api获取token
     * 建议用postman操作
     * @return
     */
    @GetMapping("/test")
    public String test() {
        StringBuffer str = new StringBuffer();
        str.append( "username:" + ((UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername());
        str.append("password:"+((UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getPassword());
        str.append("details:"+SecurityContextHolder.getContext().getAuthentication().getDetails().toString());
        SecurityContextHolder.getContext().getAuthentication().getAuthorities().forEach(auth->str.append("role:"+auth));

        return str.toString();
    }
}
