package cn.xxstudy.expensetracker.core.services.member;

import cn.xxstudy.expensetracker.constant.Constants;
import cn.xxstudy.expensetracker.constant.ErrorCode;
import cn.xxstudy.expensetracker.core.mapper.MemberMapper;
import cn.xxstudy.expensetracker.data.table.Member;
import cn.xxstudy.expensetracker.global.exception.AppException;
import cn.xxstudy.expensetracker.utils.ImageUploadHelper;
import cn.xxstudy.expensetracker.utils.TokenHelper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @date: 2023/7/5 09:18
 * @author: TangRen
 * @remark:
 */
@Service
public class MemberService extends ServiceImpl<MemberMapper, Member> implements IMemberService {
    private final MemberMapper memberMapper;

    private final TokenHelper tokenHelper;

    public MemberService(MemberMapper memberMapper, TokenHelper tokenHelper) {
        this.memberMapper = memberMapper;
        this.tokenHelper = tokenHelper;
    }


    @SneakyThrows
    @Override
    public Member addMember(HttpServletRequest request, MultipartFile icon, String name) {
        Long userId = tokenHelper.extractUserId(request.getHeader(Constants.AUTHORIZATION));
        LambdaQueryWrapper<Member> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Member::getUserId, userId).eq(Member::getMemberName, name);
        if (memberMapper.exists(queryWrapper)) {
            throw new AppException(ErrorCode.MEMBER_ERROR);
        }
        String imageUrl = ImageUploadHelper.upload(request, icon, userId.toString(), 100_000, 500);
        Member member = Member.builder()
                .userId(userId)
                .memberName(name)
                .memberAvatarUrl(imageUrl)
                .build();
        memberMapper.insert(member);
        return member;
    }

    @Override
    public boolean deleteMember(HttpServletRequest request, Long id) {
        return removeById(id);
    }

    @SneakyThrows
    @Override
    public Member updateMember(HttpServletRequest request, MultipartFile icon, String name, Long id) {
        Long userId = tokenHelper.extractUserId(request.getHeader(Constants.AUTHORIZATION));
        LambdaQueryWrapper<Member> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Member::getId, id);
        Member member = memberMapper.selectOne(queryWrapper);
        if (member == null) return null;
        String oldImageUrl = member.getMemberAvatarUrl();
        String newImageUrl = ImageUploadHelper.upload(request, icon, userId.toString(), 100_000, 300);
        if (newImageUrl != null) {
            member.setMemberAvatarUrl(newImageUrl);
        }
        member.setMemberName(name);
        boolean update = updateById(member);
        if (update) {
            ImageUploadHelper.deleteOldImage(userId.toString(), oldImageUrl, newImageUrl);
        }
        return update ? member : null;

    }

    @Override
    public List<Member> queryMemberList(HttpServletRequest request) {
        Long id = tokenHelper.extractUserId(request.getHeader(Constants.AUTHORIZATION));
        LambdaQueryWrapper<Member> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Member::getUserId, id);
        return memberMapper.selectList(queryWrapper);
    }
}
