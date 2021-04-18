package kr.ac.sejong.api.service;

import kr.ac.sejong.api.domain.User;
import kr.ac.sejong.api.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {this.userRepository = userRepository;}

    public Boolean findUser(String loginId, String loginPw){
        System.out.println("findUser");
        User user = userRepository.findByLoginId(loginId);
        return user!= null&&loginPw.equals(user.getLoginPw());
    }

    public User getUser(String loginId) { return userRepository.findByLoginId(loginId); }


    //어떻게 전달할 수 있을까? 템플릿 엔진으로 보낼 수 있나?
    public String userJoin(String loginId, String loginPw, String userName){
        User newUser = new User();

        newUser.setUserName(userName);
        newUser.setLoginId(loginId);
        newUser.setLoginPw(loginPw);

        //이미 등록된 ID면 가입을 제한함
        if(newUser.getLoginId().isEmpty()) return "already exist";

        try{
            userRepository.save(newUser);
        }catch (Exception e){
            System.out.println(e);
            return "false";
        }

        return "success";
    }
}
