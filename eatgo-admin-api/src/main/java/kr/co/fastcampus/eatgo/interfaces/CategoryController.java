package kr.co.fastcampus.eatgo.interfaces;

import kr.co.fastcampus.eatgo.application.CategoryService;
import kr.co.fastcampus.eatgo.domain.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.imageio.spi.ServiceRegistry;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

@RestController
public class CategoryController {

    // @Autowired 안해줘서 NullPointerException 뜸
    @Autowired
    private CategoryService categoryService;

    @GetMapping("/categories")
    public List<Category> list() {
        List<Category> categories = categoryService.getCategories();

        return categories;
    }

    @PostMapping("/categories")
    public ResponseEntity<?> create(
            @RequestBody Category resource
    ) throws URISyntaxException {
        Category category = categoryService.addCategory(resource.getName());

        String url = "/categories/" + category.getId();

        // body() 부분이 터미널 창에서 보여지는 결과
        return ResponseEntity.created(new URI(url)).body("{}");
    }
}
