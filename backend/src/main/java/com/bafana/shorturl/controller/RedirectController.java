package com.bafana.shorturl.controller;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.bafana.shorturl.entity.Link;
import com.bafana.shorturl.service.LinkService;

@Controller
public class RedirectController {

  private final LinkService linkService;

  public RedirectController(LinkService linksService) {
    this.linkService = linksService;
  }

  @GetMapping("/{code}")
  public ResponseEntity<Void> redirect(@PathVariable String code) {
    Link link = linkService.incrementClickCount(code);

    return ResponseEntity.status(HttpStatus.FOUND).header(HttpHeaders.LOCATION, link.getOriginalUrl()).build();
  }
}
