package Demo.Mapper;

import java.util.List;
import Demo.Entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

@Repository
public class UserDao{

	@Autowired
	UserMapper userMapper;

	public List<User> selectall(){
		return userMapper.selectall();
	}
}
