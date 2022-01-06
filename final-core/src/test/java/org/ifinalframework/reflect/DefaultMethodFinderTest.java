package org.ifinalframework.reflect;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author likly
 * @version 1.2.4
 **/
class DefaultMethodFinderTest {

    private final MethodFinder methodFinder = new DefaultMethodFinder();

    private void method1(){

    }

    private void method2(String arg){}

    private void method3(Integer arg){

    }

    @Test
    void methodFinder(){
        assertThrows(IllegalArgumentException.class,() -> methodFinder.find(DefaultMethodFinderTest.class,"method1",new Class[]{},new Object[]{1}));
        assertNotNull(methodFinder.find(DefaultMethodFinderTest.class,"method1",null,null));
        assertNotNull(methodFinder.find(DefaultMethodFinderTest.class,"method1",new Class[]{},null));
        assertNotNull(methodFinder.find(DefaultMethodFinderTest.class,"method2",null,null));
        assertNull(methodFinder.find(DefaultMethodFinderTest.class,"method2",null,new Object[]{12}));
        assertNotNull(methodFinder.find(DefaultMethodFinderTest.class,"method2",null,new Object[]{"12"}));
        assertNotNull(methodFinder.find(DefaultMethodFinderTest.class,"method3",null,new Object[]{"12"}));
        assertNotNull(methodFinder.find(DefaultMethodFinderTest.class,"method3",null,new Object[]{12}));

    }

}