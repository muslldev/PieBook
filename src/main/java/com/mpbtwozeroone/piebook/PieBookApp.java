package com.mpbtwozeroone.piebook;

import com.mpbtwozeroone.piebook.repositories.CategoryRepository;
import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.Gauge;
import io.micrometer.core.instrument.binder.MeterBinder;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class PieBookApp {
    public static void main(String[] args) {
        SpringApplication.run(PieBookApp.class, args);
    }

    @Bean
    MeterBinder meterBinder(CategoryRepository repository) {
        return registry -> {
            Counter.builder("recipes_count")
                    .description("Количество операций над рецептами")
                    .register(registry);

            Gauge.builder("categories_count", repository::count)
                    .description("Количество категорий")
                    .register(registry);
        };
    }
}
