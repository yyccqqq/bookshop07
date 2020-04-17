package com.abc.service;

import com.abc.pojo.Category;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author yan
 * @since 2020-04-03
 */
public interface ICategoryService extends IService<Category> {

    Category findCategoryById(Integer categoryId);

}
