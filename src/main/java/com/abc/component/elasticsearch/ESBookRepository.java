package com.abc.component.elasticsearch;


import com.abc.pojo.vo.ESBook;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;


public interface ESBookRepository extends ElasticsearchRepository<ESBook,Integer> {
}
