package com.programmer.Blog1.Blogger.Repository;

import com.programmer.Blog1.Blogger.Model.BlogEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BlogRepository extends JpaRepository<BlogEntity,Integer> {

}
