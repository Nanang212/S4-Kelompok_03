package id.co.mii.clientapp.services;

import id.co.mii.clientapp.models.TrainingRegister;
import id.co.mii.clientapp.models.dto.request.TrainingRegisterRequest;
import id.co.mii.clientapp.models.dto.response.HistoryResponse;
import id.co.mii.clientapp.models.dto.response.TrainingRegisterResponse;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Map;

@Service
public class TrainingRegisterService {
  @Value("${server.base.url}/trainings/register")
  private String url;
  @Autowired
  private RestTemplate restTemplate;

  public List<TrainingRegister> getAll() {
    return restTemplate
        .exchange(url, HttpMethod.GET, null, new ParameterizedTypeReference<List<TrainingRegister>>() {
        })
        .getBody();
  }

  public TrainingRegister getById(Integer id) {
    return restTemplate
        .exchange(url.concat("/" + id), HttpMethod.GET, null, TrainingRegister.class)
        .getBody();
  }

  public byte[] getAttachment(Integer id) {
    return restTemplate
        .exchange(url.concat("/attachment/" + id), HttpMethod.GET, null, new ParameterizedTypeReference<byte[]>() {
        })
        .getBody();
  }

  public TrainingRegister createCancellation(TrainingRegisterRequest trainingRegisterRequest) {
    return restTemplate
        .exchange(url.concat("/cancel"), HttpMethod.POST, new HttpEntity<>(trainingRegisterRequest),
            new ParameterizedTypeReference<TrainingRegister>() {
            })
        .getBody();
  }

  public List<TrainingRegisterResponse> getAllCancellation() {
    return restTemplate
        .exchange(url.concat("/cancel"), HttpMethod.GET, null,
            new ParameterizedTypeReference<List<TrainingRegisterResponse>>() {
            })
        .getBody();
  }

  public List<Map<String, Object>> getCancellationByIdGroupByTraining(Integer id) {
    return restTemplate
        .exchange(url.concat("/cancel/" + id), HttpMethod.GET, null,
            new ParameterizedTypeReference<List<Map<String, Object>>>() {
            })
        .getBody();
  }

  @SneakyThrows
  public TrainingRegister create(TrainingRegisterRequest trainingRegisterRequest, MultipartFile file) {
    HttpHeaders headers = new HttpHeaders();
    headers.setContentType(MediaType.MULTIPART_FORM_DATA);

    MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
    body.add("trainingId", trainingRegisterRequest.getTrainingId());
    body.add("traineeId", trainingRegisterRequest.getTraineeId());
    body.add("statusId", trainingRegisterRequest.getStatusId());
    body.add("attachment", new FileSystemResource(convertMultiPartToFile(file)));
    return restTemplate
        .exchange(url, HttpMethod.POST, new HttpEntity<>(body, headers), TrainingRegister.class)
        .getBody();
  }

  private Path convertMultiPartToFile(MultipartFile file) throws IOException {
    Path filePath = Files.createTempFile("temp-", file.getOriginalFilename());
    Files.write(filePath, file.getBytes());
    return filePath;
  }

  public TrainingRegister update(Integer id, TrainingRegisterRequest trainingRegisterRequest) {
    HttpEntity<TrainingRegisterRequest> request = new HttpEntity<>(trainingRegisterRequest);
    return restTemplate
        .exchange(url.concat("/" + id), HttpMethod.PUT, request, TrainingRegister.class)
        .getBody();
  }

  public TrainingRegister delete(Integer id) {
    return restTemplate
        .exchange(url.concat("/" + id), HttpMethod.DELETE, null, TrainingRegister.class)
        .getBody();
  }

  public List<TrainingRegisterResponse> getAllGroupByTraining() {
    return restTemplate
        .exchange(url.concat("/training"), HttpMethod.GET, null,
            new ParameterizedTypeReference<List<TrainingRegisterResponse>>() {
            })
        .getBody();
  }

  public List<Map<String, Object>> getByIdGroupByTraining(Integer id) {
    return restTemplate
        .exchange(url.concat("/training/" + id), HttpMethod.GET, null,
            new ParameterizedTypeReference<List<Map<String, Object>>>() {
            })
        .getBody();
  }

  public TrainingRegister getByTrainingId(Integer id) {
    return restTemplate
        .exchange(url.concat("/logged-in-emp/training/" + id), HttpMethod.GET, null, TrainingRegister.class)
        .getBody();
  }
}
