package com.abc.service;

import com.abc.pojo.Askbook;
import com.baomidou.mybatisplus.extension.service.IService;
import com.github.pagehelper.PageInfo;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author yan
 * @since 2020-04-03
 */
public interface IAskbookService extends IService<Askbook> {


    void uploadAskBook(Askbook askbook);

    PageInfo<Askbook> findMyAskBook(Integer pageNum);

    Askbook findAskBookById(Integer bookId);

    void updateAskBook(Askbook askbook);

    void deleteAskBook(Integer[] ids);

    PageInfo<Askbook> findAskBook(Integer pageNum);
}
