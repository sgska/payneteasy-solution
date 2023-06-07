package org.payneteasy.solution.web.controller;

import org.payneteasy.solution.context.ApplicationContext;
import org.payneteasy.solution.service.PageService;
import org.payneteasy.solution.web.entity.RequestEntity;
import org.payneteasy.solution.web.entity.ResponseEntity;

import java.util.Objects;

public class MainController extends AbstractFilesController {

    private PageService pageService;
    private PageService getPageService() {
        if (Objects.isNull(pageService)) {
            pageService = ApplicationContext.getBean(PageService.class);
        }
        return pageService;
    }

    @Override
    public ResponseEntity processGet(RequestEntity request) {
        return new ResponseEntity(getPageService().getMain());
    }

    @Override
    public String getPath() {
        return "/main";
    }

    @Override
    public String getContentType() {
        return " text/html";
    }
}
