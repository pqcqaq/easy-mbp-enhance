package online.zust.qcqcqc.easymbpenhance.module.user;

import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import online.zust.qcqcqc.services.entity.dto.PageResult;
import online.zust.qcqcqc.services.entity.dto.Result;
import online.zust.qcqcqc.services.module.log.annotation.OperationLog;
import online.zust.qcqcqc.services.module.user.entity.User;
import online.zust.qcqcqc.services.module.user.entity.dto.ChangePasswordDto;
import online.zust.qcqcqc.services.module.user.entity.dto.LoginParam;
import online.zust.qcqcqc.services.module.user.entity.dto.RegisterParam;
import online.zust.qcqcqc.services.module.user.entity.dto.UserPublish;
import online.zust.qcqcqc.services.module.user.entity.vo.UserVo;
import online.zust.qcqcqc.services.module.user.service.UserService;
import online.zust.qcqcqc.utils.utils.BeanConvertUtils;
import online.zust.qcqcqc.easymbpenhance.utils.ContextUtil;
import online.zust.qcqcqc.easymbpenhance.utils.StringUploadUtils;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;


/**
 * @author qcqcqc
 */
@RestController
@RequestMapping("/user")
@Slf4j
public class UserController {
    @Resource
    private UserService userService;

    /**
     * 开启应用
     *
     * @return 开启成功
     */
    @GetMapping("/open")
    @OperationLog("'开启应用'")
    public Result<String> openApplication() {
        return Result.success(userService.openApplication());
    }

    /**
     * 关闭应用
     */
    @GetMapping("/close")
    @OperationLog("'关闭应用'")
    public Result<String> closeApplication() {
        return Result.success(userService.closeApplication());
    }

    /**
     * 注册
     *
     * @param registerParam 注册信息
     * @return 是否注册成功
     */
    @PostMapping("/register")
    @OperationLog("'注册' + #registerParam.username + ' 结果为：' + #returnValue.msg")
    public Result<String> register(@RequestBody @Valid RegisterParam registerParam) {
        boolean register = userService.register(registerParam);
        return Result.success(register ? "注册成功" : "注册失败");
    }

    /**
     * 登录
     *
     * @param loginParam 登录信息
     * @return 用户信息
     */
    @PostMapping("/login")
    @OperationLog("'用户登录' + #loginParam.username")
    public Result<UserVo> login(@RequestBody @Valid LoginParam loginParam) {
        return Result.success("登录成功", userService.login(loginParam));
    }

    /**
     * 获取当前用户
     *
     * @return 用户信息
     */
    @GetMapping("/user")
    @PreAuthorize("hasRole('SERVER')")
    public Result<UserVo> getCurrentUser() {
        User currentUserOrThrow = ContextUtil.getCurrentUserOrThrow();
        UserVo userVo = BeanConvertUtils.objectConvent(currentUserOrThrow, UserVo.class);
        return Result.success(userVo);
    }

    /**
     * 退出
     *
     * @param httpServletRequest httpServletRequest
     * @return 是否退出成功
     */
    @PreAuthorize("hasRole('SERVER')")
    @PostMapping("/logout")
    @OperationLog("'用户退出登录'")
    public Result<String> logout(HttpServletRequest httpServletRequest) {
        Boolean logout = userService.logout(httpServletRequest);
        if (!logout) {
            return Result.error(500, "退出失败");
        }
        return Result.success("退出成功");
    }

    /**
     * 刷新token
     *
     * @param httpServletRequest httpServletRequest
     * @return 用户信息
     */
    @PreAuthorize("hasRole('SERVER')")
    @PostMapping("/refreshToken")
    @OperationLog("'刷新token'")
    public Result<UserVo> refreshToken(HttpServletRequest httpServletRequest) {
        UserVo userVo = userService.refreshToken(httpServletRequest);
        return Result.success("刷新成功", userVo);
    }

    /**
     * 获取用户列表
     *
     * @param page       分页大小
     * @param size       页码
     * @param role       角色
     * @param fuzzyQuery 模糊查询
     * @return 用户列表
     */
    @GetMapping("/list")
    @PreAuthorize("hasRole('ADMIN')")
    public Result<PageResult<UserVo>> getUserList(@RequestParam(required = false, defaultValue = "1") Integer page,
                                                  @RequestParam(required = false, defaultValue = "1") Integer size,
                                                  @RequestParam(required = false) String role,
                                                  @RequestParam(required = false) String fuzzyQuery) {
        return Result.success(userService.getUserList(page, size, role, fuzzyQuery));
    }

    /**
     * 更新用户信息
     *
     * @param userPublish 用户信息
     * @return 是否更新成功
     */
    @PutMapping("/upsert")
    @PreAuthorize("hasRole('ADMIN')")
    @OperationLog("'更新用户信息' + #userPublish.id + ' 结果为：' + #returnValue.msg")
    public Result<String> upsertUser(@RequestBody @Valid UserPublish userPublish) {
        String id = userPublish.getId();
        String msg;
        if (StringUploadUtils.checkIdExist(id)) {
            msg = userService.alterUser(userPublish);
        } else {
            msg = userService.addUser(userPublish);
        }
        return Result.success(msg);
    }

    /**
     * 修改密码
     *
     * @param userPublish 用户信息
     * @return 是否修改成功
     */
    @PutMapping("/changePassword")
    @PreAuthorize("hasRole('ADMIN')")
    @OperationLog("'修改密码' + #userPublish.id + ' 结果为：' + #returnValue.msg")
    public Result<String> changePassword(@RequestBody @Valid ChangePasswordDto userPublish) {
        return Result.success(userService.changePassword(userPublish));
    }

    /**
     * 删除用户
     *
     * @param id 用户id
     * @return 是否删除成功
     */
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @OperationLog("'删除用户' + #id + ' 结果为：' + #returnValue.msg")
    public Result<String> deleteUser(@PathVariable String id) {
        String msg = userService.deleteUser(id);
        return Result.success(msg);
    }

    /**
     * 启用用户
     *
     * @param id 用户id
     * @return 是否启用成功
     */
    @PutMapping("/enable/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @OperationLog("'启用用户' + #id + ' 结果为：' + #returnValue.msg")
    public Result<String> disableOrEnableUser(@PathVariable String id,
                                              @RequestParam(required = false, defaultValue = "true") boolean enable) {
        String msg;
        if (enable) {
            msg = userService.enableUser(id);
        } else {
            msg = userService.disableUser(id);
        }
        return Result.success(msg);
    }
}
