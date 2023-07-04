package cn.xxstudy.expensetracker.global;

import cn.xxstudy.expensetracker.utils.ImageUploadHelper;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @date: 2023/7/3 21:33
 * @author: LovelyCoder
 * @remark:
 */

@Configuration
public class WebConfig implements WebMvcConfigurer {
    private final TokenInterceptor tokenInterceptor;

    public WebConfig(TokenInterceptor tokenInterceptor) {
        this.tokenInterceptor = tokenInterceptor;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
       registry.addInterceptor(tokenInterceptor)
               .addPathPatterns("/**");
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        String imagePath= ImageUploadHelper.getUploadImagePath();
        registry.addResourceHandler("/upload/**").addResourceLocations("file:"+imagePath);
    }
}
