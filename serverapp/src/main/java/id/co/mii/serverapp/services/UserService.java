package id.co.mii.serverapp.services;

import id.co.mii.serverapp.models.User;
import id.co.mii.serverapp.services.base.BaseService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserService extends BaseService<User, Integer> {
}
