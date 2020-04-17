package com.abc.service.impl;

import cn.hutool.core.collection.CollUtil;
import com.abc.mapper.AskbookMapper;
import com.abc.pojo.Askbook;
import com.abc.pojo.User;
import com.abc.service.IAskbookService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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

    @Override
    public void uploadAskBook(Askbook askbook) {
        User user = (User) SecurityUtils.getSubject().getPrincipal();
        askbook.setUserId(user.getId());
        askbookMapper.insert(askbook);
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
}
