package cn.xxstudy.expensetracker.core.controller;

import cn.xxstudy.expensetracker.annotation.TokenRequired;
import cn.xxstudy.expensetracker.core.services.analysis.expense.ExpenseAnalysisService;
import cn.xxstudy.expensetracker.core.services.analysis.income.IncomeAnalysisService;
import cn.xxstudy.expensetracker.data.Response;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * @date: 2023/7/6 20:25
 * @author: LovelyCoder
 * @remark:
 */
@RestController
public class TrackerAnalysis {
    private final IncomeAnalysisService incomeAnalysisService;
    private final ExpenseAnalysisService expenseAnalysisService;

    public TrackerAnalysis(IncomeAnalysisService incomeAnalysisService, ExpenseAnalysisService expenseAnalysisService) {
        this.incomeAnalysisService = incomeAnalysisService;
        this.expenseAnalysisService = expenseAnalysisService;
    }

    @GetMapping("/getExpenseAnalysis")
    @TokenRequired
    public Response<Map<String, Object>> getExpenseAnalysis(HttpServletRequest request, String month) {
        Map<String, Object> map = expenseAnalysisService.analysis(request, month);
        return Response.success(map);
    }

    @GetMapping("/getIncomeAnalysis")
    @TokenRequired
    public Response<Map<String, Object>> getIncomeAnalysis(HttpServletRequest request, String month) {
        Map<String, Object> map = incomeAnalysisService.analysis(request, month);
        return Response.success(map);
    }

}
