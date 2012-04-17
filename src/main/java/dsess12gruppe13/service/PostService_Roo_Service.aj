// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package dsess12gruppe13.service;

import dsess12gruppe13.domain.Post;
import dsess12gruppe13.service.PostService;
import java.math.BigInteger;
import java.util.List;

privileged aspect PostService_Roo_Service {
    
    public abstract long PostService.countAllPosts();    
    public abstract void PostService.deletePost(Post post);    
    public abstract Post PostService.findPost(BigInteger id);    
    public abstract List<Post> PostService.findAllPosts();    
    public abstract List<Post> PostService.findPostEntries(int firstResult, int maxResults);    
    public abstract void PostService.savePost(Post post);    
    public abstract Post PostService.updatePost(Post post);    
}
