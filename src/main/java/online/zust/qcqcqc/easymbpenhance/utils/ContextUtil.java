package online.zust.qcqcqc.easymbpenhance.utils;

import online.zust.qcqcqc.services.exception.ServiceException;
import online.zust.qcqcqc.services.module.user.entity.User;
import online.zust.qcqcqc.services.utils.CurrentUserGetter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

/**
 * @author qcqcqc
 * 实现框架中的CurrentUserGetter接口，用于获取当前登录用户，对实体类的信息进行填充
 */
@Component
public class ContextUtil implements CurrentUserGetter {

    @Override
    public Long getCurrentUser() {
        User userInfo = getUserInfo();
        if (userInfo == null) {
            return 0L;
        }
        return userInfo.getId();
    }

    public static User getUserInfo() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null) {
            return null;
        }
        Object principal = authentication.getPrincipal();
        if (!(principal instanceof User user)) {
            return null;
        }
        return user;
    }

    public static User getCurrentUserOrThrow() {
        User currentUser = getUserInfo();
        if (currentUser == null) {
            throw new ServiceException("用户未登录", 403);
        }
        return currentUser;
    }
}
