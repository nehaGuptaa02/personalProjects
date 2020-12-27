package com.springbootcamp;

import com.springbootcamp.models.User;
import com.springbootcamp.repos.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class SpringSecurityApplicationTests {

	@Autowired
    UserRepository userRepository;
	@Test
	void contextLoads() {
	}
	@Test
	 void findById()
	{
		//User user=new User();
		User user=userRepository.findById(1L).get();
		System.out.println(user.getRoles()+">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
		//System.out.println("");
//		List<Role> roleList=user.getRoles();
//		System.out.println(roleList.get(1)+"*************************************");
	}

}
