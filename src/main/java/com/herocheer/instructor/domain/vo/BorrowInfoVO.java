package com.herocheer.instructor.domain.vo;

import cn.afterturn.easypoi.excel.annotation.ExcelTarget;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Objects;

/**
 * 借用信息VO
 *
 * @author gaorh
 * @create 2021-06-17
 */
@ExcelTarget("courseStatis")
@ApiModel(description = "课程服务时长统计VO")
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class BorrowInfoVO {
    @ApiModelProperty("借用开始时段 (时分)")
    private String borrowBeginTime;
    @ApiModelProperty("借用结束时段 (时分)")
    private String borrowEndTime;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BorrowInfoVO that = (BorrowInfoVO) o;
        return Objects.equals(borrowBeginTime, that.borrowBeginTime) &&
                Objects.equals(borrowEndTime, that.borrowEndTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(borrowBeginTime, borrowEndTime);
    }
}
