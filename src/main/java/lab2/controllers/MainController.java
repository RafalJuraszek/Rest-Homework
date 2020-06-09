package lab2.controllers;


import lab2.model.Form;
import lab2.model.RequestData;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
public class MainController {

    @PostMapping("/forecast")
    public String start(Model model, int number)
    {
        List<RequestData> requestData = new ArrayList<RequestData>();
        for(int i=0; i<number;i++)
        {
            requestData.add(new RequestData());
        }
        Form form = new Form(requestData);
        model.addAttribute("form",form);

        return "form";
    }

    @RequestMapping
    public String start()
    {
        return "main";
    }


}
