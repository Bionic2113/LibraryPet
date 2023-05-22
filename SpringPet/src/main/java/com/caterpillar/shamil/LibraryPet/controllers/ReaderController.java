package com.caterpillar.shamil.LibraryPet.controllers;

import jakarta.servlet.http.HttpSession;
import lombok.extern.log4j.Log4j2;
import org.apache.logging.log4j.Level;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

/**
 * <h3>SpringPet</h3>
 *
 * @author Shamil Shyhiev
 * <a href="https://t.me/bionic2113">telegram<a>
 * @date 29.04.2023
 */
@Controller
@RequestMapping("/reader")
@Log4j2
public class ReaderController {

    @GetMapping()
    public String getR(){
        return "reader";
    }
    @PostMapping
    public String postR(@RequestParam(name = "title") String title,
                        @RequestParam(name = "author") String author,
                        HttpSession session) {
        try {
            if (author.equals("")) author = "Народное";
            log.log(Level.INFO, "title is {}", title);
            log.log(Level.INFO, "author is {}",author);
            // Устанавливаем URL-адрес и параметры запроса
            URL url = new URL("https://libcat.ru");
            String value = title.replaceAll(" ","+") + "+" + author.replaceAll(" ","+");
            String parameters = "story="+value+"&do=search&subaction=search&titleonly=3";

            // Создаем объект HttpURLConnection и настраиваем его для отправки POST-запроса
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            connection.setRequestProperty("Content-Length", Integer.toString(parameters.getBytes(StandardCharsets.UTF_8).length));
            connection.setDoOutput(true);

            // Отправляем параметры запроса
            OutputStream os = connection.getOutputStream();
            os.write(parameters.getBytes(StandardCharsets.UTF_8));
            os.flush();
            os.close();

            // Получаем ответ от сервера
            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String inputLine;
            StringBuilder response = new StringBuilder();
            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();
            Document document = Jsoup.parse(response.toString(), "libcat.ru");
            Elements element = document.select("#dle-content > div.tg-bestsellingbooksslider.v3 > div:nth-child(1) > div > a");
            String link = element.attr("href");
            log.log(Level.INFO, "link is {},", link);
//            log.log(Level.INFO,"element size is {}",element.size());
//            log.log(Level.INFO,"elements.get(0) = {}",element.get(0));
//            log.log(Level.INFO, "elements.outerHtml(); = {}",element.outerHtml());

            // Записываем ссылку для перехода к разделу с этой книгой
            session.setAttribute("link", link.substring(0, link.lastIndexOf("/")+1));
            // Парсим страницу и забираем блок с текстом и ссылками на следующие страницы
            Document doc = Jsoup.connect(link).get();
            Elements elements = doc.select("#text");
            log.log(Level.INFO,"elements size is {}",elements.size());
            // Сохраняем информацию в сессии, чтобы другой контроллер мог передать блок в html
            session.setAttribute("newPage",elements.outerHtml());
            return "redirect:/reader/book/new";
        } catch (Exception e) {
            return "redirect:/reader?not_found";
        }
    }
    @GetMapping("/book/{path}")
    public String nextPage(@PathVariable String path, Model model, HttpSession session){
        if(path.equals("new")){
            String newPage = (String) session.getAttribute("newPage");
            model.addAttribute("newPage",newPage);
        }
        else{
            Document doc = null;
            try {
                doc = Jsoup.connect(session.getAttribute("link") + path).followRedirects(true).get();
            } catch (IOException e) {
                return "redirect:/reader?not_found";
            }
            Elements elements = doc.select("#text");
            model.addAttribute("newPage",elements.outerHtml());
        }
        return "reader";
    }
}
