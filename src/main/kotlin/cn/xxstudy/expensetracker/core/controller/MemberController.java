package cn.xxstudy.expensetracker.core.controller;

import cn.xxstudy.expensetracker.annotation.TokenRequired;
import cn.xxstudy.expensetracker.constant.HttpCode;
import cn.xxstudy.expensetracker.core.services.member.MemberService;
import cn.xxstudy.expensetracker.data.Response;
import cn.xxstudy.expensetracker.data.table.Member;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

/**
 * @date: 2023/7/4 14:47
 * @author: TangRen
 * @remark:
 */
@RestController
public class MemberController {
    private final MemberService service;

    public MemberController(MemberService service) {
        this.service = service;
    }

    @PostMapping("/member")
    @TokenRequired
    public Response<Member> addMember(HttpServletRequest request, @RequestParam(required = false) @Valid MultipartFile icon, @RequestParam String name) {
        Member member = service.addMember(request, icon, name);
        return Response.success(member);
    }

    @DeleteMapping("/member")
    @TokenRequired
    public Response deleteMember(HttpServletRequest request, @RequestParam @Valid Long id) {
        boolean delete = service.deleteMember(request, id);
        return delete ? Response.success() : Response.failed(HttpCode.ID_ERROR);
    }

    @PatchMapping("/member")
    @TokenRequired
    public Response<Member> updateMember(HttpServletRequest request, @RequestParam(required = false) @Valid MultipartFile icon, @RequestParam String name, Long id) {
        Member response = service.updateMember(request, icon, name, id);
        return Optional.ofNullable(response)
                .map(Response::success)
                .orElse(Response.failed(HttpCode.ID_ERROR));
    }

    @GetMapping("/member")
    @TokenRequired
    public Response<List<Member>> queryMember(HttpServletRequest request) {
        return Response.success(service.queryMemberList(request));
    }

}
