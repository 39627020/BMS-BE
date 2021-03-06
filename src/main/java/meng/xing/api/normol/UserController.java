package meng.xing.api.normol;

import meng.xing.entity.User;
import meng.xing.entity.UserRole;
import meng.xing.service.UserRoleService;
import meng.xing.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;

/**
 * user管理api
 */
@RestController
@RequestMapping("/users")
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    UserRoleService userRoleService;

    /**
     * username获取user信息
     * 鉴权：当前用户 or ADMIN
     *
     * @param username
     * @return 成功：json 200; 失败：json 403
     */
    @GetMapping("/{username}")
    @PreAuthorize("#username == authentication.name or hasRole('ADMIN')")
    public Map<String, Object> getUserByPathVariableUsername(@PathVariable("username") String username) {

        User _user = userService.findUserByUsername(username);
        Map<String, Object> user = new HashMap<>();
        user.put("id", _user.getId());
        user.put("username", _user.getUsername());
        Map<String, Object> permissions = new HashMap<>();
        permissions.put("roles", _user.getRoles().stream().map(userRole -> userRole.getRole()).collect(Collectors.toList()));
        //todo这是控制菜单的路径，有时间移动后台
        permissions.put("visit", "1,3,4,5");
        user.put("permissions", permissions);
        Map<String, Object> data = new HashMap<>();
        data.put("user", user);
        return data;
    }

    /**
     * 分页user查询
     * 鉴权：ADMIN
     *
     * @param page     当前页面
     * @param pageSize 每页大小
     * @param sort     排序字段
     * @param order    排列顺序 ASC or DESC
     * @return 成功：json 200; 失败：json 403
     */
    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    //需要ADMIN权限,有个天坑：hasAuthority('ROLE_ADMIN') means the the same as hasRole('ADMIN')
    public Page<User> getAllUsers(@RequestParam(value = "page", required = false, defaultValue = "1") Integer page,
                                  @RequestParam(value = "pageSize", required = false, defaultValue = "10") Integer pageSize,
                                  @RequestParam(value = "sort", defaultValue = "id") String sort,
                                  @RequestParam(value = "order", defaultValue = "asc") String order,
                                  @RequestParam(value = "username", required = false) String username) {
        System.out.println(username);
        Sort _sort = new Sort(Sort.Direction.fromString(order), sort);
        //传来的页码是从1开始，而服务器从1开始算
        Pageable pageable = new PageRequest(page - 1, pageSize, _sort);
        return userService.findAllUsers(pageable);
    }

    /**
     * 修改用户
     *
     * @param id
     * @param map
     */
    @PatchMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public boolean update(@PathVariable("id") Long id, @RequestBody Map<String, Object> map) {

        User user = userService.findUserById(id);

        ArrayList roles = (ArrayList) map.get("roles");
        if (roles != null) {
            Set<UserRole> _roles = new HashSet<>();
            roles.forEach(
                    (role) -> _roles.add(userRoleService.findUserRoleByRole((String) role)));
            user.setRoles(_roles);
        }

        user.setNickName(map.get("nickName").toString());
        user.setFemale((boolean) map.get("female"));
        user.setAge((int) map.get("age"));
        user.setAddress(map.get("address").toString());
        user.setEmail(map.get("email").toString());
        user.setPhone(map.get("phone").toString());
        return userService.updateUser(user);
    }

    /**
     * 删除用户
     *
     * @param id
     * @param
     */
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public boolean delete(@PathVariable("id") Long id) {
        return userService.deleteUserById(id);
    }

    /**
     * 批量删除用户
     * 前端传来ids 数组
     *
     * @param
     */
    @DeleteMapping
    @PreAuthorize("hasRole('ADMIN')")
    public void delete(@RequestBody Map<String, ArrayList<Long>> map) {
        map.get("ids").forEach(id -> userService.deleteUserById(id));
    }
}