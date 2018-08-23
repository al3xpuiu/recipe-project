package com.bishoptod3.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by Loky on 15/08/2018.
 */
@Controller
public class IndexController {

    @RequestMapping({"/", "", "/index"})
    public String getIndexPage() {
        System.out.println("Just a msg..123");
        return "index";
    }
}
