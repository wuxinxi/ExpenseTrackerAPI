package cn.xxstudy.expensetracker.core.controller;

import cn.xxstudy.expensetracker.annotation.TokenRequired;
import cn.xxstudy.expensetracker.constant.ErrorCode;
import cn.xxstudy.expensetracker.core.services.transaction.TransactionService;
import cn.xxstudy.expensetracker.data.Response;
import cn.xxstudy.expensetracker.data.model.TransactionSummary;
import cn.xxstudy.expensetracker.data.table.Transaction;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

/**
 * @date: 2023/7/16 9:08
 * @author: LovelyCoder
 * @remark:
 */
@RestController
public class TransactionCompatController {
    private final TransactionService service;

    public TransactionCompatController(TransactionService service) {
        this.service = service;
    }

    @GetMapping("/query/transactions")
    Response<TransactionSummary> queryListByMonthYear(HttpServletRequest request, String monthYear) {
        TransactionSummary transactionSummary = service.queryListByMonthYear(request, monthYear);
        return Response.success(transactionSummary);
    }

    @PostMapping("/transaction")
    @TokenRequired
    public Response<Transaction> transaction(HttpServletRequest request,
                                             @RequestBody Transaction transaction) {
        Transaction response = service.recordOrUpdate(request, transaction);
        return Optional.ofNullable(response)
                .map(Response::success)
                .orElse(Response.failed(ErrorCode.RECORD_ERROR));
    }
}
