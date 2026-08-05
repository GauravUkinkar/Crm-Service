package com.CrmService.controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.log4j.Log4j2;

@Log4j2
@RestController
@RequestMapping("/adminController")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class AdminController {

}
