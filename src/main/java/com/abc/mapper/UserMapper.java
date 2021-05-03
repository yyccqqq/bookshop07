package com.abc.mapper;

import com.abc.pojo.User;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author yan
 * @since 2020-04-03
 */
@Mapper
public interface UserMapper extends BaseMapper<User> {

}
