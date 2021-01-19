package com.herocheer.instructor.utils;

import com.herocheer.common.utils.StringUtils;

/**
 * @author chenwf
 * @desc
 * @date 2021/1/18
 * @company 厦门熙重电子科技有限公司
 */
public class ExcelUtils {
    public static boolean isRequired(Object title, Object value) {
        if(title != null && title.toString().contains("*")){
            if(StringUtils.isBlankIfStr(value)){
                return false;
            }
        }
        return true;
    }
}
