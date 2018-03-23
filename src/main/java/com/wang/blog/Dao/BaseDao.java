package com.wang.blog.Dao;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Repository
public class BaseDao {
    @Autowired
    private SessionFactory sessionFactory;


    protected  <T> Page<T> pagedQuery(String hql,int pageNo, int pageSize,Class<T> pageReturnType,Object ...selectValues){
        Assert.hasText(hql,"hql should exist");
        Assert.isTrue(pageNo >=1,"pageNo should start from 1");
        Session session= getSession();
        String countQueryString = "select count(*)  " + getTables(hql);

        Query query = createQuery(session,countQueryString,selectValues);

        List countList = query.list();
        Long totalCount = (Long)countList.get(0);
        System.out.println("totalCount: " + totalCount);


        //default start at 0,change to human
        PageRequest pageRequest = new PageRequest(regularPageNoToMachinePageNo(pageNo),pageSize);
       int startIndex =  pageRequest.getOffset();



        List list = createQuery(session,hql,selectValues).setFirstResult(startIndex).setMaxResults(pageSize).list();
     /*   List list = new LinkedList();
        Iterator<T> iterator = createQuery(session,hql,selectValues).setFirstResult(startIndex).setMaxResults(pageSize).iterate();
        while (iterator.hasNext()){
            list.add(iterator.next());
            System.out.println();
        }*/
        //PageRequest humanPageRequest = new PageRequest(pageNo,pageSize);
        //session.close();
        return new PageImpl(list,pageRequest,totalCount);
    }


    protected <T> List<T> qurey(String hql,Class<T> klass,Object ...values){
        Assert.hasText(hql,"hql should exist");
        return this.createQuery(getSession(),hql,values).list();
    }

    private int regularPageNoToMachinePageNo(int pageNo){
        return pageNo - 1;
    }

    private static String getTables(String hql){
        return removeSelect(removeOrders(hql));
    }


    /**
     * 去除hql的select 子句，未考虑union的情况,用于pagedQuery.
     *
     *
     */
    private static String removeSelect(String hql) {
        Assert.hasText(hql,"hql should exist");
        int beginPos = hql.toLowerCase().indexOf("from");
        Assert.isTrue(beginPos != -1, " hql : " + hql + " must has a keyword 'from'");
        return hql.substring(beginPos);
    }

    /**
     * 去除hql的orderby 子句，用于pagedQuery.
     *
     *
     */
    private static String removeOrders(String hql) {
        Assert.hasText(hql,"hql should exist");
        Pattern p = Pattern.compile("order\\s*by[\\w|\\W|\\s|\\S]*", Pattern.CASE_INSENSITIVE);
        Matcher m = p.matcher(hql);
        StringBuffer sb = new StringBuffer();
        while (m.find()) {
            m.appendReplacement(sb, "");
        }
        m.appendTail(sb);
        return sb.toString();
    }


    /**
     * 创建Query对象. 对于需要first,max,fetchsize,cache,cacheRegion等诸多设置的函数,可以在返回Query后自行设置.
     * 留意可以连续设置,如下：
     * <pre>
     * dao.getQuery(hql).setMaxResult(100).setCacheable(true).list();
     * </pre>
     * 调用方式如下：
     * <pre>
     *        dao.createQuery(hql)
     *        dao.createQuery(hql,arg0);
     *        dao.createQuery(hql,arg0,arg1);
     *        dao.createQuery(hql,new Object[arg0,arg1,arg2])
     * </pre>
     *
     * @param values 可变参数.
     */

    protected Query createQuery(Session session,String hql, Object... values) {
        Assert.hasText(hql,"hql is null");
        Query query = session.createQuery(hql);
        for (int i = 0; i < values.length; i++) {
            query.setParameter(i, values[i]);
        }
        return query;
    }


    protected   Session getSession() {
        Session session;

        try {
            session = sessionFactory.getCurrentSession();
        } catch (HibernateException e) {
            session = sessionFactory.openSession();
        }

        return session;
    }

    protected String notSpecifQuery(String value){
        return "%"+value+"%";
    }
}
