package danofra.nutri_balbe.config;

import com.cloudinary.Cloudinary;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class BeanConfig {
    @Bean
    public Cloudinary uploadImage(@Value("${cloudinary.cloudName}") String name,
                                  @Value("${cloudinary.apiKey}") String key,
                                  @Value("${cloudinary.apiSecret}") String secret) {
        try {
            Map<String, String> config = new HashMap<>();
            config.put("cloud_name", name);
            config.put("api_key", key);
            config.put("api_secret", secret);
            return new Cloudinary(config);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Error ", e);
        }
    }

}
