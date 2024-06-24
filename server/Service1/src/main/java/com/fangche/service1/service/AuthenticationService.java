package com.fangche.service1.service;

import jakarta.servlet.http.HttpServletRequest;

public interface AuthenticationService {
    boolean baseAuthenticate(HttpServletRequest request);
    boolean isAuthorAuthenticate(HttpServletRequest request);
    boolean isAdminAuthenticate(HttpServletRequest request);
}
