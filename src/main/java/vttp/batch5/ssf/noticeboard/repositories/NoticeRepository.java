package vttp.batch5.ssf.noticeboard.repositories;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;

import jakarta.json.JsonObject;


@Repository
public class NoticeRepository {
	@Autowired
	RedisTemplate<String,Object> template;
	
	public void insertNotices(String id, ResponseEntity<JsonObject> serverResponse) { 
	/*
		This is the command for inserting via CLI
		hset SSFProj id serverResponse
	 */
	template.opsForHash().put("SSFProj", id, serverResponse );
	
	}

    public void addFail(Exception e) {
		template.opsForHash().delete("SSFProj", "fail");
		template.opsForHash().put("SSFProj", "fail", e.toString());
        
    }

    public String getPayLoad(String id) {
		template.opsForHash().get("SSFProj",id);
		return (String) template.opsForHash().get("SSFProj",id);
    }

    public String getErrorMessage() {
		String error = (String) template.opsForHash().get("SSFProj", "fail");
		template.opsForHash().delete("SSFProj", "fail");
		return error;
	}


}
