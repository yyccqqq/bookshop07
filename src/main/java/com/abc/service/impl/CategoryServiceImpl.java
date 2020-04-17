package com.abc.service.impl;

import com.abc.mapper.CategoryMapper;
import com.abc.pojo.Category;
import com.abc.service.ICategoryService;
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
public class CategoryServiceImpl extends ServiceImpl<CategoryMapper, Category> implements ICategoryService {

    @Autowired
    private CategoryMapper categoryMapper;

    @Override
    public Category findCategoryById(Integer categoryId) {
        return categoryMapper.selectById(categoryId);
    }

}
