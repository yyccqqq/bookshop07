package com.abc.component.rabbitmq.Listener;

import com.abc.component.elasticsearch.ESBookRepository;
import com.abc.mapper.BookimageMapper;
import com.abc.pojo.Book;
import com.abc.pojo.Bookimage;
import com.abc.pojo.vo.ESBook;
import com.abc.service.IBookService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
@RabbitListener(queues = "topic.add")
public class TopicAddReceiver {

    @Autowired
    private ESBookRepository esBookRepository;

    @Autowired
    private IBookService bookService;

    @Autowired
    private BookimageMapper bookimageMapper;

    @RabbitHandler
    public void proess(Map message) {
        List<Integer> ids= (List<Integer>) message.get("ids");
        for (Integer id : ids) {
            Book book = bookService.findBookById(id);
            ESBook esBook = new ESBook();
            esBook.setId(book.getId());
            esBook.setName(book.getName());
            esBook.setPrice(book.getPrice());
            esBook.setUid(book.getUid());
            esBook.setAuthor(book.getAuthor());
            esBook.setPress(book.getPress());
            esBook.setVersion(book.getVersion());
            esBook.setPublishDate(book.getPublishDate());
            LambdaQueryWrapper<Bookimage> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(Bookimage::getBookId, book.getId());
            Bookimage bookimage = bookimageMapper.selectOne(queryWrapper);
            esBook.setImage(bookimage.getImage());
            esBookRepository.save(esBook);
        }
    }
}
