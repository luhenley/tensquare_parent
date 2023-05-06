package com.tensquare.friend.dao;

import com.tensquare.friend.pojo.Friend;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

/**
 * @Author Lu.Henley
 * @Date File Created at 2023-03-08
 * @Version 1.0
 */
public interface FriendDao extends JpaRepository<Friend,String>{
    /**
     *
     根据用户 ID
     与被关注用户 ID
     查询记录个数
     * @param userid
     * @param friendid
     * @return
     */
    @Query("select count(f) from Friend f where f.userid=?1 and f.friendid=?2")
    public int selectCount(String userid,String friendid);

    /**
     *
     更新为互相喜欢
     * @param userid
     * @param friendid
     */
    @Modifying
    @Query("update Friend f set f.islike=?3 where f.userid=?1 and f.friendid=?2")
    public void updateLike(String userid,String friendid,String islike);

    @Modifying
    @Query("delete from Friend f where f.userid = ?1 and f.friendid = ?2")
    void deleteFriend(String userid, String friendid);
}
