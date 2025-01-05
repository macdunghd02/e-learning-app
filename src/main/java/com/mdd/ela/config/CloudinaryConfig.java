package com.mdd.ela.config;

import com.cloudinary.Cloudinary;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

/**
 * @author dungmd
 * @created 1/5/2025 3:31 下午
 * @project e-learning-app
 */

@Configuration
public class CloudinaryConfig {
    @Bean
    public Cloudinary getCloudinary() {
        Map map = new HashMap();
        map.put("cloud_name", "dg5gto7ns");
        map.put("api_key", "666615287324255");
        map.put("api_secret", "jlY9fL5oF5EQkZj9AoMLPfs92TE");
        map.put("secure", true);
        return new Cloudinary(map);
    }
}
