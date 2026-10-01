package com.bafana.shorturl.controller;

import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bafana.shorturl.entity.Link;
import com.bafana.shorturl.service.LinkService;

@RestController
@RequestMapping ("/api/links")
public class LinkController {

  private final LinkService linkService;

  public LinkController(LinkService linkService) {
    this.linkService = linkService;
  }

  @PostMapping
  public ResponseEntity<Map<String, String>> create (@RequestBody CreateLinkRequest request) {

    Link link = linkService.create(request.url());

    final String shortUrl = "http://localhost:8080/" + link.getShortCode();

    return ResponseEntity.status(HttpStatus.CREATED)
    .body(Map.of(
      "shortCode", link.getShortCode(),
      "shortUrl", shortUrl));
  }

  @GetMapping
  public List<Link> findAll() {
    return linkService.findAll();
  }

  @GetMapping("/{code}")
  public Link findByCode(@PathVariable String code) {
    return linkService.findByCode(code);
  }

  @DeleteMapping("/{code}")
  public ResponseEntity<Void> delete(@PathVariable String code) {
    linkService.delete(code);

    return ResponseEntity.noContent().build();
  }

  public record CreateLinkRequest(String url) {}
}
