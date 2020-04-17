package com.abc.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.json.JSONUtil;
import com.abc.mapper.BookMapper;
import com.abc.mapper.BookimageMapper;
import com.abc.mapper.CategoryMapper;
import com.abc.mapper.UserMapper;
import com.abc.pojo.Book;
import com.abc.pojo.Bookimage;
import com.abc.pojo.Category;
import com.abc.pojo.User;
import com.abc.pojo.vo.BookResult;
import com.abc.pojo.vo.BookVo;
import com.abc.pojo.vo.ESBook;
import com.abc.service.IBookService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightField;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.data.elasticsearch.core.SearchResultMapper;
import org.springframework.data.elasticsearch.core.aggregation.AggregatedPage;
import org.springframework.data.elasticsearch.core.aggregation.impl.AggregatedPageImpl;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.core.query.SearchQuery;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author yan
 * @since 2020-04-03
 */
@Service
public class BookServiceImpl extends ServiceImpl<BookMapper, Book> implements IBookService {

    @Autowired
    private BookMapper bookMapper;

    @Autowired
    private CategoryMapper categoryMapper;

    @Autowired
    private BookimageMapper bookimageMapper;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private ElasticsearchRestTemplate elasticsearchRestTemplate;

    @Override
    public List<BookResult> findBook() {
        List<BookResult> bookResultList = new ArrayList<>();
        Subject subject = SecurityUtils.getSubject();
        User user = (User) subject.getPrincipal();
        for (Integer i = 1; i <= 9; i++) {
            PageHelper.startPage(1, 4);
            LambdaQueryWrapper<Book> wrapper = new LambdaQueryWrapper<>();
            wrapper.eq(Book::getCid, i).eq(Book::getBookType, 1).ne(Book::getUid, user.getId());
            List<Book> bookList = bookMapper.selectList(wrapper);
            PageInfo<Book> pageInfo = new PageInfo<>(bookList);
            List<BookVo> bookVoList = new ArrayList<>();
            for (Book book : pageInfo.getList()) {
                BookVo bookVo = getBookVo(book);
                bookVoList.add(bookVo);
            }
            Category category = categoryMapper.selectById(i);
            BookResult bookResult = new BookResult(category, bookVoList);
            bookResultList.add(bookResult);
        }
        return bookResultList;
    }

