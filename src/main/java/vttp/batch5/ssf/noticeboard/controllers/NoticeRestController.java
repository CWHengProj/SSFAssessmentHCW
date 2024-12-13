package vttp.batch5.ssf.noticeboard.controllers;
import java.io.IOException;
import java.text.ParseException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import jakarta.json.Json;
import jakarta.json.JsonObject;
import jakarta.servlet.http.HttpServletResponse;
import vttp.batch5.ssf.noticeboard.models.Notice;
import vttp.batch5.ssf.noticeboard.services.NoticeService;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;


@RestController
@RequestMapping("")
public class NoticeRestController {
    @Autowired 
    NoticeService noticeService;
    @GetMapping("/getApi")
    public ResponseEntity<JsonObject> postToNoticeServer(@ModelAttribute("notice") Notice notice, HttpServletResponse response) throws ParseException, IOException {
        //how to get the user information from the controller?
        JsonObject responseJson = Json.createObjectBuilder()
                        .add("title",notice.getTitle())
                        .add("poster",notice.getPoster())
                        .add("postDate",notice.getPostDateLong())
                        .add("categories",notice.getCategoriesList().toString())
                        .add("text",notice.getText())
                        .build();
        System.out.println(responseJson);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        
        ResponseEntity<JsonObject> result = noticeService.postToNoticeServer(responseJson,headers);
        return result;
    }
    
    
}
