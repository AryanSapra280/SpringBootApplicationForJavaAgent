package com.application.mainApplication.services;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;


import com.application.mainApplication.entities.Post;
import com.application.mainApplication.repositories.IPostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PostService {

    @Autowired
    private IPostRepository postRepository;

    private static Connection connection;
    private static PreparedStatement preparedStatement;

    // public String savePost(Post post) {
    //     String postId = UUID.randomUUID().toString();
    //     post.setPostId(postId);
    //     Post savedPost = postRepository.save(post);
    //     return savedPost.getPostId();
    // }
    public Post savePost(Post post) {
        final String JDBC_URL = System.getenv("JDBC_URL");
        final String JDBC_USER = System.getenv("JDBC_USER");
        final String JDBC_PASSWORD = System.getenv("JDBC_PASSWORD");
        
        Post savedPost = null;
        try {
            connection = DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASSWORD);

            String sqlInsert = "INSERT INTO posts (post_name, post_content) VALUES (?, ?)";


            preparedStatement = connection.prepareStatement(sqlInsert, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, post.getPostName());
            preparedStatement.setString(2, post.getPostContent());


            preparedStatement.executeUpdate();
            ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
            Integer uniqueKey = null;
            if (generatedKeys.next()) {
                uniqueKey = generatedKeys.getInt(1); // Assuming 'id' is a Long type
            }
            savedPost = findById(uniqueKey);

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            // 5. Close all JDBC objects
            try {
                if (preparedStatement != null) {
                    preparedStatement.close();
                }
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return savedPost;

    }
    private Post findById(Integer id) {
        Post post = null;
        try {

            String selectQuery = "SELECT * FROM POSTS WHERE post_id = ?";


            preparedStatement = connection.prepareStatement(selectQuery);
            preparedStatement.setInt(1, id);


            ResultSet rs =  preparedStatement.executeQuery();
            post = new Post();
            while(rs.next()) {
                post.setPostId(rs.getInt("post_id"));
                post.setPostContent(rs.getString("post_content"));
                post.setPostName(rs.getString("post_name"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return post;
    }
    public String hello() {
        System.out.println("I am from hello function");
        return "hello";
    }
    public String testAgent() {
        return hello();
    }
    public List<Post> getAllPosts() {
        return postRepository.findAll();
    }
}
