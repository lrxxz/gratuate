package org.lrx.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class MyWebConfig implements WebMvcConfigurer {

    // 公共资源访问（服务器允许访问目录）
    @Value("${lrx.com.fileSpace}")
    public String FILE_SPACE;

    /**显示相对地址*/
    @Value("${lrx.com.fileSpace.relative}")
    private String fileRelativePath;

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        //开放资源访问路劲
        registry.addResourceHandler(fileRelativePath).
                addResourceLocations("file:" + FILE_SPACE + "/");
    }
}
