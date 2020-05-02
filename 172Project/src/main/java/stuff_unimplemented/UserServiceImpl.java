package stuff_unimplemented;
import com.example.service.UserService;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;

import com.example.demo.model.User;
import com.example.repository.UserRepository;


public class UserServiceImpl implements UserService{
	private final UserRepository userRepo;
    //private final User aUser;
    
    @Autowired
    public UserServiceImpl(UserRepository userRepo) {
        this.userRepo = userRepo;
    }

	@Override
	public Optional<User> findByUsername(String userName) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Optional<User> findByID(int userID) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public User saveUser(User user) {
		// TODO Auto-generated method stub
        userRepo.save(user);
        return user;
	}

	@Override
	public void login(String username, String password) {
		// TODO Auto-generated method stub
		
	}

}
