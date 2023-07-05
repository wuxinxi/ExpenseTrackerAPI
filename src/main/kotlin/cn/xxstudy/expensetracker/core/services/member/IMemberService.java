package cn.xxstudy.expensetracker.core.services.member;

import cn.xxstudy.expensetracker.data.table.Member;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.List;

/**
 * @date: 2023/7/5 09:17
 * @author: TangRen
 * @remark:
 */
public interface IMemberService extends IService<Member> {
    Member addMember(HttpServletRequest request, MultipartFile icon, String name) throws IOException;

    boolean deleteMember(HttpServletRequest request, Long id);

    Member updateMember(HttpServletRequest request, MultipartFile icon, String name, Long id);

    List<Member> queryMemberList(HttpServletRequest request);
}
