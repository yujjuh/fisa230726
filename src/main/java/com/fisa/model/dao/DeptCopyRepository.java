/* CrudRepository
 * 
 * - 별도의 메소드 재정의 없이 crud 기능의 메소드 호출만으로 실행 가능
 * - 개발시 주의 사항 (rule)
 * - CrudRepository<T, ID>
 *  T : table과 매팽된 Entity 타입이어야 함
 *  ID : 해당 Entity의 pk 즉 id 변수 타입
 */

package com.fisa.model.dao;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.fisa.model.domain.entity.DeptCopy;

@Repository
public interface DeptCopyRepository extends CrudRepository<DeptCopy, Integer>{

}
