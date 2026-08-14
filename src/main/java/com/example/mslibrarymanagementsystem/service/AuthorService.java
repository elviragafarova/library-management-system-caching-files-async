package com.example.mslibrarymanagementsystem.service;

import com.example.mslibrarymanagementsystem.criteria.PageCriteria;
import com.example.mslibrarymanagementsystem.dto.request.AuthorRequest;
import com.example.mslibrarymanagementsystem.dto.response.AuthorResponse;
import com.example.mslibrarymanagementsystem.entity.AuthorEntity;
import com.example.mslibrarymanagementsystem.exceptions.AuthorNotFoundException;
import com.example.mslibrarymanagementsystem.mapper.AuthorMapper;
import com.example.mslibrarymanagementsystem.repository.AuthorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthorService {
    private final AuthorRepository authorRepository;

    public void createAuthor(AuthorRequest authorRequest) {
        var author = AuthorMapper.toEntity(authorRequest);
        authorRepository.save(author);
    }

    public Page<AuthorResponse> getAuthors(PageCriteria pageCriteria) {
        Sort.Direction direction = Sort.Direction.fromString(pageCriteria.getDirection());
        var pageable = PageRequest.of(
                pageCriteria.getPage(),
                pageCriteria.getCount(),
                Sort.by(direction, pageCriteria.getSortBy()));
        Page<AuthorEntity> authors = authorRepository.findAll(pageable);
        return authors.map(AuthorMapper::toResponse);
    }

    public AuthorResponse getAuthorById(Long id) {
        var author = fetchAuthorIfExists(id);
        return AuthorMapper.toResponse(author);
    }

    public AuthorResponse updateAuthor(Long id, AuthorRequest request) {
        var author = fetchAuthorIfExists(id);
        author.setName(request.getName());
        var updatedAuthor = authorRepository.save(author);
        return AuthorMapper.toResponse(updatedAuthor);
    }

    public void deleteAuthor(Long id) {
        var author = fetchAuthorIfExists(id);
        authorRepository.delete(author);
    }

    private AuthorEntity fetchAuthorIfExists(Long id) {
        return authorRepository.findById(id)
                .orElseThrow(() ->
                        new AuthorNotFoundException("Author not found with id: " + id));
    }
}