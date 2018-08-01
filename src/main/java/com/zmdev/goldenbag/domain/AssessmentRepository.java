package com.zmdev.goldenbag.domain;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Collection;
import java.util.List;
import java.util.Map;

public interface AssessmentRepository extends JpaRepository<Assessment, Long> {

    /**
     * 这个恶心的Sql是用来查询指定用户s的考核记录的
     */
    String sql = "select a.id as id, a.status as status, a.createdAt as created_at, a.timeCoefficient as time_coefficient, a.totalSelfScore as total_self_score," +
            "a.user.name as uname, a.user.id as uid, a.user.gender as gender, a.user.entryDate as entry_date, a.quarter as quarter";

    @Query("select count(a.id) from Assessment a where a.user=:user and a.quarter = :quarter")
    int isSubmitedWithCurrentQuarter(@Param("user") User user, @Param("quarter") Quarter quarter);

    List<Assessment> findByUserIn(Collection<User> users);

    List<Assessment> findByUser(User user);

    List<Assessment> findByQuarter(Quarter quarter);

    @Query("select a from Assessment as a where a.user in :users and a.status='SUBMITTED'")
    List<Assessment> queryAllWithCurrentQuarterWaitAudit(Collection<User> users, Pageable pageable);

    @Query(sql + " from Assessment as a where a.user in :users and a.status=:status")
    Page<Map<String, Object>> selectByUserInAndStatusIs(@Param("users") Collection<User> users, @Param("status") Assessment.Status status, Pageable pageable);

    @Query("select a from Assessment a where a.id in :Ids")
    List<Assessment> findByIds(Long[] Ids);

}
