package com.baizhi.cmfz.service.impl;

import com.baizhi.cmfz.dao.UserDao;
import com.baizhi.cmfz.entity.Province;
import com.baizhi.cmfz.entity.Sex;
import com.baizhi.cmfz.entity.User;
import com.baizhi.cmfz.service.UserService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.google.gson.Gson;
import io.goeasy.GoEasy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

@Service
public class UserServiceImpl implements UserService {


    /**
     * IO密集型任务  （常出现于线程中：数据库数据交互、文件上传下载、网络数据传输等等 能够体现多核处理器的优势）
     * CPU密集型任务 (常出现于线程中：复杂算法 能体现CPU版本的优势）
     *
     */
    private static int corePoolSize = Runtime.getRuntime().availableProcessors();
    /**
     * public ThreadPoolExecutor(int corePoolSize,int maximumPoolSize,long keepAliveTime,
     *                           TimeUnit unit,BlockingQueue<Runnable> workQueue)
     * corePoolSize用于指定核心线程数量
     * maximumPoolSize指定最大线程数
     * keepAliveTime和TimeUnit指定线程空闲后的最大存活时间
     * workQueue则是线程池的缓冲队列,还未执行的线程会在队列中等待
     * 监控队列长度，确保队列有界
     * 不当的线程池大小会使得处理速度变慢，稳定性下降，并且导致内存泄露。如果配置的线程过少，则队列会持续变大，消耗过多内存。
     * 而过多的线程又会 由于频繁的上下文切换导致整个系统的速度变缓——殊途而同归。队列的长度至关重要，它必须得是有界的，这样如果线程池不堪重负了它可以暂时拒绝掉新的请求。
     * ExecutorService 默认的实现是一个无界的 LinkedBlockingQueue。
     */
    private static ThreadPoolExecutor executor  = new ThreadPoolExecutor(corePoolSize, corePoolSize+1, 10l, TimeUnit.SECONDS,
            new LinkedBlockingQueue<Runnable>(1000));



    @Autowired
    private UserDao userDao;

    @Override
    public Map<String, Object> showUser(int page, int rows) {
        Map<String, Object> map = new HashMap<String, Object>();

        IPage<User> iPage = new Page<User>(page, rows);
        IPage<User> iPage1 = userDao.selectPage(iPage, null);

        List<User> list = iPage1.getRecords();

        int count = userDao.selectCount(null);

        map.put("rows", list);
        map.put("total", count);

        return map;
    }

    @Override
    public boolean multiDelete(int[] ids) {
        try {
            for (int id : ids) {
                User user = userDao.selectById(id);
                user.setUserStatus(user.getUserStatus() == 1 ? 0 : 1);
                userDao.updateById(user);
            }
        } catch (Exception e) {
            return false;
        }

        return true;
    }

    @Override
    public boolean insertNew(User user) {
        try {
            //        1.获取数据
            List dataPage = this.getDataPage();
//        2.创建Gson 转json
            Gson gson = new Gson();
            String json = gson.toJson(dataPage);

//        3.创建GoEasy 对象
            GoEasy goEasy = new GoEasy( "http://rest-hangzhou.goeasy.io", "BC-16d86c4f7ad74e19810feb541ac47189");

//        4.推送信息
            /**
             * channel 通道 频道的名字
             * content 要发布的内容
             */
            goEasy.publish("cmfzChannel1", json);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return userDao.insert(user) == 1 ? true : false;
    }

    @Override
    public boolean updateUser(User user) {
        return userDao.updateById(user)==1?true:false;
    }

    @Override
    public List<Sex> getCountBySex() {
        return userDao.getCountBySex();
    }

    @Override
    public List<Map> getProvince() {
        List<Map> list = new ArrayList<Map>();


        List<Province> province = userDao.getProvince();

        for (Province province1 : province) {
            Map map = new HashMap();

            map.put("name", province1.getUserProvince());
            map.put("value", userDao.selectCount(new QueryWrapper<User>().eq("user_province", province1.getUserProvince())));
            list.add(map);
        }

        return list;
    }

    @Override
    public List getDataPage() {
        List<Map> list = new ArrayList<Map>();
        Map map = new HashMap();

        // 性别数量
        List<Sex> sex = userDao.getCountBySex();

        map.put("boy", sex.get(1).getCount());
        map.put("girl", sex.get(0).getCount());

        // 注册数量
        int last = userDao.getNewCreate(7).size();
        int earlier = userDao.getNewCreate(14).size() - userDao.getNewCreate(7).size();
        int earliest = userDao.getNewCreate(21).size() - userDao.getNewCreate(14).size();

        map.put("last", last);
        map.put("earlier", earlier);
        map.put("earliest", earliest);

        // 省份
        List<Province> province = userDao.getProvince();

        for (Province province1 : province) {
            Map map1 = new HashMap();

            map1.put("name", province1.getUserProvince());
            map1.put("value", userDao.selectCount(new QueryWrapper<User>().eq("user_province", province1.getUserProvince())));
            list.add(map1);
        }

        list.add(map);
        return list;
    }

    @Override
    public Map youhua() {
        final Map map = new HashMap();
//        1.创建一个任务计数器的类
        final CountDownLatch countDownLatch = new CountDownLatch(3);
//        2.获取三个线程去做查询
//        执行性别统计查询
        executor.execute(new Runnable() {
            @Override
            public void run() {
//                执行性别查询
                List<Sex> sex = userDao.getCountBySex();
                map.put("sex",sex);
//                执行完计数器减一
                countDownLatch.countDown();
            }
        });

//        执行注册量统计
        executor.execute(new Runnable() {
            @Override
            public void run() {
                int last = userDao.getNewCreate(7).size();
                int earlier = userDao.getNewCreate(14).size() - userDao.getNewCreate(7).size();
                int earliest = userDao.getNewCreate(21).size() - userDao.getNewCreate(14).size();

                map.put("last", last);
                map.put("earlier", earlier);
                map.put("earliest", earliest);
                countDownLatch.countDown();
            }
        });

//        执行地区分布
        executor.execute(new Runnable() {
            @Override
            public void run() {
                List<Province> province = userDao.getProvince();
                map.put("province",province);
                countDownLatch.countDown();
            }
        });

//        3.主线程等待其他线程执行完
        try {
            countDownLatch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return map;
    }
}
