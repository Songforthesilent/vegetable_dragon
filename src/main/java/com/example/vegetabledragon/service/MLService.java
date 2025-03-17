package com.example.vegetabledragon.service;

import java.util.Map;

public interface MLService {
    Map<String, Object> predict(String text);
}
