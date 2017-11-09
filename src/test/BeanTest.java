import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.fireyao.bean.BraveKnight;
import com.fireyao.bean.ExpressiveBean;
import com.fireyao.RootConfig;
import com.fireyao.bean.list.AListSupplierTest;
import com.fireyao.domain.UserTest;
import com.fireyao.mq.RabbitProcesse;
import com.fireyao.repository.ItemRepository;
import com.fireyao.repository.ItemRepositoryCustom;
import com.fireyao.repository.dto.ItemDTO;
import org.junit.Before;
import org.junit.Test;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

/**
 * Created by lly on 2017/8/29
 */
public class BeanTest {

    private static ApplicationContext context;

    @Before
    public void setUp() throws Exception {
        context = new AnnotationConfigApplicationContext(RootConfig.class);
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

        ItemRepositoryCustom itemRepository = context.getBean(ItemRepositoryCustom.class);
        Page<ItemDTO> itemDTO = itemRepository.findItemDTOs(null, null);
        print(itemDTO);
    }

    @Test
    public void test2() throws Exception {
        AListSupplierTest aListSupplierTest = (AListSupplierTest) context.getBean("aListSupplierTest");

        aListSupplierTest.doSom();
    }

    @Test
    public void test3() throws Exception {
        ItemRepository bean = context.getBean(ItemRepository.class);
        Page<ItemDTO> byId123 = bean.findItemDTOs(null, new PageRequest(0, 10));
        print(byId123.getContent());
    }

    private void print(Object o) {
        System.out.println(JSONObject.toJSONString(o,
                SerializerFeature.PrettyFormat,
                SerializerFeature.WriteNullStringAsEmpty));
    }
}
