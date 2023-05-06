package com.tensquare.spit.dao;


import com.tensquare.spit.pojo.Spit;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * 吐槽dao接口
 * MongoRepository类似于JpaRepository，拥有CRUD方法
 */
public interface SpitDao extends MongoRepository<Spit,String> {
    Page<Spit> findByParentid(String parentid, Pageable pageable);
}
