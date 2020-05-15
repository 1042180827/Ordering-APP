package com.stone.service;

import com.stone.bean.Caipin;

import java.util.List;

public interface BookService {

	List<Caipin> getBook(String type);
//	List<Caipin> getBookByShen(String address);
//	int deleteBook(int bookId);
//	int saveBook(Caipin book,boolean isSave);
}
