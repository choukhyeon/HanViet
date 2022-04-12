package com.shop.service;

import org.springframework.beans.factory.annotation.Value;

public class AudioService {
    @Value("${audioLocation}")
    private String audioLocation;
}
