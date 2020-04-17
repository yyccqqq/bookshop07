package com.abc.service.impl;

import com.abc.mapper.BookimageMapper;
import com.abc.pojo.Bookimage;
import com.abc.service.IBookimageService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author yan
 * @since 2020-04-03
 */
@Service
public class BookimageServiceImpl extends ServiceImpl<BookimageMapper, Bookimage> implements IBookimageService {

    @Autowired
    private BookimageMapper bookimageMapper;

    @Override
    public Bookimage findByBookId(Integer bookId) {
        LambdaQueryWrapper<Bookimage> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Bookimage::getBookId,bookId);
        return bookimageMapper.selectOne(wrapper);
    }
}