    @Override
    public PageInfo<BookVo> findBookByCategoryId(Integer categoryId, Integer pageNum) {
        PageHelper.startPage(pageNum, 8);
        Subject subject = SecurityUtils.getSubject();
        User user = (User) subject.getPrincipal();
        LambdaQueryWrapper<Book> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Book::getCid, categoryId).eq(Book::getBookType, 1).ne(Book::getUid, user.getId());
        List<Book> bookList = bookMapper.selectList(wrapper);
        PageInfo<Book> bookPageInfo = new PageInfo<>(bookList);
        List<BookVo> bookVoList = new ArrayList<>();
        for (Book book : bookPageInfo.getList()) {
            BookVo bookVo = getBookVo(book);
            bookVoList.add(bookVo);
        }
        PageInfo<BookVo> pageInfo = new PageInfo<>();
        pageInfo.setList(bookVoList);
        pageInfo.setTotal(bookPageInfo.getTotal());
        return pageInfo;
    }

    @Override
    public PageInfo<BookVo> findGoodBook(Integer pageNum) {
        PageHelper.startPage(pageNum, 8);
        Subject subject = SecurityUtils.getSubject();
        User user = (User) subject.getPrincipal();
        LambdaQueryWrapper<Book> wrapper = new LambdaQueryWrapper<>();
        wrapper.ge(Book::getDegree, 9).eq(Book::getBookType, 1).ne(Book::getUid, user.getId());
        List<Book> bookList = bookMapper.selectList(wrapper);
        PageInfo<Book> bookPageInfo = new PageInfo<>(bookList);
        List<BookVo> bookVoList = new ArrayList<>();
        for (Book book : bookPageInfo.getList()) {
            BookVo bookVo = getBookVo(book);
            bookVoList.add(bookVo);
        }
        PageInfo<BookVo> pageInfo = new PageInfo<>();
        pageInfo.setList(bookVoList);
        pageInfo.setTotal(bookPageInfo.getTotal());
        return pageInfo;
    }

    @Override
    public AggregatedPage<ESBook> search(String keyword, Integer pageNum) {
        List<HighlightBuilder.Field> highlightFields = new ArrayList<>();
        HighlightBuilder.Field name = new HighlightBuilder.Field("name").preTags("<span style='color:red'>").postTags("</span>");
        HighlightBuilder.Field author = new HighlightBuilder.Field("author").preTags("<span style='color:red'>").postTags("</span>");
        HighlightBuilder.Field press = new HighlightBuilder.Field("press").preTags("<span style='color:red'>").postTags("</span>");
        highlightFields.add(name);
        highlightFields.add(author);
        highlightFields.add(press);
        HighlightBuilder.Field[] fields = new HighlightBuilder.Field[highlightFields.size()];
        for (int i = 0; i < highlightFields.size(); i++) {
            fields[i] = highlightFields.get(i);
        }

        User login_user = (User) SecurityUtils.getSubject().getPrincipal();
        SearchQuery searchQuery = new NativeSearchQueryBuilder().
                withQuery(QueryBuilders.boolQuery()
                        .must(QueryBuilders.multiMatchQuery(keyword, "name", "author", "press"))
                        .mustNot(QueryBuilders.termQuery("uid", login_user.getId())))
                .withHighlightFields(fields)
                .withPageable(PageRequest.of(pageNum - 1, 8))
                .build();

        SearchResultMapper resultMapper = new SearchResultMapper() {
            @Override
            public <T> AggregatedPage<T> mapResults(SearchResponse searchResponse, Class<T> aClass, Pageable pageable) {
                List<ESBook> esBookList = new ArrayList<>();
                for (SearchHit searchHit : searchResponse.getHits()) {
                    if (searchResponse.getHits().getHits().length <= 0) {
                        return null;
                    }
                    ESBook esBook = new ESBook();
                    Map<String, Object> sourceAsMap = searchHit.getSourceAsMap();
                    esBook.setId(Integer.valueOf(searchHit.getId()));
                    esBook.setPrice((Double) sourceAsMap.get("price"));
                    esBook.setVersion((String) sourceAsMap.get("version"));
                    esBook.setPublishDate((String) sourceAsMap.get("publishDate"));
                    esBook.setImage((String) sourceAsMap.get("image"));
                    esBook.setName((String) sourceAsMap.get("name"));
                    esBook.setAuthor((String) sourceAsMap.get("author"));
                    esBook.setPress((String) sourceAsMap.get("press"));

                    Map<String, HighlightField> highlightFields = searchHit.getHighlightFields();
                    HighlightField name = highlightFields.get("name");
                    if (name != null) {
                        esBook.setName(name.fragments()[0].toString());
                    }

                    HighlightField author = highlightFields.get("author");
                    if (author != null) {
                        esBook.setAuthor(author.fragments()[0].toString());
                    }

                    HighlightField press = highlightFields.get("press");
                    if (press != null) {
                        esBook.setPress(press.fragments()[0].toString());
                    }
                    esBookList.add(esBook);
                }
                return new AggregatedPageImpl<>((List<T>) esBookList);
            }

            @Override
            public <T> T mapSearchHit(SearchHit searchHit, Class<T> aClass) {
                return null;
            }
        };

        AggregatedPage<ESBook> pageInfo = elasticsearchRestTemplate.queryForPage(searchQuery, ESBook.class, resultMapper);
        pageInfo.getContent();
        return pageInfo;
    }

    @Override
    public PageInfo<BookVo> findUserAllBook(Integer userId, Integer pageNum) {
        PageHelper.startPage(pageNum, 8);
        LambdaQueryWrapper<Book> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Book::getBookType, 1).eq(Book::getUid, userId);
        List<Book> bookList = bookMapper.selectList(wrapper);
        PageInfo<Book> bookPageInfo = new PageInfo<>(bookList);
        List<BookVo> bookVoList = new ArrayList<>();
        for (Book book : bookPageInfo.getList()) {
            BookVo bookVo = getBookVo(book);
            bookVoList.add(bookVo);
        }
        PageInfo<BookVo> pageInfo = new PageInfo<>();
        pageInfo.setList(bookVoList);
        pageInfo.setTotal(bookPageInfo.getTotal());
        return pageInfo;
    }

    @Override
    public void updateBookType(Integer bookId, Integer bookType) {
        LambdaUpdateWrapper<Book> wrapper = new LambdaUpdateWrapper<>();
        wrapper.eq(Book::getId, bookId).set(Book::getBookType, bookType);
        bookMapper.update(null, wrapper);
    }

    @Override
    public Book findBookById(Integer bookId) {
        return bookMapper.selectById(bookId);
    }

    @Override
    public void uploadSellBook(BookVo bookVo) {
        Book book = getBook(bookVo);
        bookMapper.insert(book);

        Bookimage bookimage = new Bookimage();
        bookimage.setBookId(book.getId());
        bookimage.setImage(bookVo.getImage());
        bookimageMapper.insert(bookimage);
    }

    @Override
    public Map<String, Object> findMySellBook(Integer pageNum) {
        User user = (User) SecurityUtils.getSubject().getPrincipal();
        user = userMapper.selectById(user.getId());
        PageHelper.startPage(pageNum, 4);
        LambdaQueryWrapper<Book> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Book::getUid, user.getId());
        List<Book> books = bookMapper.selectList(wrapper);
        PageInfo<Book> pageInfo = new PageInfo<>(books);
        List<BookVo> bookVoList = new ArrayList<>();
        for (Book book : pageInfo.getList()) {
            BookVo bookVo = getBookVo(book);
            bookVoList.add(bookVo);
        }
        PageInfo<BookVo> pageInfo2 = new PageInfo<>();
        pageInfo2.setList(bookVoList);
        pageInfo2.setTotal(pageInfo.getTotal());
        Map<String, Object> map = new HashMap<>();
        map.put("pageInfo", pageInfo2);
        map.put("user", user);
        return map;
    }

    @Override
    public BookVo findBookVoById(Integer bookId) {
        Book book = bookMapper.selectById(bookId);
        return getBookVo(book);
    }

    @Override
    public void updateSellBook(BookVo bookVo) {
        Book book = getBook(bookVo);
        book.setId(bookVo.getId());
        bookMapper.updateById(book);

        LambdaUpdateWrapper<Bookimage> wrapper = new LambdaUpdateWrapper<>();
        wrapper.eq(Bookimage::getBookId, bookVo.getId()).set(Bookimage::getImage, bookVo.getImage());
        bookimageMapper.update(null, wrapper);
    }

    @Override
    public void deleteSellBook(Integer[] ids) {
        bookMapper.deleteBatchIds(CollUtil.newArrayList(ids));
    }

    private BookVo getBookVo(Book book) {
        LambdaQueryWrapper<Bookimage> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Bookimage::getBookId, book.getId());
        Bookimage bookimage = bookimageMapper.selectOne(queryWrapper);
        String bookStr = JSONUtil.toJsonStr(book);
        BookVo bookVo = JSONUtil.toBean(bookStr, BookVo.class);
        bookVo.setImage(bookimage.getImage());
        return bookVo;
    }

    private Book getBook(BookVo bookVo) {
        User user = (User) SecurityUtils.getSubject().getPrincipal();
        Book book = new Book();
        book.setName(bookVo.getName());
        book.setCid(bookVo.getCid());
        book.setPrice(bookVo.getPrice());
        book.setOriginalPrice(bookVo.getOriginalPrice());
        book.setUid(user.getId());
        book.setAuthor(bookVo.getAuthor());
        book.setPress(bookVo.getPress());
        book.setVersion(bookVo.getVersion());
        book.setDegree(bookVo.getDegree());
        book.setPublishDate(bookVo.getPublishDate());
        book.setDescription(bookVo.getDescription());
        return book;
    }

}
