package vttp.batch5.ssf.noticeboard.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

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
    public String homePageValidation(@Valid @ModelAttribute("notice") Notice notice, BindingResult result, Model model) {
        if(result.hasErrors()){
            return "notice";
        }
        Notice currNotice =  
        //get the data received from the post request, if it is successful then display success page if not then failure page
        return "redirect:/getApi";
    }
    
    
}
