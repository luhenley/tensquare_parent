package com.tensquare.friend.service;

import com.tensquare.friend.client.UserClient;
import com.tensquare.friend.dao.FriendDao;
import com.tensquare.friend.dao.NoFriendDao;
import com.tensquare.friend.pojo.Friend;
import com.tensquare.friend.pojo.NoFriend;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @Author Lu.Henley
 * @Date File Created at 2023-03-08
 * @Version 1.0
 */
@Service
public class FriendService {

    @Autowired
    private FriendDao friendDao;
    @Autowired
    private NoFriendDao noFriendDao;
    @Autowired
    private UserClient userClient;

    @Transactional
    public int addFriend(String userid, String friendid) {
        //判断如果用户已经添加了这个好友，则不进行任何操作,返回 0
        if (friendDao.selectCount(userid,friendid)>0){
            return 0;
        }
        //向喜欢表中添加记录,添加好友
        Friend friend = new Friend();
        friend.setUserid(userid);
        friend.setFriendid(friendid);
        friend.setIslike("0");
        friendDao.save(friend);

        //判断对方是否喜欢你，如果喜欢，将 islike设置为 1
        //判断对方是否也加过当前用户，如果加过的话，把双方的好友记录的islike都改为1
        if (friendDao.selectCount(friendid,userid)>0){
            friendDao.updateLike(userid,friendid,"1");
            friendDao.updateLike(friendid,userid,"1");
        }

        //更新关注数和粉丝数
        userClient.updateFollowcount(userid,1);
        userClient.updateFanscount(friendid,1);

        return 1;
    }

    /**
     * 添加非好友
     */
    public void addNoFriend(String userid, String friendid) {
        NoFriend noFriend = new NoFriend();
        noFriend.setUserid(userid);
        noFriend.setFriendid(friendid);
        noFriendDao.save(noFriend);
    }
    /**
     * 删除好友
     * */
    @Transactional
    public void deleteFriend(String userid, String friendid) {
        //1.把当前好友记录删除
        friendDao.deleteFriend(userid,friendid);
        //2.判断对方有没有加过当前用户，加过的话，把对方的记录islike改为0
        if (friendDao.selectCount(friendid,userid)>0){
            friendDao.updateLike(friendid,userid,"0");
        }

        //更新关注数和粉丝数
        userClient.updateFollowcount(userid,-1);
        userClient.updateFanscount(friendid,-1);
    }
}
