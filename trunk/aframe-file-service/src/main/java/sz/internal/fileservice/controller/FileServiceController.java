package sz.internal.fileservice.controller;

import common.ConfigUtils;
import org.apache.log4j.Logger;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.*;

@RestController
@RequestMapping("/file")
public class FileServiceController {
    private static final Logger logger = Logger.getLogger(FileServiceController.class);

    @RequestMapping("/upload")
    public Object uploads(@RequestParam("file") MultipartFile[] files, HttpServletRequest request) {
        String path = request.getContextPath();
        String basePath = ConfigUtils.getProperties("upload_image_url_prefix");
        if (basePath == null || "".equals(basePath)) {
            basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path;
        }
        String destDir = ConfigUtils.getProperties("upload_image_dest_dir");
        if (destDir == null || "".equals(destDir)) {
            destDir = "/ueditor/jsp/upload/file/";
        }

        // 获取文件上传的真实路径
        String uploadPath = request.getSession().getServletContext().getRealPath("/");
        List<Map> resultArr = new ArrayList<Map>();
        try {
            //上传文件过程
            for (MultipartFile file : files) {
                String suffix = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf(".") + 1);
                File destFile = new File(uploadPath + destDir);
                if (!destFile.exists()) {
                    destFile.mkdirs();
                }
                String fileNameNew = getFileNameNew() + "." + suffix;//
                File f = new File(destFile.getAbsoluteFile() + File.separator + fileNameNew);
                //如果当前文件已经存在了，就跳过。
                if (f.exists()) {
                    continue;
                }
                file.transferTo(f);
                f.createNewFile();
                String filename = basePath + destDir + fileNameNew;
                Map<String, String> upload_map = new HashMap<String, String>();
                upload_map.put("url", filename);
                resultArr.add(upload_map);
            }
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return resultArr;
    }

    /**
     * 为文件重新命名，命名规则为当前系统时间毫秒数
     *
     * @return string
     */
    private String getFileNameNew() {
        SimpleDateFormat fmt = new SimpleDateFormat("yyyyMMddHHmmssSSS");
        return fmt.format(new Date());
    }
}
