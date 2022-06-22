package Demo.Mapper;

import java.util.HashMap;
import java.util.List;

import Demo.Entity.User;

public interface UserMapper {
	
	User queryuser(HashMap<String, Object> map);
	
	List<User> selectall();
	
    int deleteByPrimaryKey(long id);

    int insert(User record);

    int insertSelective(User record);

    User selectByPrimaryKey(long id);

    int updateByPrimaryKeySelective(User record);

    int updateByPrimaryKey(User record);

	User studentlogin(HashMap<String, Object> map);
}