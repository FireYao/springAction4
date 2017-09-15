package com.fireyao.aop;

import com.fireyao.bean.DefaultEncoreable;
import com.fireyao.bean.Encoreable;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.DeclareParents;

/**
 * Created by lly on 2017/8/31
 */
@Aspect
public class EncoreableIntroducer {

    @DeclareParents(value = "com.fireyao.bean.Encoreable", defaultImpl = DefaultEncoreable.class)
    public static Encoreable encoreable;

}
