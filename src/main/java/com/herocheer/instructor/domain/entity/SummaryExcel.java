package com.herocheer.instructor.domain.entity;

import cn.afterturn.easypoi.excel.annotation.Excel;
import cn.afterturn.easypoi.excel.annotation.ExcelTarget;
import lombok.Data;

/**
 * @author gaorh
 * @create 2021-06-15
 */
@ExcelTarget("summaryexcel")
@Data
public class SummaryExcel {


    @Excel(name = "日期", orderNum = "1", mergeVertical = true, isImportField = "date")
    private String date;//当天日期

    @Excel(name = "能源类型", orderNum = "2", mergeVertical = true, isImportField = "type")
    private String type;         // 能源类型

    @Excel(name = "能源用量", orderNum = "3", mergeVertical = true, isImportField = "sum")
    private Double sum;          // 用量
}
