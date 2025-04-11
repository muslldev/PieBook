package com.mpbtwozeroone.piebook.services;

import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class QuadraticService {

    public List<Double> solve(double a, double b, double c) {
        double discriminant = b * b - 4 * a * c;

        if (discriminant < 0) {
            throw new IllegalArgumentException("Уравнение не имеет действительных корней");
        } else if (discriminant == 0) {
            double x = -b / (2 * a);
            return List.of(x);
        } else {
            double x1 = (-b + Math.sqrt(discriminant)) / (2 * a);
            double x2 = (-b - Math.sqrt(discriminant)) / (2 * a);
            return List.of(x1, x2);
        }
    }
}