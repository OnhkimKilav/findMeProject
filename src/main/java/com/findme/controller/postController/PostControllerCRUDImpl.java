package com.findme.controller.postController;

import com.findme.controller.IControllerCRAD;
import com.findme.model.Post;
import com.findme.service.IServiceCRAD;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.IOException;

@Controller
public class PostControllerCRUDImpl implements IControllerCRAD{
    private IServiceCRAD<Post> iServiceCRAD;

    @Autowired
    public PostControllerCRUDImpl(IServiceCRAD<Post> iServiceCRAD) {
        this.iServiceCRAD = iServiceCRAD;
    }

    @Override
    @RequestMapping(value = "/savePost", method = RequestMethod.POST, produces = "text/plain")
    public ResponseEntity<String> save(HttpServletRequest req) {
        Post post = null;
        try {
            post = iServiceCRAD.save(readValuesPostman(req));
        } catch (Exception e) {
            return new ResponseEntity<String>(e.getMessage(), null, null);
        }
        return new ResponseEntity<String>("Ok : " + post.toString(), HttpStatus.CREATED);
    }

    @Override
    @RequestMapping(value = "/findPost", method = RequestMethod.GET, produces = "text/plain")
    public ResponseEntity<String> findById(HttpServletRequest req) {
        Post post = null;
        try {
            post = iServiceCRAD.findById(Long.parseLong(req.getParameter("id")));
        } catch (Exception e) {
            return new ResponseEntity<String>(e.getMessage(), null, null);
        }
        return new ResponseEntity<String>("Ok : " + post.toString(), HttpStatus.OK);
    }

    @Override
    @RequestMapping(value = "/deletePost", method = RequestMethod.DELETE, produces = "text/plain")
    public ResponseEntity<String> delete(HttpServletRequest req) {
        try {
            iServiceCRAD.delete(Long.parseLong(req.getParameter("id")));
        } catch (Exception e) {
            return new ResponseEntity<String>(e.getMessage(), null, null);
        }
        return new ResponseEntity<String>("Ok", HttpStatus.OK);
    }

    @Override
    @RequestMapping(value = "/updatePost", method = RequestMethod.PUT, produces = "text/plain")
    public ResponseEntity<String> update(HttpServletRequest req) {
        try {
            iServiceCRAD.update(readValuesPostman(req));
        } catch (Exception e) {
            return new ResponseEntity<String>(e.getMessage(), null, null);
        }
        return new ResponseEntity<String>("Ok", HttpStatus.OK);
    }

    private Post readValuesPostman(HttpServletRequest req) throws IOException {
        Post post;
        try (BufferedReader reader = req.getReader()) {
            Gson gson = new Gson();
            post = gson.fromJson(reader, Post.class);
        }
        return post;
    }
}
