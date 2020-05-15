package com.stone.service.Impl;

import com.stone.bean.Caipin;
import com.stone.mapper.BookMapper;
import com.stone.service.BookService;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
import java.util.List;

@Service
public class BookServiceImpl implements BookService {
    @Resource
    private BookMapper bookMapper;

    @Override
    public List<Caipin> getBook(String type) {
        Example example = new Example(Caipin.class);
        example.createCriteria().andEqualTo("type",type);
        return bookMapper.selectByExample(example);
    }
//    /**
//     * 匹配
//     * @return
//     */
//    @Override
//    public List<Caipin>  getBookByShen(String address){
//        Example example = new Example(Caipin.class);
//        example.createCriteria().andLike("address", "%"+address+"%");
//        return bookMapper.selectByExample(example);
//    }
//
//
//    @Override
//    public int deleteBook(int bookId) {
//        Example example = new Example(Caipin.class);
//        example.createCriteria().andEqualTo("id", bookId);
//        return bookMapper.deleteByExample(example);
//    }
//
//    @Override
//    public int saveBook(Caipin book,boolean isSave) {
//        int flag = 0;
//        if(isSave){
//            //添加
//            flag = bookMapper.insertUseGeneratedKeys(book);
//        }else{
//            flag = bookMapper.updateByPrimaryKey(book);
//        }
//
//        return flag;
//    }
}
