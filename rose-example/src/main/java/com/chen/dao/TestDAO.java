/**
 * @author 54chen(陈臻) [chenzhen@xiaomi.com czhttp@gmail.com]
 * @since 2012-6-27 下午08:29:22
 */
package com.chen.dao;

import com.chen.model.Test;

import net.paoding.rose.jade.annotation.DAO;
import net.paoding.rose.jade.annotation.ReturnGeneratedKeys;
import net.paoding.rose.jade.annotation.SQL;
import net.paoding.rose.jade.annotation.SQLParam;

import java.util.List;

@DAO
public interface TestDAO {

    @SQL("select id,msg from test limit 1")
    public Test getTest();

    @ReturnGeneratedKeys
    @SQL("insert into test (id,msg) values (:t.id,:t.msg)")
    public int insertTest(@SQLParam("t") Test test);

    @SQL("SELECT user_id, device_token FROM test_##(:partition) LIMIT :limit")
    public List<Test> getTests(@SQLParam("partition") int partition, @SQLParam("limit") int limit);

    @SQL("SELECT user_id, device_token FROM test_##(:partition) #if(:user>0){ where user_id=:user } LIMIT :limit")
    public List<Test> getTestsIf(@SQLParam("partition") int partition, @SQLParam("limit") int limit, @SQLParam("user") int user);

    @SQL("SELECT user_id, device_token FROM test_##(:partition) where user_id in(:ids)")
    public List<Test> getTestsByIds(@SQLParam("partition") int partition, @SQLParam("ids") List<Integer> ids);
}
