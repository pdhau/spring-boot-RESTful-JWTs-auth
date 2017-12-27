package com.pdhau.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/role")
public class RoleController {
	public ResponseEntity<?> getRole() {
		return new ResponseEntity<>(HttpStatus.OK);
	}
}
