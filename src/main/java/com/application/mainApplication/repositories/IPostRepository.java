package com.application.mainApplication.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.application.mainApplication.entities.Post;

@Repository
public interface IPostRepository extends JpaRepository<Post, Integer>{

    
}
