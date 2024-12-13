package vttp.batch5.ssf.noticeboard.models;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class Notice {
    //3 to 128 characters mandatory
    @NotBlank(message="This is a required field.")
    @Size(min=3, max=128, message = "Title must contain 3 to 128 characters.")
    private String title;
    //email address mandatory
    @NotBlank(message="This is a required field.")
    @Email(message="Please enter a valid email address.")
    private String poster;
    //date in yyyy-MM-dd mandatory
    @NotNull(message="This is a required field.")
    @DateTimeFormat(pattern="yyyy-MM-dd")
    @Future(message= "The Date must be from the future.")
    private LocalDate postDate;
    @NotBlank(message="Please select at least one category.")
    private String categories;
    //contents of the notice, mandatory
    @NotBlank(message="This is a required field.")
    private String text;


    public Notice() {
    }


    public Notice(
            @NotBlank(message = "This is a required field.") @Size(min = 3, max = 128, message = "Title must contain 3 to 128 characters.") String title,
            @NotBlank(message = "This is a required field.") @Email(message = "Please enter a valid email address.") String poster,
            @NotNull(message = "This is a required field.") @Future(message = "The Date must be from the future.") LocalDate postDate,
            @NotBlank(message = "Please select at least one category.") String categories,
            @NotBlank(message = "This is a required field.") String text) {
        this.title = title;
        this.poster = poster;
        this.postDate = postDate;
        this.categories = categories;
        this.text = text;
    }


    public String getTitle() {
        return title;
    }


    public void setTitle(String title) {
        this.title = title;
    }


    public String getPoster() {
        return poster;
    }


    public void setPoster(String poster) {
        this.poster = poster;
    }
    
    
    public long getPostDateLong() throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MMM-yyyy");
        Date d = sdf.parse(postDate.toString());
        long milliseconds = d.getTime();
        return milliseconds;
        
        
    }


    public void setPostDate(LocalDate postDate) {
        this.postDate = postDate;
    }


    public List<String> getCategoriesList() {
        //
        List<String> categoryList = new ArrayList<>();
        String[] cats = categories.split(",");
        for (String cat : cats){
            categoryList.add(cat);
        }
        
        return categoryList;
    }


    public void setCategories(String categories) {
        this.categories = categories;
    }


    public String getText() {
        return text;
    }


    public void setText(String text) {
        this.text = text;
    }


    public LocalDate getPostDate() {
        return postDate;
    }


    public String getCategories() {
        return categories;
    }

    
}
