package com.zmdev.goldenbag.utils;

import com.zmdev.goldenbag.domain.*;
import com.zmdev.goldenbag.service.AssessmentProjectScoreService;
import com.zmdev.goldenbag.service.AssessmentTemplateService;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ExportAssessment {
    @Autowired
    private AssessmentTemplateService assessmentTemplateService;
    @Autowired
    private AssessmentProjectScoreService assessmentProjectScoreService;

    public Workbook createTemplate(Assessment assessment, AssessmentTemplate template) {

        Workbook workBook = new HSSFWorkbook();
        Sheet sheet = workBook.createSheet();
        sheet.setDefaultRowHeight((short) 600);

        // 居中style
        CellStyle centerStyle = workBook.createCellStyle();
        centerStyle.setAlignment(HorizontalAlignment.CENTER);
        centerStyle.setVerticalAlignment(VerticalAlignment.CENTER);
        centerStyle.setWrapText(true);// 换行

        // 默认style
        CellStyle defaultStyle = workBook.createCellStyle();
        defaultStyle.cloneStyleFrom(centerStyle);
        Font defaultFont = workBook.createFont();
        defaultFont.setFontHeightInPoints((short) 12);
        defaultFont.setFontName("宋体");
        defaultStyle.setFont(defaultFont);
        for (int i = 0; i < 10; i++) {
            sheet.setDefaultColumnStyle(i, defaultStyle);
        }
        // 创建前三行
        CellStyle headerCellStyle = workBook.createCellStyle();
        headerCellStyle.cloneStyleFrom(centerStyle);
        headerCellStyle.setAlignment(HorizontalAlignment.CENTER);
        Font headerFont = workBook.createFont();
        headerFont.setFontHeightInPoints((short) 16);
        headerFont.setFontName("宋体");

        headerCellStyle.setFont(headerFont);
        Row header1 = sheet.createRow(0);
        header1.createCell(0).setCellStyle(headerCellStyle);
        header1.getCell(0).setCellValue("上海容大数字技术有限公司");


        Row header2 = sheet.createRow(1);
        header2.createCell(0).setCellStyle(headerCellStyle);
        header2.getCell(0).setCellValue("员工季度绩效考核表");

        Row header3 = sheet.createRow(2);
        header3.createCell(0).setCellStyle(headerCellStyle);
        header3.getCell(0).setCellValue("注：本表适用于不带团队的员工填写");

        sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 9));
        sheet.addMergedRegion(new CellRangeAddress(1, 1, 0, 9));
        sheet.addMergedRegion(new CellRangeAddress(2, 2, 0, 9));

        // 姓名xx行
        Row row3 = sheet.createRow(3);
        row3.createCell(0).setCellValue("姓名");
        row3.createCell(1).setCellValue(assessment.getUser().getName());
        row3.createCell(2).setCellValue("部门");
        User user = assessment.getUser();
        Department department = user.getDepartment();
        if (user.getTopDepartment(department) != null) {
            row3.createCell(3).setCellValue(user.getTopDepartment(department).getName());
        }
        row3.createCell(4).setCellValue("项目组");
        if (department != null) {
            row3.createCell(5).setCellValue(assessment.getUser().getDepartment().getName());
        }
        row3.createCell(6).setCellValue("岗位");
        // TODO 这里暂时这样写
        row3.createCell(7).setCellValue(AssessmentTemplate.Type.STAFF_TEMPLATE == assessment.getUser().getType() ? "员工" : "经理");
        sheet.addMergedRegion(new CellRangeAddress(row3.getRowNum(), row3.getRowNum(), 7, 9));

        // 直接经理xx行
        Row row4 = sheet.createRow(4);
        row4.createCell(0).setCellValue("直接经理");
        User directManager = assessment.getUser().getDirectManager();
        if (directManager != null) {
            row4.createCell(1).setCellValue(directManager.getName());
        }
        row4.createCell(2).setCellValue("间接经理");
        User indirectManager = assessment.getUser().getIndirectManager();
        if (indirectManager != null) {
            row4.createCell(3).setCellValue(indirectManager.getName());
        }
        row4.createCell(5).setCellValue("考核时间");
        row4.createCell(6).setCellValue(assessment.getQuarter().getName());
        sheet.addMergedRegion(new CellRangeAddress(row4.getRowNum(), row4.getRowNum(), 3, 4));
        sheet.addMergedRegion(new CellRangeAddress(row4.getRowNum(), row4.getRowNum(), 6, 9));

        // 考核项目xx行
        Row row5 = sheet.createRow(5);
        row5.createCell(0).setCellValue("考核项目");
        row5.createCell(1).setCellValue("评分参考标准");
        row5.createCell(6).setCellValue("分值");
        row5.createCell(7).setCellValue("自评得分");
        row5.createCell(8).setCellValue("直接经理评分");
        row5.createCell(9).setCellValue("备注");
        sheet.addMergedRegion(new CellRangeAddress(row5.getRowNum(), row5.getRowNum(), 1, 5));

        List<AssessmentProject> projects = assessment.getAssessmentTemplate().getAssessmentProjects();
        int projectItemSize = 0;
        int currentRow = 5;
        for (int i = 0; i < projects.size(); i++) {
            AssessmentProject project = projects.get(i);
            List<AssessmentProjectItem> items = project.getItems();
            AssessmentProjectScore assessmentProjectScore = assessmentProjectScoreService.findByAssessmentProjectAndAssessment(project, assessment);
            // 在 5+i 行插入一个项目
            Row projectRow = sheet.createRow(currentRow + 1);
            projectRow.createCell(0).setCellValue(project.getTitle());
            if (items.size() > 1) {
                sheet.addMergedRegion(new CellRangeAddress(projectRow.getRowNum(), projectRow.getRowNum() + items.size() - 1, 0, 0));
                sheet.addMergedRegion(new CellRangeAddress(projectRow.getRowNum(), projectRow.getRowNum() + items.size() - 1, 7, 7));
                sheet.addMergedRegion(new CellRangeAddress(projectRow.getRowNum(), projectRow.getRowNum() + items.size() - 1, 8, 8));
                sheet.addMergedRegion(new CellRangeAddress(projectRow.getRowNum(), projectRow.getRowNum() + items.size() - 1, 9, 9));
            }
            currentRow += (i + items.size());
            for (int j = 0; j < items.size(); j++) {
                AssessmentProjectItem item = items.get(j);
                Row itemRow = null;
                if (j == 0) {
                    itemRow = projectRow;
                } else {
                    itemRow = sheet.createRow(projectRow.getRowNum() + j);
                }
                itemRow.createCell(1).setCellValue(item.getTitle());
                itemRow.createCell(6).setCellValue(item.getScore());
                sheet.addMergedRegion(new CellRangeAddress(itemRow.getRowNum(), itemRow.getRowNum(), 1, 5));
                projectItemSize++;
            }
            projectRow.createCell(7).setCellValue(assessmentProjectScore.getSelfScore());
            projectRow.createCell(8).setCellValue(assessmentProjectScore.getManagerScore());
            projectRow.createCell(9).setCellValue(assessmentProjectScore.getRemarks());
        }
        // 合计行
        Row totalRow = sheet.createRow(currentRow);
        totalRow.createCell(0).setCellValue("合计");
        totalRow.createCell(7).setCellValue(assessment.getTotalSelfScore());
        totalRow.createCell(8).setCellValue(assessment.getTotalManagerScore());
        sheet.addMergedRegion(new CellRangeAddress(totalRow.getRowNum(), totalRow.getRowNum(), 0, 5));

        // 工作总结
        Row inputStartRow = sheet.createRow(totalRow.getRowNum() + 1);
        inputStartRow.createCell(0).setCellValue("工作总结、改进和工作目标计划");
        sheet.addMergedRegion(new CellRangeAddress(inputStartRow.getRowNum(), inputStartRow.getRowNum(), 0, 9));

        List<AssessmentInputContent> inputContents = assessment.getAssessmentInputContents();
        /*for (int i = 0; i < inputContents.size(); i++) {
            assessmentInputContents.get(i).get
            AssessmentInput input = inputs.get(i);
            Row row = sheet.createRow(inputStartRow.getRowNum() + 1 + (i * 2));
            row.createCell(0).setCellValue(input.getTitle());
            sheet.addMergedRegion(new CellRangeAddress(row.getRowNum(), row.getRowNum(), 0, 9));
            sheet.addMergedRegion(new CellRangeAddress(row.getRowNum() + 1, row.getRowNum() + 1, 0, 9));
        }*/

        // 直接经理评价和改进建议
        Row directManagerEvaluateRow = sheet.createRow(inputStartRow.getRowNum() + inputContents.size() * 2 + 1);
        directManagerEvaluateRow.createCell(0).setCellValue("直接经理评价和改进建议：");
        sheet.addMergedRegion(new CellRangeAddress(directManagerEvaluateRow.getRowNum(), directManagerEvaluateRow.getRowNum(), 0, 9));
        sheet.addMergedRegion(new CellRangeAddress(directManagerEvaluateRow.getRowNum() + 1, directManagerEvaluateRow.getRowNum() + 1, 0, 9));
        // 直接经理评价和改进建议 Value
        Row directValue = sheet.createRow(directManagerEvaluateRow.getRowNum() + 1);
        directValue.createCell(0).setCellValue(assessment.getDirectManagerEvaluation());
        sheet.addMergedRegion(new CellRangeAddress(directValue.getRowNum() + 1, directValue.getRowNum() + 1, 0, 9));
        System.out.println(directValue.getRowNum());
        /*// 间接经理审核意见
        Row indirectManagerAuditCommentRow = sheet.createRow(directValue.getRowNum() + 1);
        indirectManagerAuditCommentRow.createCell(0).setCellValue("间接经理审核意见");
        sheet.addMergedRegion(new CellRangeAddress(indirectManagerAuditCommentRow.getRowNum(), indirectManagerAuditCommentRow.getRowNum(), 0, 2));
        sheet.addMergedRegion(new CellRangeAddress(indirectManagerAuditCommentRow.getRowNum(), indirectManagerAuditCommentRow.getRowNum(), 3, 9));
        // 间接经理审核意见 Value
        Row indirectValue = sheet.createRow(indirectManagerAuditCommentRow.getRowNum() + 1);
        indirectValue.createCell(0).setCellValue(assessment.getIndirectManagerAuditComments());
        sheet.addMergedRegion(new CellRangeAddress(indirectValue.getRowNum() + 1, indirectValue.getRowNum() + 1, 0, 9));
*/
       /* //签名
        Row signRow = sheet.createRow(indirectValue.getRowNum() + 2);
        signRow.createCell(0).setCellValue("返回个人确认签名");
        sheet.addMergedRegion(new CellRangeAddress(signRow.getRowNum(), signRow.getRowNum(), 0, 2));
        sheet.addMergedRegion(new CellRangeAddress(signRow.getRowNum(), signRow.getRowNum(), 3, 9));
*/
        return workBook;
    }
}
