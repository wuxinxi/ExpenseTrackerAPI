package cn.xxstudy.expensetracker.utils;

import cn.xxstudy.expensetracker.constant.Constants;
import cn.xxstudy.expensetracker.constant.HttpCode;
import cn.xxstudy.expensetracker.global.exception.AppException;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * @date: 2023/7/4 15:17
 * @author: TangRen
 * @remark:
 */
public class ImageUploadHelper {

    public static void upload(MultipartFile file, String childDir, int maxSize, int maxPixel) throws Exception {
        byte[] fileBytes = check(file, maxSize, maxPixel);
        Path filePath = Paths.get(Constants.UPLOAD + childDir + "/" + file.getOriginalFilename());
        Files.write(filePath, fileBytes);
    }


    public static byte[] check(MultipartFile file, int maxSize, int maxPixel) throws Exception {
        if (file.getContentType() == null || !file.getContentType().startsWith("image/")) {
            throw new AppException(HttpCode.ERROR.getCode(), "图片文件格式错误");
        }

        if (file.getSize() > maxSize) {
            throw new AppException(HttpCode.ERROR.getCode(), "icon大小不能超过100KB");
        }

        byte[] fileBytes = file.getBytes();
        BufferedImage image = ImageIO.read(new ByteArrayInputStream(fileBytes));
        if (image.getWidth() > maxPixel || image.getHeight() > maxPixel) {
            throw new AppException(HttpCode.ERROR.getCode(), "图片尺寸不能超过100*100像素");
        }

        return fileBytes;
    }
}
