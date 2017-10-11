import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.fireyao.bean.BraveKnight;
import com.fireyao.bean.ExpressiveBean;
import com.fireyao.RootConfig;
import com.fireyao.domain.Item;
import com.fireyao.domain.UserTest;
import com.fireyao.mq.RabbitProcesse;
import com.fireyao.repository.ItemRepository;
import org.geolatte.geom.M;
import org.junit.Before;
import org.junit.Test;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.io.Serializable;
import java.util.List;

/**
 * Created by lly on 2017/8/29
 */
public class BeanTest {

    private static ApplicationContext context;

    @Before
    public void setUp() throws Exception {
        context = new AnnotationConfigApplicationContext(RootConfig.class);
//        context = new ClassPathXmlApplicationContext("classpath:knights.xml");
    }

    @Test
    public void name1() throws Exception {
        ExpressiveBean express = (ExpressiveBean) context.getBean("express");
        System.out.println(express);
    }

    @Test
    public void name2() throws Exception {
        BraveKnight knight = (BraveKnight) context.getBean("knight");
        StringBuffer sb = new StringBuffer();
        sb.append("元首");
        knight.saySomething(sb);
    }

    @Test
    public void name3() throws Exception {

        RabbitProcesse bean = (RabbitProcesse) context.getBean("rabbitProcesse");

        MessageProperties messageProperties = new MessageProperties();
        messageProperties.setContentEncoding("UTF-8");
        messageProperties.setContentType(MessageProperties.CONTENT_TYPE_JSON);
        Message message = new Message("tset".getBytes(), messageProperties);


        UserTest userTest = new UserTest();
        userTest.setAge(123);
        userTest.setId(1L);
        userTest.setName("测试");

        bean.process(userTest);
    }

    @Test
    public void test1() throws Exception {

        ItemRepository itemRepository = context.getBean(ItemRepository.class);

        int page=1,size=10;
        Sort sort = new Sort(Sort.Direction.DESC, "itemId");
        Pageable pageable = new PageRequest(page, size, sort);

        Page<Item> pageResult = itemRepository.findAll(pageable);
        List<Item> itemList = pageResult.getContent();
    }
}
