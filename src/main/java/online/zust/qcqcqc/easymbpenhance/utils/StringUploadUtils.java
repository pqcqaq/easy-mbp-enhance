package online.zust.qcqcqc.easymbpenhance.utils;

/**
 * @author qcqcqc
 * @date 2024/03
 * @time 15-54-12
 */
public class StringUploadUtils {
    public static boolean checkIdExist(String id) {
        // 判断id是否合法，这里的逻辑是和前端约定好的，如果id为null或者空字符串或者-1或者null字符串，那么就认为id不存在
        return !(id == null || id.trim().isEmpty() || "-1".equals(id) || "null".equals(id));
    }

    public static boolean isParamConcluded(String input) {
        // 判断参数是否包含内容，这里的逻辑是和前端约定好的，如果参数为null或者空字符串或者$null字符串，那么就认为参数不完整
        return input != null && !input.trim().isEmpty() && !"$null".equals(input);
    }
}
