package vttp.batch5.ssf.noticeboard.controllers;

import java.text.ParseException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

import jakarta.json.Json;
import jakarta.json.JsonArray;
import jakarta.json.JsonArrayBuilder;
import jakarta.json.JsonObject;
import jakarta.validation.Valid;
import vttp.batch5.ssf.noticeboard.models.Notice;
import vttp.batch5.ssf.noticeboard.services.NoticeService;
import org.springframework.web.bind.annotation.PostMapping;



// Use this class to write your request handlers
@Controller
@RequestMapping("/notice")
public class NoticeController {
    @Autowired
    NoticeService noticeService;
    @GetMapping("")
    public String gotoHome(Model model) {
        Notice notice = new Notice();
        model.addAttribute(notice);
        return "notice";
    }
    @PostMapping("")
    public String homePageValidation(@Valid @ModelAttribute("notice") Notice notice, BindingResult result, Model model) throws ParseException {
        if(result.hasErrors()){
            return "notice";
        }
        List<String> categoryArray = notice.getCategoriesList();
        JsonArrayBuilder jab = Json.createArrayBuilder();
        
        for (String category: categoryArray){
            jab.add(category);    
        }
        JsonArray catObj = jab.build();
        JsonObject responseJson  = Json.createObjectBuilder()
                        .add("title",notice.getTitle())
                        .add("poster",notice.getPoster())
                        .add("postDate",notice.getPostDateLong())
                        .add("categories",catObj)
                        .add("text",notice.getText())
                        .build();
        System.out.println(responseJson);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        
        ResponseEntity<JsonObject> rresult = noticeService.postToNoticeServer(responseJson,headers);       

        //add conditional to check for the response
        if (rresult==null){
            String errorMessage = noticeService.getErrorMessage();
            model.addAttribute("errorMessage",errorMessage);
            return "failure";
        } 
        else{
            //how to get unique id?
            String id = rresult.getBody().getString("id");
            String message = "The notice posting id is "+ noticeService.getPayLoad(id);
            model.addAttribute("message",message);
            return "success";
        }

    }   
    
    
}
