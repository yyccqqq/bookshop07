package com.abc.pojo.vo;

import com.abc.pojo.Category;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookResult {

    private Category category;

    private List<BookVo> bookVoList;
}
