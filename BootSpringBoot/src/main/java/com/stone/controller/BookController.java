package com.stone.controller;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import com.stone.conf.constant.error.ErrorEnum;
import com.stone.service.BookService;
import com.stone.util.ReturnJSONUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("/cai")
public class BookController {
	@Resource
	private BookService bookService;

	/*
	 * 获取
	 * */
	@PostMapping("/get_cai")
	public JSONObject getBooks(@RequestParam String type) {
		return ReturnJSONUtils.successJson(bookService.getBook(type));
	}



}
