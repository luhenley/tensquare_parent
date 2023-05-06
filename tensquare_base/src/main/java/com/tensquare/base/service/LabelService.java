package com.tensquare.base.service;

import com.tensquare.base.dao.LabelDao;
import com.tensquare.base.pojo.Label;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import util.IdWorker;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 标签service
 */
@Service
public class LabelService {

    @Autowired
    private LabelDao labelDao;

    @Autowired
    private IdWorker idWorker;

    /**
     * 查询所有
     */
    public List<Label> findAll(){
        return labelDao.findAll();
    }

    /**
     * 查询一个
     */
    public Label findById(String id){
        return labelDao.findById(id).get();
    }


    /**
     * 添加
     */
    public void add(Label label){
        //设置id
        label.setId(idWorker.nextId()+"");
        labelDao.save(label);
    }

    /**
     * 修改
     */
    public void update(Label label){ // label必须带有数据库存在的id
        labelDao.save(label);
    }

    /**
     * 删除
     */
    public void delete(String id){
        labelDao.deleteById(id);
    }

    //创建查询条件
    private Specification<Label> createSpecification(Map searchMap){
        return new Specification<Label>() {
            @Override
            public Predicate toPredicate(Root<Label> root,
                                         CriteriaQuery<?> criteriaQuery,
                                         CriteriaBuilder criteriaBuilder) {
                List<Predicate> preList = new ArrayList<Predicate>();
                //根据标签名模糊查询
                if (searchMap.get("labelname") != null && !(searchMap.get("labelname")).equals("")){
                    preList.add(criteriaBuilder.like(root.get("labelname").as(String.class),"%" + searchMap.get("labelname") + "%"));
                }
                //根据状态查询
                if (searchMap.get("state") != null && !(searchMap.get("state")).equals("")){
                    preList.add(criteriaBuilder.equal(root.get("state").as(String.class),searchMap.get("state")));
                }
                //根据是否推荐查询
                if (searchMap.get("recommend") != null && !(searchMap.get("recommend")).equals("")){
                    preList.add(criteriaBuilder.equal(root.get("recommend").as(String.class),searchMap.get("recommend")));
                }
                Predicate[] preArray = new Predicate[preList.size()];
                return criteriaBuilder.and(preList.toArray(preArray));
            }
        };
    }

    // 条件查询
    public List<Label> findSearch(Map searchMap) {
        Specification<Label> specification = createSpecification(searchMap);
        return labelDao.findAll(specification);
    }

    /**
     *分页+条件查询
     */
    public Page<Label> findSearch(Map searchMap, int page, int size) {
        Specification<Label> specification = createSpecification(searchMap);
        PageRequest pageRequest = PageRequest.of(page-1,size);
        return labelDao.findAll(specification,pageRequest);
    }
}
