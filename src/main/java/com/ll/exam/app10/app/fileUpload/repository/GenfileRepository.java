package com.ll.exam.app10.app.fileUpload.repository;

import com.ll.exam.app10.app.article.entity.Article;
import com.ll.exam.app10.app.fileUpload.entity.GenFile;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GenfileRepository extends JpaRepository<GenFile, Long> {
}
