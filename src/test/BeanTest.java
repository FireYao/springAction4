import com.fireyao.bean.BraveKnight;
import com.fireyao.bean.ExpressiveBean;
import com.fireyao.RootConfig;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

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

}
