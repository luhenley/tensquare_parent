package com.tensquare.qa.dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.tensquare.qa.pojo.Problem;
import org.springframework.data.jpa.repository.Query;

/**
 * 数据访问接口
 * @author Administrator
 *
 */
public interface ProblemDao extends JpaRepository<Problem,String>,JpaSpecificationExecutor<Problem>{

    /**
     *
     根据标签 ID
     查询最新问题列表
     * @param labelId
     * @param pageable
     * @return
     */
    //select * from tb_problem where id in
    // (select problemid from tb_pl where labelid=?) order by replytime desc
    @Query("select p from Problem p where id in (select problemid from Pl where labelid=?1) order by replytime desc")
    Page<Problem> findNewListByLabelId(String labelId, Pageable pageable);

    /**
     *
     根据标签 ID
     查询热门问题列表
     * @param labelId
     * @param pageable
     * @return
     */
    @Query("select p from Problem p where id in( select problemid from Pl where labelid=?1) order by reply desc")
    Page<Problem> findHotListByLabelId(String labelId, Pageable pageable);

    /**
     *
     等待问答列表
     */
    @Query("select p from Problem p where p.id in ( select pl.problemid from Pl pl where pl.labelid = ?1) and p.reply = 0 order by p.createtime desc ")
    Page<Problem> findWaitListByLabelId(String labelid, Pageable pageable);
}
