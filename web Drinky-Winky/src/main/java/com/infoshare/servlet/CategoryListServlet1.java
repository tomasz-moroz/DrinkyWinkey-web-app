package com.infoshare.servlet;

import com.infoshare.dto.DrinkDTO;
import com.infoshare.freemarker.TemplateProvider;
import com.infoshare.model.Drink;
import com.infoshare.service.CategoryService;
import com.infoshare.service.DrinkService;
import freemarker.template.Template;
import freemarker.template.TemplateException;

import javax.inject.Inject;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.Writer;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@WebServlet("/Category-list1")
public class CategoryListServlet1 extends HttpServlet {

    @Inject
    TemplateProvider templateProvider;
    @Inject
    CategoryService categoryService;


    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        response.setContentType("text/html;charset=UTF-8");
        Map<String, Object> root = new HashMap<>();
        root.put("categories", categoryService.getCategoriesList());

        Template template = templateProvider.getTemplate(getServletContext(), "category1.ftlh");
        Writer out = response.getWriter();

        try {
            template.process(root,out);
        } catch (TemplateException e) {
            e.printStackTrace();
        }
    }
}