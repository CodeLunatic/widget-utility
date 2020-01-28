package com.cy.index.model.output;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.metadata.BaseRowModel;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ExcelOutput extends BaseRowModel {

    @ExcelProperty(index = 0, value = "键")
    private String key;

    @ExcelProperty(index = 1, value = "值")
    private String value;
}
