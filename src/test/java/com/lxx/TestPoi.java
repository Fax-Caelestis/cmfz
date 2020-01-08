package com.lxx;

import com.lxx.dao.BannerDao;
import com.lxx.entity.Banner;
import org.apache.poi.hssf.usermodel.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.List;

@SpringBootTest(classes = CmfzApplication.class)
@RunWith(SpringRunner.class)
public class TestPoi {
    @Autowired
    BannerDao bannerDao;
    @Test
    public void name() {
        //查所有数据
        List<Banner> list = bannerDao.selectAll();
        // 1. 创建Excle文档
        HSSFWorkbook workbook = new HSSFWorkbook();
        // 2. 创建一个工作簿
        HSSFSheet sheet = workbook.createSheet();
        //  创建表头
        HSSFRow row = sheet.createRow(0);
        String[] str = {"ID","标题","图片","超链接","创建时间","描述","状态"};
        for (int i = 0; i < str.length; i++) {
            String s = str[i];
            row.createCell(i).setCellValue(s);
        }
        // 通过workbook对象获取样式对象
        HSSFCellStyle cellStyle = workbook.createCellStyle();
        // 通过workbook对象获取数据格式化处理对象
        HSSFDataFormat dataFormat = workbook.createDataFormat();
        // 指定格式化样式 如 yyyy-MM-dd
        short format = dataFormat.getFormat("yyyy-MM-dd");
        // 为样式对象 设置格式化处理
        cellStyle.setDataFormat(format);
        for (int i = 0; i < list.size(); i++) {
            Banner banner = list.get(i);
            HSSFRow row1 = sheet.createRow(i + 1);
            row1.createCell(0).setCellValue(banner.getId());
            row1.createCell(1).setCellValue(banner.getTitle());
            row1.createCell(2).setCellValue(banner.getUrl());
            row1.createCell(3).setCellValue(banner.getHref());
            HSSFCell cell = row1.createCell(4);
            cell.setCellStyle(cellStyle);
            cell.setCellValue(banner.getCreateDate());
            row1.createCell(5).setCellValue(banner.getDescript());
            row1.createCell(6).setCellValue(banner.getStatus());
        }
        try {
            workbook.write(new File("F:\\后期项目\\测试xls\\"+new Date().getTime()+".xls"));
            workbook.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
