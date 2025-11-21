package com.restaurant.restaurantservice.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // This maps any /images/** request to your local folder path
        registry.addResourceHandler("/images/**")
                .addResourceLocations("file:F:/RestaurantProject/Images for Restaurant app/");
    }
}