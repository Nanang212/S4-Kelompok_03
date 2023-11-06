package id.co.mii.serverapp.services;

import id.co.mii.serverapp.models.Status;
import id.co.mii.serverapp.services.base.BaseService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class StatusService extends BaseService<Status, Integer> {
}
