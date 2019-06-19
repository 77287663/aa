package com.itheima.test;

import com.itheima.dao.UserDao;
import com.itheima.domain.QueryVo;
import com.itheima.domain.User;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MybatisCRUDTest {
    private InputStream is;
    private SqlSession session;
    private UserDao userDao;
    @Before //init执行后方法才执行
    public void init() throws Exception{
        //读取文件
        is= Resources.getResourceAsStream("SqlMapConfig.xml");
        //创建SqlMapFactory工厂
        SqlSessionFactoryBuilder builder=new SqlSessionFactoryBuilder();
        SqlSessionFactory factory = builder.build(is);
        //获取SqlSession对象,true表示自动提交事务
        session = factory.openSession(true);
        //使用SqlSession创建Dao接口的代理对象
        userDao = session.getMapper(UserDao.class);
    }
    @After//方法执行完destroy再执行
    public void destroy() throws Exception{
        //释放资源
        session.close();
        is.close();
    }

    /**
     * 查询所有
     */
    @Test
    public void TestfindAll(){
        List<User> users = userDao.findAll();
        for (User user : users) {
            System.out.println(user);
        }
    }

    /**
     * 添加用户
     */
    @Test
    public void TestsaveUser(){
        User user =new User();
        user.setUsername("胡婷婷");
        user.setBirthday(new Date());
        user.setSex("女");
        user.setAddress("宜昌");
        userDao.saveUser(user);
        System.out.println(user);
    }

    /**
     * 根据id查询用户信息
     */
    @Test
    public void TestfindById(){
        User user = userDao.findById(51);
        System.out.println(user);
    }

    /**
     * 修改用户信息
     */
    @Test
    public void TestupdateUser(){
        User user = userDao.findById(51);
        user.setAddress("潜江");
        int i = userDao.updateUser(user);
        System.out.println(i);
    }

    /**
     * 根据用户id删除对应用户
     */
    @Test
    public void TestdeleteUser(){
        int i = userDao.deleteUser(51);
        System.out.println(i);
    }

    /**
     * 根据username模糊查询对应的用户
     */
    @Test
    public void TestfindByName(){
        List<User> users = userDao.findByName("%王%");
        for (User user : users) {
            System.out.println(user);
        }
    }

    /**
     *查询总记录数
     */
    @Test
    public void TestfindTotal(){
        int total = userDao.findTotal();
        System.out.println(total);
    }

    /**
     * 动态sql模糊查询
     */
    @Test
    public void TestfindByUser(){
        User user=new User();
        user.setUsername("%王%");
        user.setAddress("%北京%");
        List<User> list = userDao.findByUser(user);
        for (User user1 : list) {
            System.out.println(user1);
        }
    }

    /**
     * 查询queryVo对象中的每一个元素
     */
    @Test
    public void TestFindInIds(){
        QueryVo queryVo=new QueryVo();
        List<Integer> ids=new ArrayList<Integer>();
        ids.add(41);
        ids.add(42);
        ids.add(43);
        ids.add(46);
        ids.add(52);
        queryVo.setIds(ids);
        //执行操作
        List<User> users = userDao.findInIds(queryVo);
        for (User user : users) {
            System.out.println(user);
        }
    }
}
