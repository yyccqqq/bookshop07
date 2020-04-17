package com.abc.service;

import com.abc.pojo.Bookimage;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author yan
 * @since 2020-04-03
 */
public interface IBookimageService extends IService<Bookimage> {

    Bookimage findByBookId(Integer bookId);
}
