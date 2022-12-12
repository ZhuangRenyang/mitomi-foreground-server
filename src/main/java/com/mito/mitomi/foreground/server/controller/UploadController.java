package com.mito.mitomi.foreground.server.controller;

import com.mito.mitomi.foreground.server.pojo.dto.CommodityInsertDTO;
import com.mito.mitomi.foreground.server.service.ICommodityService;
import com.mito.mitomi.foreground.server.web.JsonResult;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;


/*
 *图片加载控制器
 */
@Slf4j
@RestController
public class UploadController {

    @Value("${dirPath}")
    private String dirPath;

    @RequestMapping("/upload")
    public JsonResult upload(@RequestBody MultipartFile picFile) throws IOException {
        log.debug("执行到添加图片的方法picFile:"+picFile);
        //得到文件原始文件名 a.jpg
        String filename = picFile.getOriginalFilename();
        //得到后缀名  从最后一个.出现的位置截取到最后
        String suffix = filename.substring(filename.lastIndexOf("."));
        //得到唯一文件名 UUID.randomUUID()得到一个唯一标识
        filename =UUID.randomUUID() + suffix;
        log.debug("文件名:"+filename);
        //准备保存图片的文件夹路径
//        String dirPath = "E:/files";
        File dirFile = new File(dirPath);
        //如果文件夹不存在,则创建
        if (!dirFile.exists()) {
            dirFile.mkdirs(); //创建文件夹
        }
        //得到文件的完整路径
        String filePath = dirPath+"/"+filename;
        //把文件保存到此路径
        picFile.transferTo(new File(filePath));
        log.debug("文件保存完成,请检查文件是否存在!"+filePath);
        log.debug("文件名称!"+filename);
        return JsonResult.ok(filename);
    }


    @RequestMapping("/remove/{name}")
    public JsonResult remove(@PathVariable("name")String name) {
        String filePath = dirPath+"/"+name;
        log.debug("执行到删除图片的方法,删除图片的名称{}",name);
        new File(filePath).delete(); //删除文件
        return JsonResult.ok();
    }
}
