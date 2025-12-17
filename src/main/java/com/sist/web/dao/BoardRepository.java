package com.sist.web.dao;

import java.util.List;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.sist.web.entity.BoardEntity;
import com.sist.web.vo.BoardUpdateVO;
import com.sist.web.vo.BoardVO;

public interface BoardRepository extends JpaRepository<BoardEntity, Integer> {
	// 상세보기 : 메소드
	public BoardEntity findByNo(int no);
	// SELECT * FROM board WHERE no = 1
	/*
	 *   findByNameContains %name%
	 *   findByNameStartsWith name%
	 *   findByNameEndsWith %name
	 */
	// update, insert, delete, count
	@Query(value = "SELECT no, subject, name, TO_CHAR(regdate, 'yyyy-mm-dd') AS dbday, hit, num "
			+ "FROM (SELECT no, subject, name, regdate, hit, ROWNUM AS num "
			+ "FROM (SELECT no, subject, name, regdate, hit "
			+ "FROM board_1 ORDER BY no DESC)) "
			+ "WHERE num BETWEEN :start AND :end", nativeQuery = true)
	// nativeQuery => SQL문장을 그대로 적용 JPQL로 변환 => 오류발생
	// 받는 변수?
	public List<BoardVO> boardListData(@Param("start") Integer start, @Param("end") Integer end);
	
	@Query(value = "SELECT NVL(MAX(no)+1, 1) FROM board_1", nativeQuery = true)
	public int getMax();
	
	// 수정 데이터 읽기
	@Query(value = "SELECT no, subject, name, TO_CHAR(content) AS content "
			+ "FROM board_1 WHERE no = :no", nativeQuery = true)
	public BoardUpdateVO boardUpdateData(@Param("no") int no);
	// 수정 => save()
	// 삭제 => delete()
	// 총갯수 => count()
	// 전체 데이터 => findAll()
	// WHERE => findBy(컬럼명)(오라클명령문)
	// 상세보기 => findByNo() => findByFno() =
	//          ------ WHERE no=  WHERE fno= 메소드 규칙
}
