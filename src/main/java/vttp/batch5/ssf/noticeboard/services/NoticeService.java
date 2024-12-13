package vttp.batch5.ssf.noticeboard.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
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
	public ResponseEntity<JsonObject> postToNoticeServer(JsonObject requestJson, org.springframework.http.HttpHeaders headers) {
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
