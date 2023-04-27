package com.programmer.Blog1.Blogger.Repository;

import com.programmer.Blog1.Blogger.Model.BlogEntity;
import com.programmer.Blog1.Blogger.ResponseDto.BlogResponseDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface BlogRepository extends JpaRepository<BlogEntity,Long> {

    @Query(value = "select * from blogs b ORDER BY b.pub_date DESC limit 6",nativeQuery = true)
    List<BlogEntity> findTopSixBlogs();

    BlogEntity findByTitle(String title);
}
