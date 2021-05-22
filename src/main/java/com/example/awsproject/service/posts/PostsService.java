package com.example.awsproject.service.posts;

import com.example.awsproject.domain.posts.Posts;
import com.example.awsproject.domain.posts.PostsRepository;
import com.example.awsproject.web.dto.PostsListResponseDto;
import com.example.awsproject.web.dto.PostsResponseDto;
import com.example.awsproject.web.dto.PostsSaveRequestDto;
import com.example.awsproject.web.dto.PostsUpdateRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class PostsService {
    private final PostsRepository postsRepository;

    @Transactional
    public Long save(PostsSaveRequestDto requestDto){
        return postsRepository.save(requestDto.toEntity()).getId();
    }

    @Transactional
    public Long update(Long id, PostsUpdateRequestDto requestDto){
        Posts posts=postsRepository.findById(id).orElseThrow(()->new IllegalArgumentException("해당 게시글이 없습니다. id=" + id));
        posts.update(requestDto.getTitle(),requestDto.getContent());
        return id;
    }

    @Transactional// (1)
    public List<PostsListResponseDto> findAllDesc() {
        return postsRepository.findAllDesc().stream()
                .map(PostsListResponseDto::new) // (2)
                .collect(Collectors.toList());
    }

    @Transactional
    public void delete(Long id){
        Posts posts = postsRepository.findById(id).orElseThrow( () ->new IllegalArgumentException("해당게시글이 없습니다. id=" +id));
        postsRepository.delete(posts);
    }

    public PostsResponseDto findById(Long id){
        Posts entity = postsRepository.findById(id).orElseThrow(()->new IllegalArgumentException("해당 게시글이 없습니다. id=" + id));

        return new PostsResponseDto(entity);
    }
}
