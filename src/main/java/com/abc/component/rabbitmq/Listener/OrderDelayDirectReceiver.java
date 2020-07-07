package com.abc.component.rabbitmq.Listener;

import cn.hutool.core.date.DateUtil;
import com.abc.component.elasticsearch.ESBookRepository;
import com.abc.mapper.BookMapper;
import com.abc.mapper.BookimageMapper;
import com.abc.mapper.OrderInfoMapper;
import com.abc.mapper.OrdersMapper;
import com.abc.pojo.Book;
import com.abc.pojo.Bookimage;
import com.abc.pojo.OrderInfo;
import com.abc.pojo.Orders;
import com.abc.pojo.vo.ESBook;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
@RabbitListener(queues = "OrderQueue")
public class OrderDelayDirectReceiver {

    @Autowired
    private OrdersMapper ordersMapper;

    @Autowired
    private OrderInfoMapper orderInfoMapper;

    @Autowired
    private BookMapper bookMapper;

    @Autowired
    private ESBookRepository esBookRepository;

    @Autowired
    private BookimageMapper bookimageMapper;

    @RabbitHandler
    public void process(Map<String, Object> message) {
        List<String> orderIdList = (List<String>) message.get("orderIdList");
        for (String orderId : orderIdList) {
            LambdaUpdateWrapper<Orders> wrapper = new LambdaUpdateWrapper<>();
            wrapper.eq(Orders::getOrderId, orderId).set(Orders::getType, 4);
            ordersMapper.update(null, wrapper);

            LambdaQueryWrapper<OrderInfo> wrapper2 = new LambdaQueryWrapper<>();
            wrapper2.eq(OrderInfo::getOrderId, orderId);
            List<OrderInfo> orderInfoList = orderInfoMapper.selectList(wrapper2);
            for (OrderInfo orderInfo : orderInfoList) {
                LambdaUpdateWrapper<Book> wrapper3 = new LambdaUpdateWrapper<>();
                wrapper3.eq(Book::getId, orderInfo.getBookId()).set(Book::getBookType, 1);
                bookMapper.update(null, wrapper3);

                Book book = bookMapper.selectById(orderInfo.getBookId());
                ESBook esBook = new ESBook();
                esBook.setId(book.getId());
                esBook.setName(book.getName());
                esBook.setPrice(book.getPrice());
                esBook.setUid(book.getUid());
                esBook.setAuthor(book.getAuthor());
                esBook.setPress(book.getPress());
                esBook.setVersion(book.getVersion());
                esBook.setPublishDate(book.getPublishDate());
                LambdaQueryWrapper<Bookimage> wrapper4 = new LambdaQueryWrapper<>();
                wrapper4.eq(Bookimage::getBookId, book.getId());
                Bookimage bookimage = bookimageMapper.selectOne(wrapper4);
                esBook.setImage(bookimage.getImage());
                esBookRepository.save(esBook);
            }
        }
    }
}