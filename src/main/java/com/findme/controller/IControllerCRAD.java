package com.findme.controller;

import org.springframework.http.ResponseEntity;

import javax.servlet.http.HttpServletRequest;

public interface IControllerCRAD {
    ResponseEntity<String> save(HttpServletRequest req);

    ResponseEntity<String> findById(HttpServletRequest req);

    ResponseEntity<String> delete(HttpServletRequest req);

    ResponseEntity<String> update(HttpServletRequest req);
}
