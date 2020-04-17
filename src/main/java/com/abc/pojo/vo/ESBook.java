package com.abc.pojo.vo;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

@Data
@Document(indexName = "bookshop",type = "ESBook",shards = 1,replicas = 0)
public class ESBook {

    @Id
    private Integer id;

    @Field(type = FieldType.Text,analyzer = "ik_max_word")
    private String name;

    @Field(type = FieldType.Double,index = false)
    private double price;

    @Field(type = FieldType.Integer)
    private Integer uid;

    @Field(type = FieldType.Keyword)
    private String author;

    @Field(type = FieldType.Keyword)
    private String press;

    @Field(type = FieldType.Keyword,index = false)
    private String version;

    @Field(type = FieldType.Keyword ,index = false)
    private String publishDate;

    @Field(type = FieldType.Keyword,index = false)
    private String image;

}
