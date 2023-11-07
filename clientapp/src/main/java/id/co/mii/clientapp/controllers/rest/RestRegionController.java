package id.co.mii.clientapp.controllers.rest;

import id.co.mii.clientapp.models.Region;
import id.co.mii.clientapp.models.dto.request.RegionRequest;
import id.co.mii.clientapp.models.dto.response.RegionResponse;
import id.co.mii.clientapp.services.RegionService;
import java.util.List;
import lombok.AllArgsConstructor;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping("/api/region")
public class RestRegionController {

    private RegionService regionService;

    @GetMapping
    public List<Region> getAll() {
        return regionService.getAll();
    }

    @GetMapping("/{id}")
    public Region getById(@PathVariable Integer id) {
        return regionService.getById(id);
    }

    @PostMapping
    public RegionResponse create(@RequestBody RegionRequest regionRequest) {
        return regionService.create(regionRequest);
    }

    @PutMapping("/{id}")
    public Region update(@PathVariable Integer id, @RequestBody Region region) {
        return regionService.update(id, region);
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable Integer id) {
        regionService.delete(id);
        return "redirect:/region";
    }
}
