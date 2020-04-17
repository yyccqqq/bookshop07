package com.abc;

import cn.hutool.core.io.FileUtil;
import cn.hutool.json.JSONUtil;
import com.abc.component.beetl.LocalDateTimeFormat;
import com.abc.component.elasticsearch.ESBookRepository;
import com.abc.mapper.AskbookMapper;
import com.abc.mapper.BookMapper;
import com.abc.mapper.BookimageMapper;
import com.abc.mapper.UserMapper;
import com.abc.pojo.Askbook;
import com.abc.pojo.Book;
import com.abc.pojo.Bookimage;
import com.abc.pojo.User;
import com.abc.pojo.vo.BookVo;
import com.abc.pojo.vo.ESBook;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.beetl.core.Configuration;
import org.beetl.core.GroupTemplate;
import org.beetl.core.Template;
import org.beetl.core.resource.WebAppResourceLoader;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class Bookshop07ApplicationTests {

    public Bookshop07ApplicationTests() {
        System.setProperty("es.set.netty.runtime.available.processors", "false");
    }

    @Autowired
    private ElasticsearchRestTemplate elasticsearchRestTemplate;

    @Autowired
    private ESBookRepository esBookRepository;

    @Autowired
    private BookMapper bookMapper;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private BookimageMapper bookimageMapper;

    @Autowired
    private AskbookMapper askbookMapper;

    @Test
    public void createIndex() {
        elasticsearchRestTemplate.createIndex(ESBook.class);
    }

    @Test
    public void importData() {
        LambdaQueryWrapper<Book> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Book::getBookType, 1);
        List<Book> bookList = bookMapper.selectList(wrapper);
        List<ESBook> esBookList = new ArrayList<>();
        for (Book book : bookList) {
            ESBook esBook = new ESBook();
            esBook.setId(book.getId());
            esBook.setName(book.getName());
            esBook.setPrice(book.getPrice());
            esBook.setUid(book.getUid());
            esBook.setAuthor(book.getAuthor());
            esBook.setPress(book.getPress());
            esBook.setVersion(book.getVersion());
            esBook.setPublishDate(book.getPublishDate());
            LambdaQueryWrapper<Bookimage> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(Bookimage::getBookId, book.getId());
            Bookimage bookimage = bookimageMapper.selectOne(queryWrapper);
            esBook.setImage(bookimage.getImage());
            esBookList.add(esBook);
        }
        esBookRepository.saveAll(esBookList);
    }

    @Test
    public void creatHtml() throws IOException {
        //路径
        String path = "D:/Projects/bookshop07/src/main/resources/static/view/book/";

        //获取数据
        LambdaQueryWrapper<Book> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Book::getBookType, 1);
        List<Book> bookList = bookMapper.selectList(wrapper);
        for (Book book : bookList) {
            //beetl初始化
            Configuration cfg = Configuration.defaultConfiguration();
            WebAppResourceLoader resourceLoader = new WebAppResourceLoader();
            resourceLoader.setRoot("src/main/resources/templates/");
            GroupTemplate groupTemplate = new GroupTemplate(resourceLoader, cfg);
            groupTemplate.registerFormat("localDateTimeFormat", new LocalDateTimeFormat());
            Template template = groupTemplate.getTemplate("bookDetail.html");
            LambdaQueryWrapper<Bookimage> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(Bookimage::getBookId, book.getId());
            Bookimage bookimage = bookimageMapper.selectOne(queryWrapper);
            String bookStr = JSONUtil.toJsonStr(book);
            BookVo bookVo = JSONUtil.toBean(bookStr, BookVo.class);
            bookVo.setImage(bookimage.getImage());
            User user = userMapper.selectById(bookVo.getUid());
            template.binding("bookVo", bookVo);
            template.binding("user", user);
            BufferedOutputStream out = FileUtil.getOutputStream(path + bookVo.getId() + ".html");
            template.renderTo(out);
        }
    }

    @Test
    public void creatAskBookHtml() throws IOException {
        //路径
        String path = "D:/Projects/bookshop07/src/main/resources/static/view/askBook/";

        //获取数据
        List<Askbook> askbooks = askbookMapper.selectList(null);

        for (Askbook askbook : askbooks) {
            User user = userMapper.selectById(askbook.getUserId());
            //beetl初始化
            Configuration cfg = Configuration.defaultConfiguration();
            WebAppResourceLoader resourceLoader = new WebAppResourceLoader();
            resourceLoader.setRoot("src/main/resources/templates/");
            GroupTemplate groupTemplate = new GroupTemplate(resourceLoader, cfg);
            groupTemplate.registerFormat("localDateTimeFormat", new LocalDateTimeFormat());
            Template template = groupTemplate.getTemplate("askBookDetail.html");
            template.binding("askBook", askbook);
            template.binding("user", user);
            BufferedOutputStream out = FileUtil.getOutputStream(path + askbook.getId() + ".html");
            template.renderTo(out);
        }
    }
}
