/**
 * @author 54chen(陈臻) [chenzhen@xiaomi.com czhttp@gmail.com]
 * @since 2012-8-1 下午05:40:27
 */
package com.chen.service;

import com.chen.dao.TestDAO;

import org.easymock.classextension.EasyMock;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.util.ReflectionTestUtils;

import junit.framework.Assert;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:applicationContext.xml")
public class TestServiceTest {

    @Autowired
    private TestService service;
    @Test
    public void getTestTest() {
        com.chen.model.Test t = new com.chen.model.Test();
        t.setId(1111);
        t.setMsg("adfadf");
        TestDAO testDAO = EasyMock.createMock("testDAO", TestDAO.class);

        EasyMock.expect(testDAO.getTest()).andReturn(t);
        EasyMock.replay(testDAO);

        ReflectionTestUtils.setField(service, "testDAO", testDAO, TestDAO.class);

        t = service.getTest();
        // 确信使用了mock
        EasyMock.verify(testDAO);

        Assert.assertEquals(1111, t.getId());
    }
}
