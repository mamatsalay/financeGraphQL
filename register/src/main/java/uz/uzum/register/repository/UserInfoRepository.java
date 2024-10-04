package uz.uzum.register.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.uzum.register.model.UserInfo;

public interface UserInfoRepository extends JpaRepository<UserInfo,Long> {
}
