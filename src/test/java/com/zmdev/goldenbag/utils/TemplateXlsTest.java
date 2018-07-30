package com.zmdev.goldenbag.utils;

import com.zmdev.goldenbag.domain.AssessmentTemplate;
import com.zmdev.goldenbag.service.AssessmentTemplateService;
import org.apache.poi.ss.usermodel.Workbook;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.FileOutputStream;
import java.io.IOException;


@RunWith(SpringRunner.class)
@SpringBootTest
public class TemplateXlsTest {

    @Autowired
    private TemplateXls templateXls;

    @Autowired
    private AssessmentTemplateService assessmentTemplateService;



    @Test
    public void testCreateTemplate() throws IOException {
        AssessmentTemplate template = assessmentTemplateService.findById(1L).orElse(null);
        Workbook workbook = templateXls.createTemplate(template);
        FileOutputStream fileOut = new FileOutputStream("workbook.xls");
        workbook.write(fileOut);
        fileOut.close();
    }
}
