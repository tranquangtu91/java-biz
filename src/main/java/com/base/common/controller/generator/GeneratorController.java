package com.base.common.controller.generator;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.base.common.dto.formfield.FormField;
import com.base.common.dto.generalresponse.GeneralResponse;
import com.base.common.dto.generalresponse.GeneralResponseErrorDetail;
import com.base.common.dto.generalresponse.GeneralResponseGetInstanceOptions;
import com.base.common.dto.generalresponse.ResponseCode;
import com.base.common.utils.convert.string.CasingUtils;
import com.base.common.utils.validator.ValidatorUtils;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/api/v1/common/generator")
public class GeneratorController {
    @Autowired
    ValidatorUtils validatorUtils;

    public static final String GENERATOR_CONSTRAINT_CODE = "/api/v1/common/generator/generate-.*";

    GeneratorController() {
        ValidatorUtils.registryConstraint(GENERATOR_CONSTRAINT_CODE, new ArrayList<FormField>() {
            {
                add(new FormField() {
                    {
                        code = "entity";
                        required = true;
                    }
                });
                add(new FormField() {
                    {
                        code = "package";
                        required = true;
                        pattern = "^[\\w]+.[\\w]+.[\\w]+.[\\w]+";
                    }
                });
            }
        });
    }

    @PostMapping("/generate-all")
    ResponseEntity<?> generateAll(@RequestBody Map<String, Object> body) throws IOException {
        GeneralResponse gr = validatorUtils.validate(GENERATOR_CONSTRAINT_CODE, body);
        if (gr.code != ResponseCode.SUCCESS) {
            return new ResponseEntity<Object>(gr, HttpStatus.OK);
        }

        String entity = (String) (body.get("entity"));
        String __package = (String) (body.get("package"));

        generateEntity(entity, __package);
        generateRepository(entity, __package);
        generateService(entity, __package);
        generateController(entity, __package);

        return new ResponseEntity<Object>("", HttpStatus.OK);
    }

    @PostMapping("/generate-entity")
    ResponseEntity<?> generateEntity(@RequestBody Map<String, Object> body) throws IOException {
        GeneralResponse gr = validatorUtils.validate(GENERATOR_CONSTRAINT_CODE, body);
        if (gr.code != ResponseCode.SUCCESS) {
            return new ResponseEntity<Object>(gr, HttpStatus.OK);
        }

        String entity = (String) (body.get("entity"));
        String __package = (String) (body.get("package"));
        String fileContent = generateEntity(entity, __package);

        return new ResponseEntity<Object>(fileContent, HttpStatus.OK);
    }

    @PostMapping("/generate-service")
    ResponseEntity<?> generateService(@RequestBody Map<String, Object> body) throws IOException {
        GeneralResponse gr = validatorUtils.validate(GENERATOR_CONSTRAINT_CODE, body);
        if (gr.code != ResponseCode.SUCCESS) {
            return new ResponseEntity<Object>(gr, HttpStatus.OK);
        }

        String entity = (String) (body.get("entity"));
        String __package = (String) (body.get("package"));
        String fileContent = generateRepository(entity, __package);
        fileContent = generateService(entity, __package);

        return new ResponseEntity<Object>(fileContent, HttpStatus.OK);
    }

    @PostMapping("/generate-controller")
    ResponseEntity<?> generateController(@RequestBody Map<String, Object> body) throws IOException {
        GeneralResponse gr = validatorUtils.validate(GENERATOR_CONSTRAINT_CODE, body);
        if (gr.code != ResponseCode.SUCCESS) {
            return new ResponseEntity<Object>(gr, HttpStatus.OK);
        }

        String entity = (String) (body.get("entity"));
        String __package = (String) (body.get("package"));
        String fileContent = generateController(entity, __package);

        return new ResponseEntity<Object>(fileContent, HttpStatus.OK);
    }

    private String generateEntity(String entity, String __package) throws IOException {
        String snakeCaseName = CasingUtils.toSnake(entity);
        String pascalCaseName = CasingUtils.toPascal(entity);
        String kebabCaseName = CasingUtils.toKebab(entity);

        File file = new File("src\\main\\java\\com\\ttq\\app\\common\\resources\\entity.template");
        String fileContent = new String(Files.readAllBytes(file.toPath()), StandardCharsets.UTF_8);
        fileContent = fileContent.replaceAll("@@snakeCaseName@@", snakeCaseName);
        fileContent = fileContent.replaceAll("@@pascalCaseName@@", pascalCaseName);
        fileContent = fileContent.replaceAll("@@kebabCaseName@@", kebabCaseName);
        fileContent = fileContent.replaceAll("@@package@@", __package);

        String[] __packages = __package.split("\\.");

        writeFile(fileContent,
                "src\\main\\java\\" + String.join("\\", __packages)
                        + "\\entity\\" + snakeCaseName + "\\"
                        + pascalCaseName + ".java");

        return fileContent;
    }

