package cn.xxstudy.expensetracker.core.controller;

import cn.xxstudy.expensetracker.annotation.TokenRequired;
import cn.xxstudy.expensetracker.constant.HttpCode;
import cn.xxstudy.expensetracker.core.services.transaction.ExpenseService;
import cn.xxstudy.expensetracker.core.services.transaction.IncomeService;
import cn.xxstudy.expensetracker.data.Response;
import cn.xxstudy.expensetracker.data.table.TransactionExpense;
import cn.xxstudy.expensetracker.data.table.TransactionIncome;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Optional;

/**
 * @date: 2023/7/3 21:08
 * @author: LovelyCoder
 * @remark:
 */
@RestController
public class TransactionController {

    private final ExpenseService expenseService;
    private final IncomeService incomeService;

    public TransactionController(ExpenseService expenseService, IncomeService incomeService) {
        this.expenseService = expenseService;
        this.incomeService = incomeService;
    }

    @PostMapping("/income")
    @TokenRequired
    public Response<TransactionIncome> income(HttpServletRequest request,
                                              @RequestBody TransactionIncome income) {
        TransactionIncome response = incomeService.recordOrUpdate(request, income);
        return Optional.ofNullable(response)
                .map(Response::success)
                .orElse(Response.failed(HttpCode.RECORD_ERROR));
    }

    @GetMapping("/query/income")
    @TokenRequired
    public Response<List<TransactionIncome>> queryIncomeList(HttpServletRequest request,
                                                             @RequestParam int page,
                                                             @RequestParam(required = false, defaultValue = "10") int size) {
        return Response.success(incomeService.queryList(request, page, size));
    }

    @GetMapping("/query/date/income")
    @TokenRequired
    Response<List<TransactionExpense>> queryIncomeListByDate(HttpServletRequest request,
                                                             @RequestParam String date) {
        return Response.success(expenseService.queryListByDate(request, date));
    }


    @PostMapping("/expense")
    @TokenRequired
    Response<TransactionExpense> expense(HttpServletRequest request,
                                         @RequestBody TransactionExpense income) {
        TransactionExpense response = expenseService.recordOrUpdate(request, income);
        return Optional.ofNullable(response)
                .map(Response::success)
                .orElse(Response.failed(HttpCode.RECORD_ERROR));
    }

    @GetMapping("/query/expense")
    @TokenRequired
    Response<List<TransactionExpense>> queryExpenseList(HttpServletRequest request,
                                                        @RequestParam int page,
                                                        @RequestParam(required = false, defaultValue = "10") int size) {
        return Response.success(expenseService.queryList(request, page, size));
    }

    @GetMapping("/query/date/expense")
    @TokenRequired
    Response<List<TransactionExpense>> queryExpenseListByDate(HttpServletRequest request,
                                                              @RequestParam String date) {
        return Response.success(expenseService.queryListByDate(request, date));
    }

}
