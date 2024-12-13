package vttp.batch5.ssf.noticeboard.repositories;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;


@Repository
public class NoticeRepository {
	@Autowired
	RedisTemplate<String,Object> template;
	
	public void insertNotices(String id) { 
	/*
		This is the command for inserting via CLI
		hset SSFProj jsonPayload id
		where ID is the variable name of the payload we received earlier
	 */
	template.opsForHash().put("SSFProj", "jsonPayload", id);
	
	}

    public void addFail(Exception e) {
		template.opsForHash().put("SSFProj", "jsonPayload", e.toString());
        
    }


}
