package digitalhands.az.service;

import digitalhands.az.entity.BlogPost;
import digitalhands.az.entity.User;
import digitalhands.az.enums.UserRole;
import digitalhands.az.exception.BlogPostNotFoundException;
import digitalhands.az.exception.UserNotFoundException;
import digitalhands.az.exception.errors.ErrorMessage;
import digitalhands.az.mappers.BlogPostMapper;
import digitalhands.az.repository.BlogPostRepository;
import digitalhands.az.repository.UserRepository;
import digitalhands.az.request.BlogPostRequest;
import digitalhands.az.response.BlogPostResponse;
import digitalhands.az.wrapper.BlogPostWrapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class BlogPostService {

    private final BlogPostRepository blogPostRepository;
    private final UserRepository userRepository;
    private final BlogPostMapper blogPostMapper;

    public ResponseEntity<BlogPostResponse> createBlog(BlogPostRequest blogPostRequest, Long userId) {
        User user = userRepository.findById(userId).orElseThrow(
                () -> new UserNotFoundException(ErrorMessage.USER_NOT_FOUND));
        if (Objects.nonNull(user) && user.getUserRole().equals(UserRole.ADMIN)) {
            BlogPost blogPost = blogPostRepository.save(blogPostMapper.fromRequestToModel(blogPostRequest));
            return ResponseEntity.status(HttpStatus.OK).body(blogPostMapper.fromModelToResponse(blogPost));
        } else
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    public ResponseEntity<BlogPostResponse> updateBlog(BlogPostRequest blogPostRequest, Long userId) {
        User user = userRepository.findById(userId).orElseThrow(
                () -> new UserNotFoundException(ErrorMessage.USER_NOT_FOUND));
        if (Objects.nonNull(user) && user.getUserRole().equals(UserRole.ADMIN)) {
            BlogPost blogPost = blogPostRepository.findById(blogPostRequest.getId()).orElseThrow(
                    () -> new BlogPostNotFoundException(ErrorMessage.BLOG_POST_NOT_FOUND));
            if (Objects.nonNull(blogPost)) {
                return ResponseEntity.status(HttpStatus.OK)
                        .body(blogPostMapper.fromModelToResponse
                                (blogPostRepository.save(blogPostMapper.fromRequestToModel(blogPostRequest))));
            } else
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } else
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    public ResponseEntity<List<BlogPostWrapper>> getAllBlogs() {
        return ResponseEntity.status(HttpStatus.OK).body(blogPostRepository.getAllBlogPosts());
    }

    public ResponseEntity<BlogPostResponse> getBlogById(Long blogId) {
        BlogPost blogPost = blogPostRepository.findById(blogId).orElseThrow(
                () -> new BlogPostNotFoundException(ErrorMessage.BLOG_POST_NOT_FOUND));
        if (Objects.nonNull(blogPost)) {
            return ResponseEntity.status(HttpStatus.OK).body(blogPostMapper.fromModelToResponse(blogPost));
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

}