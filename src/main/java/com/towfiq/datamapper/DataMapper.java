package com.towfiq.datamapper;

import com.towfiq.dto.RefreshTokenDTO;

import java.util.HashMap;
import java.util.Map;

public class DataMapper {
    public static Map<String, RefreshTokenDTO> userRefreshTokenMapper = new HashMap<>();
    public static Map<String, RefreshTokenDTO> tokenRefreshTokenMapper = new HashMap<>();
}
