package com.fag.mapas;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@SpringBootApplication
@Controller
public class MapasApplication {

    public static void main(String[] args) {
        SpringApplication.run(MapasApplication.class, args);
    }

    @RequestMapping("/")
    public ModelAndView index() {
        ModelAndView mv = new ModelAndView("mapas/index");
        return mv;
    }

    @RequestMapping("/mapas/equipamento")
    public ModelAndView mapaEquipamento() {
        ModelAndView mv = new ModelAndView("mapas/MapaEquipamento");
        return mv;
    }
    
    @RequestMapping("/mapas/equipamentocores")
    public ModelAndView mapaEquipamentocores() {
        ModelAndView mv = new ModelAndView("mapas/MapaEquipamentocores");
        return mv;
    }

    @RequestMapping("/mapas/gps")
    public ModelAndView mapaGPS() {
        ModelAndView mv = new ModelAndView("mapas/MapaGPS");
        return mv;
    }

    @RequestMapping("/mapas/municipio")
    public ModelAndView mapaMunicipio() {
        ModelAndView mv = new ModelAndView("mapas/MapaMunicipio");
        return mv;
    }

}
