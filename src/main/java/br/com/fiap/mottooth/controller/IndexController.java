package br.com.fiap.mottooth.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.view.RedirectView;

@Controller
public class IndexController {

    @GetMapping("/")
    public RedirectView index() {
        return new RedirectView("/swagger-ui.html");
    }

    @GetMapping("/api")
    public RedirectView api() {
        return new RedirectView("/swagger-ui.html");
    }
}