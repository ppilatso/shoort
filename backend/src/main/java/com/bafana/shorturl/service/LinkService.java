package com.bafana.shorturl.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.bafana.shorturl.entity.Link;
import com.bafana.shorturl.exception.LinkNotFoundException;
import com.bafana.shorturl.repository.LinkRepository;
import com.bafana.shorturl.util.Base62;

import jakarta.transaction.Transactional;

@Service
public class LinkService {
  private final LinkRepository linkRepository;

  public LinkService(LinkRepository linkRepository) {
    this.linkRepository = linkRepository;
  }

  @Transactional
  public Link create(String originalUrl) {
    Link link = new Link(originalUrl);

    link = linkRepository.save(link);
    String shortCode = Base62.encode(link.getId());
    link.setShortCode(shortCode);

    return linkRepository.save(link);
  }

  public List<Link> findAll() {

    return linkRepository.findAll();
  }

  public Link findByCode(String code) {

    return linkRepository
      .findByShortCode(code)
      .orElseThrow(() -> new LinkNotFoundException(code));
  }

  @Transactional
  public Link incrementClickCount(String code) {
     Link link = findByCode(code);

     linkRepository.incrementClickCount(link.getShortCode());

     return findByCode(code);
  }

  @Transactional
  public void delete(String code) {
    Link link = findByCode(code);

    linkRepository.delete(link);
  }
}
