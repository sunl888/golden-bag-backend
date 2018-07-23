package com.zmdev.goldenbag.web;

import com.zmdev.goldenbag.domain.result.Response;
import com.zmdev.goldenbag.domain.result.ResponseData;
import com.zmdev.goldenbag.service.DepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/departments", produces = "application/json;charset=UTF-8")
public class DepartmentController extends BaseController {

    private final DepartmentService departmentService;

    @Autowired
    public DepartmentController(DepartmentService departmentService) {
        this.departmentService = departmentService;
    }

    /**
     * 顯示部門列表
     *
     * @return Response
     */
    @GetMapping
    @ResponseBody
    public Response index() {
        //
        return new ResponseData(departmentService.findTopDepartment());
    }

    /*private List<Department> getItem(List<Department> categoryDTOS) {

        List<Department> resultCategoryList = new ArrayList<>();

        List<Department> parentCategoryList = new ArrayList<>();

        Set<Integer> itemCode = new HashSet<>();
        for (Department categoryDTO : categoryDTOS) {
            if (null != categoryDTO.getPid()) {
                itemCode.add(categoryDTO.getPid());
            }
        }

        if (!CollectionUtils.isEmpty(itemCode)) {
            List<CategoryDTO> categoryDTOS = categoryService.getCategoryBYProductId(itemCode);
            if (null != categoryDTOS) {
                parentCategoryList = ConvertUtil.convertList(categoryDTOS, CategoryDTO.class);
            }
        }
        for (CategoryDTO parentCategoryDTO : parentCategoryList) {
            List<CategoryDTO> subItemList = new ArrayList<>();
            for (CategoryDTO categoryDTO : categoryDTOS) {
                if (parentCategoryDTO.getId().equals(categoryDTO.getPid())) {
                    subItemList.add(categoryDTO);
                }
            }
            parentCategoryDTO.setSubCategory(subItemList);
            resultCategoryList.add(parentCategoryDTO);
        }
        if (!CollectionUtils.isEmpty(resultCategoryList)) {
            List<CategoryDTO> categoryDTOList = getItem(resultCategoryList);
            if (!CollectionUtils.isEmpty(categoryDTOList)) {
                resultCategoryList = categoryDTOList;
            }
        }
	return resultCategoryList;
    }*/

}
