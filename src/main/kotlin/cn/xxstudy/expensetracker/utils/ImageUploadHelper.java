package cn.xxstudy.expensetracker.utils;

import cn.xxstudy.expensetracker.constant.ErrorCode;
import cn.xxstudy.expensetracker.global.exception.AppException;
import org.jetbrains.annotations.Nullable;
import org.springframework.boot.system.ApplicationHome;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

/**
 * @date: 2023/7/4 15:17
 * @author: TangRen
 * @remark:
 */
public class ImageUploadHelper {

    /**
     *
     * @param request HttpServletRequest
     * @param file MultipartFile
     * @param childDir User id
     * @param maxSize image max size
     * @param maxPixel image max pixel
     * @return result image url
     * @throws Exception
     */
    @Nullable
    public static String upload(HttpServletRequest request, MultipartFile file, String childDir, int maxSize, int maxPixel) throws Exception {
        if(file==null||file.isEmpty()){
            return null;
        }
        String imagePath= getUploadImagePath()+childDir;
        File imageDir = new File(imagePath);
        if(!imageDir.exists()){
            boolean mkdirs = imageDir.mkdirs();
        }
        byte[] fileBytes = check(file, maxSize, maxPixel);
        String originalName = file.getOriginalFilename();
        assert originalName != null;
        String suffix = originalName.substring(originalName.lastIndexOf('.'));
        String newName= UUID.randomUUID() +suffix;
        Path filePath = Paths.get(imagePath, newName);
        Files.write(filePath, fileBytes);
        String baseUrl = request.getRequestURL().toString();
        return baseUrl.substring(0, baseUrl.lastIndexOf("/")) + "/upload/" + childDir+"/"+newName;
    }

    public static byte[] check(MultipartFile file, int maxSize, int maxPixel) throws Exception {
        if (file.getContentType() == null || !file.getContentType().startsWith("image/")) {
            throw new AppException(ErrorCode.ERROR.getCode(), "图片文件格式错误");
        }
        if (file.getSize() > maxSize) {
            throw new AppException(ErrorCode.ERROR.getCode(), String.format("icon大小不能超过%dKB",maxSize/1000));
        }

        byte[] fileBytes = file.getBytes();
        BufferedImage image = ImageIO.read(new ByteArrayInputStream(fileBytes));
        if (image.getWidth() > maxPixel || image.getHeight() > maxPixel) {
            throw new AppException(ErrorCode.ERROR.getCode(), String.format("图片尺寸不能超过%d*%d像素",maxPixel,maxPixel));
        }
        return fileBytes;
    }

    public static String getUploadImagePath(){
        ApplicationHome applicationHome = new ApplicationHome();
        String path = applicationHome.getDir().getPath();
        String separator = File.separator;
        String imagePath= path+separator+"src"+separator+"main"+separator+"resources"+separator+"static"+separator+"upload"+separator;
        File imageDir = new File(imagePath);
        if(!imageDir.exists()){
            boolean mkdirs = imageDir.mkdirs();
        }
        return imagePath;
    }

    /**
     * delete old image
     * @param childDir userId
     * @param oldImageUrl .
     * @param newImageUrl .
     * @return
     */
    public static boolean deleteOldImage(String childDir,String oldImageUrl, String newImageUrl){
        if (newImageUrl != null && oldImageUrl != null){
            String oldImagePath = ImageUploadHelper.getUploadImagePath() + childDir + File.separator + oldImageUrl.substring(oldImageUrl.lastIndexOf('/') + 1);
            File oldImageFile = new File(oldImagePath);
            if(oldImageFile.exists()){
                return oldImageFile.delete();
            }
        }
        return false;
    }
}
