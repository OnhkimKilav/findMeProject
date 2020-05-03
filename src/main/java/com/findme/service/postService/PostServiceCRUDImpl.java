package com.findme.service.postService;

import com.findme.dao.ICRUDDAO;
import com.findme.exception.BadRequestException;
import com.findme.model.Post;
import com.findme.service.IServiceCRAD;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;

@Service
@Scope(proxyMode = ScopedProxyMode.TARGET_CLASS)
public class PostServiceCRUDImpl implements IServiceCRAD<Post> {
    private ICRUDDAO<Post> icruddao;

    @Autowired
    public PostServiceCRUDImpl(ICRUDDAO<Post> icruddao) {
        this.icruddao = icruddao;
    }

    @Override
    public Post save(Post post) throws RuntimeException {
        banLinks(post.getMessage());
        if (post.getMessage().length() > 200)
            throw new BadRequestException("your message is longer than 200 characters.");
        return icruddao.save(post);
    }

    @Override
    public Post findById(Long id) {
        return icruddao.findById(id);
    }

    @Override
    public void delete(Long id) {
        icruddao.delete(id);
    }

    @Override
    public void update(Post post) {
        icruddao.update(post);
    }

    private static void banLinks(String message) {
        String[] wordsMessage = message.split(" ");
        for (int i = 0; i < wordsMessage.length; i++) {
            if ((wordsMessage[i]).contains("www.") || (wordsMessage[i]).contains("http://") ||
                    (wordsMessage[i]).contains("https://"))
                throw new BadRequestException("You can't use links in the post message");
        }
    }
}
