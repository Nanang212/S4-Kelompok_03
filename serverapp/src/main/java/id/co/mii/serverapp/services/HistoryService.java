package id.co.mii.serverapp.services;

import id.co.mii.serverapp.models.History;
import id.co.mii.serverapp.services.base.BaseService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class HistoryService extends BaseService<History, Integer> {
}
