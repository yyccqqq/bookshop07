package com.abc.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.io.FileUtil;
import com.abc.component.beetl.LocalDateTimeFormat;
import com.abc.mapper.AskbookMapper;
import com.abc.mapper.UserMapper;
import com.abc.pojo.Askbook;
import com.abc.pojo.User;
import com.abc.service.IAskbookService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.shiro.SecurityUtils;
import org.beetl.core.Configuration;
import org.beetl.core.GroupTemplate;
import org.beetl.core.Template;
import org.beetl.core.resource.WebAppResourceLoader;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.BufferedOutputStream;
import java.io.IOException;
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
public class AskbookServiceImpl extends ServiceImpl<AskbookMapper, Askbook> implements IAskbookService {

    @Autowired
    private AskbookMapper askbookMapper;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Autowired
    private UserMapper userMapper;

    @Override
    public void uploadAskBook(Askbook askbook) {
        User user = (User) SecurityUtils.getSubject().getPrincipal();
        askbook.setUserId(user.getId());
        askbookMapper.insert(askbook);

        Map<String, Integer> map = new HashMap<>();
        map.put("bookId", askbook.getId());
        rabbitTemplate.convertAndSend("AskBookDirectExchange", "AskBookDirectRouting", map);
    }

    @Override
    public PageInfo<Askbook> findMyAskBook(Integer pageNum) {
        User user = (User) SecurityUtils.getSubject().getPrincipal();
        PageHelper.startPage(pageNum, 4);
        LambdaQueryWrapper<Askbook> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Askbook::getUserId, user.getId());
        List<Askbook> askbookList = askbookMapper.selectList(wrapper);
        PageInfo<Askbook> pageInfo = new PageInfo<>(askbookList);
        return pageInfo;
    }

    @Override
    public Askbook findAskBookById(Integer bookId) {
        return askbookMapper.selectById(bookId);
    }

    @Override
    public void updateAskBook(Askbook askbook) {
        askbookMapper.updateById(askbook);
    }

    @Override
    public void deleteAskBook(Integer[] ids) {
        askbookMapper.deleteBatchIds(CollUtil.newArrayList(ids));
    }

    @Override
    public PageInfo<Askbook> findAskBook(Integer pageNum) {
        User user = (User) SecurityUtils.getSubject().getPrincipal();
        LambdaQueryWrapper<Askbook> wrapper = new LambdaQueryWrapper<>();
        wrapper.ne(Askbook::getUserId, user.getId());
        PageHelper.startPage(pageNum, 8);
        List<Askbook> askbookList = askbookMapper.selectList(wrapper);
        PageInfo<Askbook> pageInfo = new PageInfo<>(askbookList);
        return pageInfo;
    }

    @Override
    public void createHtml(Integer bookId) {
        //路径
        String path = "D:/Projects/bookshop07/src/main/resources/static/view/askBook/";

        Askbook askbook = askbookMapper.selectById(bookId);
        User user = userMapper.selectById(askbook.getUserId());
        try {
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
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
