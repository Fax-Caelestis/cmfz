package com.lxx.entity;


import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.write.style.ColumnWidth;
import com.alibaba.excel.annotation.write.style.ContentRowHeight;
import com.alibaba.excel.annotation.write.style.HeadRowHeight;
import com.alibaba.fastjson.annotation.JSONField;
import com.lxx.data.ImgConverter;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Id;
import java.io.Serializable;
import java.util.Date;
@Data
@AllArgsConstructor
@NoArgsConstructor
@ContentRowHeight(70)
@HeadRowHeight(20)
@ColumnWidth(25)
public class Banner implements Serializable {
  @Id
  @ExcelProperty(value = "ID")
  private String id;
  @ExcelProperty(value = "标题")
  private String title;
  @ExcelProperty(value = "图片",converter = ImgConverter.class)
  private String url;
  @ExcelProperty(value = "超链接")
  private String href;
  @ExcelProperty(value = "创建日期")
  @DateTimeFormat(pattern = "yyyy-MM-dd")
  @JSONField(format = "yyyy-MM-dd")
  private Date createDate;
  @ExcelProperty(value = "描述")
  private String descript;
  @ExcelProperty(value = "状态")
  private String status;



}
