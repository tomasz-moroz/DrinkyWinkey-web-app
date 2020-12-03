package com.infoshare.servlet;

import com.infoshare.freemarker.TemplateProvider;
import com.infoshare.utils.Utils;
import freemarker.template.Template;
import freemarker.template.TemplateException;

import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;

@WebServlet("/Age-query")
public class AgeQueryServlet extends HttpServlet {

    @Inject
    TemplateProvider templateProvider;
    @Inject
    Utils utils;

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");

        //create Siara Boss user
        utils.createRandomUserDto();

        Map<String, Object> root = new HashMap<>();

        Template template = templateProvider.getTemplate(getServletContext(), "age-query.ftlh");
        Writer out = response.getWriter();

        try {
            template.process(root, out);
        } catch (TemplateException e) {
            e.printStackTrace();
        }
    }
}
