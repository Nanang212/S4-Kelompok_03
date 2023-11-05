package id.co.mii.clientapp.services;

import id.co.mii.clientapp.models.Region;
import id.co.mii.clientapp.models.dto.request.RegionRequest;
import id.co.mii.clientapp.models.dto.response.RegionResponse;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class RegionService {

        @Value("${server.base.url}/region")
        private String url;

        @Autowired
        private RestTemplate restTemplate;

        public List<Region> getAll() {
                return restTemplate
                .exchange(url, HttpMethod.GET, null, new ParameterizedTypeReference<List<Region>>() {})
                .getBody();
        }

        public Region getById(Integer id) {
                return restTemplate
                .exchange(url.concat("/" + id), HttpMethod.GET, null, Region.class)
                .getBody();
        }

        public RegionResponse create(RegionRequest regionRequest) {
                return restTemplate
                .exchange(url,HttpMethod.POST,new HttpEntity<RegionRequest>(regionRequest),RegionResponse.class)
                .getBody();
        }

        public Region update(Integer id, Region region) {
                HttpEntity<Region> request = new HttpEntity<Region>(region);
                return restTemplate
                .exchange(url.concat("/" + id), HttpMethod.PUT, request, Region.class)
                .getBody();
        }

        public Region delete(Integer id) {
                return restTemplate
                .exchange(url.concat("/" + id), HttpMethod.DELETE, null, Region.class)
                .getBody();
        }
}
