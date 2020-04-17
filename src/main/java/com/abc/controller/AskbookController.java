package com.abc.controller;


import com.abc.pojo.Askbook;
import com.abc.pojo.vo.Result;
import com.abc.service.IAskbookService;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author yan
 * @since 2020-04-03
 */
@RestController
@RequestMapping("/askbook")
public class AskbookController {

    @Autowired
    private IAskbookService askbookService;

    @PostMapping("/uploadAskBook")
    public Result uploadAskBook(@RequestBody Askbook askbook) {
        try {
            askbookService.uploadAskBook(askbook);
            return new Result(true, "成功");
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, "失败");
        }
    }

    @GetMapping("/findMyAskBook/{pageNum}")
    public PageInfo<Askbook> findMyAskBook(@PathVariable Integer pageNum) {
        return askbookService.findMyAskBook(pageNum);
    }

    @GetMapping("/findAskBookById/{bookId}")
    public Askbook findAskBokkById(@PathVariable Integer bookId) {
        return askbookService.findAskBookById(bookId);
    }

    @PutMapping("/updateAskBook")
    public Result updateAskBook(@RequestBody Askbook askbook) {
        try {
            System.out.println(askbook.getImage());
            askbookService.updateAskBook(askbook);
            return new Result(true, "成功");
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, "失败");
        }
    }

    @PostMapping("/deleteAskBook")
    public Result deleteAskBook(@RequestBody Integer[] ids) {
        try {
            askbookService.deleteAskBook(ids);
            return new Result(true, "成功");
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, "失败");
        }
    }

    @GetMapping("/findAskBook/{pageNum}")
    public PageInfo<Askbook> findAskBook(@PathVariable Integer pageNum) {
        return askbookService.findAskBook(pageNum);
    }

}
