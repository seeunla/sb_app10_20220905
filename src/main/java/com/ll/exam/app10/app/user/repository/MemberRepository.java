package com.ll.exam.app10.app.user.repository;

import com.ll.exam.app10.app.user.entity.Member;
import com.ll.exam.app10.app.user.service.MemberServise;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.swing.text.html.Option;
import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {

    Optional<Member> findByUsername(String username);
}
