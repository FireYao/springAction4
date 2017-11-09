import com.fasterxml.jackson.databind.util.BeanUtil;
import com.fasterxml.jackson.databind.util.ClassUtil;
import com.fireyao.MyBeanRegister;
import org.junit.Test;
import org.springframework.beans.BeanUtils;
import org.springframework.util.ClassUtils;
import org.springframework.web.servlet.view.BeanNameViewResolver;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class JavaTest {

    @Test
    public void name1() throws Exception {
        Class clazz = Class.forName("com.fireyao.mq.RabbitProcesse");
        System.out.println(ClassUtils.getShortNameAsProperty(clazz));

    }


    @Test
    public void test1() throws Exception {
        Integer i1 = 128;
        Integer i2 = Integer.valueOf(128);
        Integer i3 = new Integer(128);
        int i4 = 128;
        System.out.println(i1==i2);
        System.out.println(i1==i3);
        System.out.println(i1==i4);
        System.out.println(i2==i3);
        System.out.println(i2==i4);
        System.out.println(i3==i4);
    }

    @Test
    public void test2() throws Exception {

    }
}