    private String generateRepository(String entity, String __package) throws IOException {
        String snakeCaseName = CasingUtils.toSnake(entity);
        String pascalCaseName = CasingUtils.toPascal(entity);
        String kebabCaseName = CasingUtils.toKebab(entity);

        File file = new File("src\\main\\java\\com\\ttq\\app\\common\\resources\\repository.template");
        String fileContent = new String(Files.readAllBytes(file.toPath()), StandardCharsets.UTF_8);
        fileContent = fileContent.replaceAll("@@snakeCaseName@@", snakeCaseName);
        fileContent = fileContent.replaceAll("@@pascalCaseName@@", pascalCaseName);
        fileContent = fileContent.replaceAll("@@kebabCaseName@@", kebabCaseName);
        fileContent = fileContent.replaceAll("@@package@@", __package);

        String[] __packages = __package.split("\\.");

        writeFile(fileContent,
                "src\\main\\java\\" + String.join("\\", __packages)
                        + "\\repository\\" + snakeCaseName + "\\"
                        + pascalCaseName + "Repository.java");

        return fileContent;
    }

    private String generateService(String entity, String __package) throws IOException {
        String snakeCaseName = CasingUtils.toSnake(entity);
        String pascalCaseName = CasingUtils.toPascal(entity);
        String kebabCaseName = CasingUtils.toKebab(entity);

        File file = new File("src\\main\\java\\com\\ttq\\app\\common\\resources\\service.template");
        String fileContent = new String(Files.readAllBytes(file.toPath()), StandardCharsets.UTF_8);
        fileContent = fileContent.replaceAll("@@snakeCaseName@@", snakeCaseName);
        fileContent = fileContent.replaceAll("@@pascalCaseName@@", pascalCaseName);
        fileContent = fileContent.replaceAll("@@kebabCaseName@@", kebabCaseName);
        fileContent = fileContent.replaceAll("@@package@@", __package);

        String[] __packages = __package.split("\\.");

        writeFile(fileContent,
                "src\\main\\java\\" + String.join("\\", __packages)
                        + "\\service\\" + snakeCaseName + "\\"
                        + pascalCaseName + "Service.java");

        return fileContent;
    }

    private String generateController(String entity, String __package) throws IOException {
        String snakeCaseName = CasingUtils.toSnake(entity);
        String pascalCaseName = CasingUtils.toPascal(entity);
        String kebabCaseName = CasingUtils.toKebab(entity);

        File file = new File("src\\main\\java\\com\\ttq\\app\\common\\resources\\controller.template");
        String fileContent = new String(Files.readAllBytes(file.toPath()), StandardCharsets.UTF_8);
        fileContent = fileContent.replaceAll("@@snakeCaseName@@", snakeCaseName);
        fileContent = fileContent.replaceAll("@@pascalCaseName@@", pascalCaseName);
        fileContent = fileContent.replaceAll("@@kebabCaseName@@", kebabCaseName);
        fileContent = fileContent.replaceAll("@@package@@", __package);

        String[] __packages = __package.split("\\.");

        writeFile(fileContent,
                "src\\main\\java\\" + String.join("\\", __packages)
                        + "\\controller\\" + snakeCaseName + "\\"
                        + pascalCaseName + "Controller.java");

        return fileContent;
    }

    private GeneralResponse writeFile(String str, String fileName) throws IOException {
        // List<String> activeProfiles = Arrays.asList(this.environment.getActiveProfiles());

        // if (!activeProfiles.contains("dev")) {
        //     String msg = String.format("env '%s' doesn't support...", activeProfiles);
        //     log.error(msg);
        //     return GeneralResponse.getInstance(GeneralResponseErrorDetail.INTERNAL_SERVER_ERROR,
        //             new GeneralResponseGetInstanceOptions() {
        //                 {
        //                     message = msg;
        //                 }
        //             });
        // }

        Path path = Paths.get(fileName);

        if (Files.exists(path)) {
            String msg = String.format("file '%s' is exists...", fileName);
            log.error(msg);
            return GeneralResponse.getInstance(GeneralResponseErrorDetail.INTERNAL_SERVER_ERROR,
                    new GeneralResponseGetInstanceOptions() {
                        {
                            message = msg;
                        }
                    });
        }

        Files.createDirectories(path.getParent());

        byte[] strToBytes = str.getBytes();
        Files.write(path, strToBytes);

        return new GeneralResponse();
    }
}
