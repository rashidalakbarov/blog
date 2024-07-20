package com.company.blog.service;

import com.company.blog.models.Post;
import com.company.blog.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.ArrayList;
import java.util.Optional;

@Service
public class PostService {

    @Autowired
    private PostRepository postRepository;

    public Iterable<Post> findAll(Model model) {
        Iterable<Post> posts = postRepository.findAll();
        model.addAttribute("posts", posts);

        return postRepository.findAll();
    }

    public void save(String title, String anons, String fullText) {
        Post post = new Post(title, anons, fullText);
        postRepository.save(post);
    }

    public void updatePost(long id, String title, String anons, String full_text) {
        var post = postRepository.findById(id).orElseThrow();
        post.setTitle(title);
        post.setAnons(anons);
        post.setFull_text(full_text);

        postRepository.save(post);
    }

    public void deletePost(long id) {
        Post post = postRepository.findById(id).orElseThrow();
        postRepository.delete(post);
    }

    public boolean notFoundPost(@PathVariable("id") long id,
                                Model model) {

        if (!postRepository.existsById(id)) {
            return true;
        }

        Optional<Post> post = postRepository.findById(id);
        ArrayList<Post> res = new ArrayList<>();
        post.ifPresent(res::add);
        model.addAttribute("post", res);
        return false;
    }

}
