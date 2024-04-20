package com.alisimsek.HumorousBlog.configuration;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.CacheControl;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.concurrent.TimeUnit;

@Configuration
@EnableWebMvc
@RequiredArgsConstructor
public class StaticResourceConfig implements WebMvcConfigurer {

    private final HumorousProperties humorousProperties;

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        String path = Paths.get(humorousProperties.getStorage().getRoot()).toAbsolutePath().toString() + "/";
        registry.addResourceHandler("/assets/**")
                .addResourceLocations("file:"+path)
                .setCacheControl(CacheControl.maxAge(365, TimeUnit.DAYS));
    }

    @Bean
    CommandLineRunner createStorageDirectories(){
        return args -> {
          createFolder(Paths.get(humorousProperties.getStorage().getRoot()));
          createFolder(Paths.get(humorousProperties.getStorage().getRoot(), humorousProperties.getStorage().getProfile()));

        };
    }

    private void createFolder(Path path){
        File file = path.toFile();
        boolean isFolderExist = file.exists() && file.isDirectory();
        if (!isFolderExist){
            file.mkdir();
        }
    }
}