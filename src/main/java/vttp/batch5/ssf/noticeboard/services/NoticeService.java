package vttp.batch5.ssf.noticeboard.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpHeaders;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import jakarta.json.JsonObject;
import vttp.batch5.ssf.noticeboard.repositories.NoticeRepository;

@Service
public class NoticeService {
	@Autowired
	NoticeRepository noticeRepo;
	@Value("${noticeboard.db.url}")
  	private String url;
	//TODO NOTE - why is my manual printing of the json object that i have created working when i put in postman, but it returns payload format error here?
	public ResponseEntity<JsonObject> postToNoticeServer(JsonObject requestJson, HttpHeaders headers) {
		RequestEntity<JsonObject> rq = RequestEntity.put(url).headers(headers).body(requestJson);
		RestTemplate rt = new RestTemplate();
		//exchange with the server
		try{
			ResponseEntity<JsonObject> serverResponse = rt.exchange(url, HttpMethod.POST, rq, JsonObject.class);
			String id = serverResponse.getBody().getString("id");
			//convert idJson to a string type
			noticeRepo.insertNotices(id,serverResponse);
			//return successPage if successful
			return serverResponse;
		}
		catch (Exception e){
			noticeRepo.addFail(e);
			System.out.println(e.getMessage());
		}
		return null;
		//return error page if failed
	}
    public String getPayLoad(String id) {
		return noticeRepo.getPayLoad(id);
	}
	public String getErrorMessage() {
		return noticeRepo.getErrorMessage();
	}

}
