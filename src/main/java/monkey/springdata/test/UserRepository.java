package monkey.springdata.test;

import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * @Class Name: UserRepository
 * @Description: TODO
 * @Company bgy:  MK monkey
 * @create: 2018-01-06 22:27
 **/
public interface UserRepository extends CrudRepository<MkTest,String>{

    List<MkTest> findByIdAndName(Integer id, String name);
}
