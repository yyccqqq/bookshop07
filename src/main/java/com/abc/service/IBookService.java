package com.abc.service;

import com.abc.pojo.Book;
import com.abc.pojo.vo.BookResult;
import com.abc.pojo.vo.BookVo;
import com.abc.pojo.vo.ESBook;
import com.baomidou.mybatisplus.extension.service.IService;
import com.github.pagehelper.PageInfo;
import org.springframework.data.elasticsearch.core.aggregation.AggregatedPage;

import java.util.List;
import java.util.Map;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author yan
 * @since 2020-04-03
 */
public interface IBookService extends IService<Book> {

    List<BookResult> findBook();

    PageInfo<BookVo> findBookByCategoryId(Integer categoryId, Integer pageNum);

    PageInfo<BookVo> findGoodBook(Integer pageNum);

    AggregatedPage<ESBook> search(String keyword, Integer pageNum);

    PageInfo<BookVo> findUserAllBook(Integer userId, Integer pageNum);

    void updateBookType(Integer bookId, Integer bookType);

    Book findBookById(Integer bookId);

    void uploadSellBook(BookVo bookVo);

    Map<String, Object> findMySellBook(Integer pageNum);

    BookVo findBookVoById(Integer bookId);

    void updateSellBook(BookVo bookVo);

    void deleteSellBook(Integer[] ids);
}
