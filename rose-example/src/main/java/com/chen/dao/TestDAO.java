/**
 * @author 54chen(陈臻) [chenzhen@xiaomi.com czhttp@gmail.com]
 * @since 2012-6-27 下午08:29:22
 */
package com.chen.dao;

import com.chen.model.Test;

import net.paoding.rose.jade.annotation.DAO;
import net.paoding.rose.jade.annotation.SQL;
import net.paoding.rose.jade.annotation.SQLParam;

@DAO
public interface TestDAO {

    @SQL("select id,msg from test limit 1")
    public Test getTest();

    @SQL("insert into test (id,msg) values (:t.id,:t.msg)")
    public void insertTest(@SQLParam("t") Test test);
}
