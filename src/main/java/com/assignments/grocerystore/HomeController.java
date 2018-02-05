package com.assignments.grocerystore;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;


@Controller
public class HomeController {

    @Autowired
    CleaningItemsRepository cleaningItemsRepository;

    @Autowired
    CosmeticsRepository cosmeticsRepository;

    @Autowired
    SnacksRepository snacksRepository;

    double cleaningItemsTotalPrice=0.0;
    double cosmeticsTotalPrice=0.0;
    double snacksTotalPrice=0.0;
    double expectedTotalRevenue=0.0;

    @RequestMapping("/")
    public String storeIndex(Model model){
        return "index";
    }

    @GetMapping("/cleaningitems")
    public String cleaningItemsForm(Model model){
        model.addAttribute("cleaningitems",new CleaningItems());
        return "cleaningitems";
    }

    @PostMapping("/cleaningitems")
    public String cleaningItemsprocessForm(@Valid CleaningItems cleaningItems, BindingResult result){
        if(result.hasErrors()){
            return "cleaningitems";
        }
        cleaningItemsRepository.save(cleaningItems);
        cleaningItemsTotalPrice+=((cleaningItems.getPrice())*(cleaningItems.getQuantity()));
        return "redirect:/";
    }

    @GetMapping("/cosmetics")
    public String cosmeticsForm(Model model){
        model.addAttribute("cosmetics",new Cosmetics());
        return "cosmetics";
    }

    @PostMapping("/cosmetics")
    public String cosmeticsProcessForm(@Valid Cosmetics cosmetics, BindingResult result){
        if(result.hasErrors()){
            return "cosmetics";
        }
        cosmeticsRepository.save(cosmetics);
        cosmeticsTotalPrice+=((cosmetics.getPrice())*(cosmetics.getQuantity()));
        return "redirect:/";
    }

    @GetMapping("/snacks")
    public String snacksForm(Model model){
        model.addAttribute("snacks",new Snacks());

        return "snacks";
    }

    @PostMapping("/snacks")
    public String snacksProcessForm(@Valid Snacks snacks, BindingResult result){
        if(result.hasErrors()){
            return "snacks";
        }
        snacksRepository.save(snacks);
        snacksTotalPrice+=((snacks.getPrice())*(snacks.getQuantity()));
        return "redirect:/";
    }

    @RequestMapping("/jbcstore")
    public String jbcstoreIndex(Model model){

        model.addAttribute("cleaningitems_",cleaningItemsRepository.findAll());
        model.addAttribute("cosmetics_",cosmeticsRepository.findAll());
        model.addAttribute("snacks_",snacksRepository.findAll());
        expectedTotalRevenue=cleaningItemsTotalPrice+snacksTotalPrice+cosmeticsTotalPrice;
        model.addAttribute("expectedTotalRevenue",expectedTotalRevenue);

        return "jbcstore";
    }


}
