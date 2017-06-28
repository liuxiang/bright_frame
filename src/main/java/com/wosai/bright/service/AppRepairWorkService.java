package com.wosai.bright.service;

import com.wosai.bright.model.AppRepairWork;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by Administrator on 2017/5/5 0005.
 */
public interface AppRepairWorkService extends IService<AppRepairWork> {

    Object appRepairWork_save(HttpServletRequest request, AppRepairWork appRepairWork
            , @RequestParam(required = false, value = "file1") MultipartFile file1
            , @RequestParam(required = false, value = "file2") MultipartFile file2
            , @RequestParam(required = false, value = "file3") MultipartFile file3
            , @RequestParam(required = false, value = "file4") MultipartFile file4);
}
