package pl.allegrotech.productsshop.api;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/performance")
class PerformanceEndpoint {

    private Map<CustomKey, String> map = new HashMap<>();

    @GetMapping(value = "/heap1")
    void heap1() {
        for (int i = 0; i < 5000; i++) {
            map.put(new CustomKey("key"), "value");
        }
    }

    @GetMapping(value = "/heap2")
    void heap2() {
        byte[] array = new byte[256 * 1024];
    }

    class CustomKey {

        private String key;

        CustomKey(String key) {
            this.key = key;
        }
    }
}
