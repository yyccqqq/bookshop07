package com.abc.pojo.vo;

import com.abc.pojo.Book;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookVo extends Book {

  private String image;
}
