package com.example.awsproject.web;

import com.example.awsproject.config.auth.LoginUser;
import com.example.awsproject.config.auth.dto.SessionUser;
import com.example.awsproject.service.posts.PostsService;
import com.example.awsproject.web.dto.PostsResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import javax.servlet.http.HttpSession;

@RequiredArgsConstructor
@Controller
public class IndexController {
    private final PostsService postsService;
    private final HttpSession httpSession;


/*    @GetMapping("/")
    public String index(Model model){
        model.addAttribute("posts",postsService.findAllDesc());
        return "index";
    }*/

    @GetMapping("/posts/save")
    public String postsSave(){
        return "posts-save";
    }

    @GetMapping("/posts/update/{id}")
    public String postsUpdate(@PathVariable Long id,Model model){
        PostsResponseDto dto=postsService.findById(id);
        model.addAttribute("post",dto);
        return "posts-update";
    }

    @GetMapping("/")
    public String index(Model model, @LoginUser SessionUser user) {

        model.addAttribute("posts", postsService.findAllDesc());

        //SessionUser user = (SessionUser) httpSession.getAttribute("user");//
        // (1)
        if(user != null) { // (2)
            System.out.println("userName" + user.getName());
            model.addAttribute("userName", user.getName());
            model.addAttribute("test1", user.getName());
            model.addAttribute("test", user.getEmail());
            //System.out.println(model.getAttribute("model get="+"userName"));
        }
        return "index";
    }

}
