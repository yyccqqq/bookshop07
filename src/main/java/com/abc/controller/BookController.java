package com.abc.controller;


import cn.hutool.core.convert.Convert;
import cn.hutool.core.io.resource.InputStreamResource;
import cn.hutool.crypto.SecureUtil;
import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONUtil;
import com.abc.pojo.Bookimage;
import com.abc.pojo.Category;
import com.abc.pojo.User;
import com.abc.pojo.vo.BookResult;
import com.abc.pojo.vo.BookVo;
import com.abc.pojo.vo.ESBook;
import com.abc.pojo.vo.Result;
import com.abc.service.IBookService;
import com.abc.service.IBookimageService;
import com.abc.service.ICategoryService;
import com.abc.service.IUserService;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.aggregation.AggregatedPage;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author yan
 * @since 2020-04-03
 */
@RestController
@RequestMapping("/book")
public class BookController {

    @Autowired
    private IBookService bookService;

    @Autowired
    private ICategoryService categoryService;

    @Autowired
    private IUserService userService;

    @Autowired
    private IBookimageService bookimageService;

    private final static String uploadPath = "http://bookshop.yancq.top:10342/group1/upload";

    private final static String deletePath = "http://bookshop.yancq.top:10342/group1/delete";

    @GetMapping("/findBook")
    public Map<String, Object> findBook() {
        List<BookResult> bookResultList = bookService.findBook();
        Map<String, Object> map = new HashMap<>();
        map.put("bookResultList", bookResultList);
        return map;
    }

    @GetMapping("/findBookByCategoryId/{categoryId}/{pageNum}")
    public Map<String, Object> findBookByCategoryId(@PathVariable Integer categoryId, @PathVariable Integer pageNum) {
        PageInfo<BookVo> pageInfo = bookService.findBookByCategoryId(categoryId, pageNum);
        Category category = categoryService.findCategoryById(categoryId);
        Map<String, Object> map = new HashMap<>();
        map.put("pageInfo", pageInfo);
        map.put("category", category);
        return map;
    }

    @GetMapping("/findGoodBook/{pageNum}")
    public Map<String, Object> findGoodBook(@PathVariable Integer pageNum) {
        PageInfo<BookVo> pageInfo = bookService.findGoodBook(pageNum);
        Map<String, Object> map = new HashMap<>();
        map.put("pageInfo", pageInfo);
        return map;
    }

    @GetMapping("/search/{keyword}/{pageNum}")
    public AggregatedPage<ESBook> search(@PathVariable String keyword, @PathVariable Integer pageNum) {
        return bookService.search(keyword, pageNum);
    }

    @GetMapping("/findUserAllBook/{userId}/{pageNum}")
    public Map<String, Object> findUserAllBook(@PathVariable Integer userId, @PathVariable Integer pageNum) {
        PageInfo<BookVo> pageInfo = bookService.findUserAllBook(userId, pageNum);
        User user = userService.findUserById(userId);
        Map<String, Object> map = new HashMap<>();
        map.put("pageInfo", pageInfo);
        map.put("seller", user);
        return map;
    }

    @PostMapping("/upload")
    public Result upload(MultipartFile file) {
        try {
            InputStreamResource isr = new InputStreamResource(file.getInputStream(), file.getOriginalFilename());
            Map<String, Object> paramMap = new HashMap<>();
            paramMap.put("file", isr);
            paramMap.put("path", "image");
            paramMap.put("output", "json");
            paramMap.put("scene", "image");
            String md5 = SecureUtil.md5(file.getInputStream());
            String path = uploadPath + "?md5=" + md5 + "&output=json";
            String resp = HttpUtil.post(path, paramMap);
            Map<String, Object> result = JSONUtil.toBean(resp, Map.class);
            String url = "http://bookshop.yancq.top:10342" + Convert.toStr(result.get("path"));
            return new Result(true, url);
        } catch (IOException e) {
            e.printStackTrace();
            return new Result(false, "上传失败");
        }
    }

    @GetMapping("/delete/{bookId}")
    public Result deleteImage(@PathVariable Integer bookId) {
        try {
            Bookimage bookimage = bookimageService.findByBookId(bookId);
            String[] split = bookimage.getImage().split("/");
            String path = "/" + split[split.length - 3] + "/" + split[split.length - 2] + "/" + split[split.length - 1];
            String resp = HttpUtil.get(deletePath + "?path=" + path);
            return new Result(true, "成功");
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, "失败");
        }
    }

    @PostMapping("/uploadSellBook")
    public Result uploadSellBook(@RequestBody BookVo bookVo) {
        try {
            bookService.uploadSellBook(bookVo);
            return new Result(true, "成功");
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, "失败");
        }
    }

    @GetMapping("/findMySellBook/{pageNum}")
    public Map<String, Object> findMySellBook(@PathVariable Integer pageNum) {
        return bookService.findMySellBook(pageNum);
    }

    @GetMapping("/findBookById/{bookId}")
    public BookVo findBookById(@PathVariable Integer bookId) {
        return bookService.findBookVoById(bookId);
    }

    @PutMapping("/updateSellBook")
    public Result updateSellBook(@RequestBody BookVo bookVo) {
        try {
            bookService.updateSellBook(bookVo);
            return new Result(true, "成功");
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, "失败");
        }
    }

    @PostMapping("/deleteSellBook")
    public Result deleteSellBook(@RequestBody Integer[] ids) {
        try {
            bookService.deleteSellBook(ids);
            return new Result(true, "成功");
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, "失败");
        }
    }

}
