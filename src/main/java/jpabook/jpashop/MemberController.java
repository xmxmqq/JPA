package jpabook.jpashop;

import jpabook.jpashop.domain.Address;
import jpabook.jpashop.domain.Member;
import jpabook.jpashop.service.MemberService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;
import java.util.List;

@Controller
public class MemberController {

    @GetMapping
    public String createForm(Model model) {
        // 이 데이터를 가지고 가는 이우는 Validation을 위해서임
        // 화면에서 MemberForm 객체에 대한 접근이 가능해진다.
        model.addAttribute("memberForm", new MemberForm());

        return "members/createMemberForm";
    }

    @PostMapping("/members/new")
    public String create(@Valid MemberForm memberForm, BindingResult bindingResult) {

        // BindingResult에는 hasErrors() 말고도 많은 유용한 메서드들이 있다.
        // 이렇게 BindingResult를 이용하여 에러를 검출한 뒤에 Thymeleaf 화면에도 적용시켜줄 수 있다.
        if (bindingResult.hasErrors()) {
            return "member/createMemberForm";
        }

        Address address = new Address(memberForm.getCity(), memberForm.getStreet(), memberForm.getZipcode());

        Member member = new Member();
        member.setName(memberForm.getName());
        member.setAddress(address);

        memberService.join(member);
        return "redirect:/";
    }

    @GetMapping("/members")
    public String list(Model model) {

        // MemberForm을 만들어야 하는 이유는 Entity 객체를 순수하기 유지하기 위해서이다.
        // 실무에서는 DTO를 이용해서 정확히 필요한 부분만 반환하는 것이 좋다.
        List<Member> memberList = MemberService.findMembers();
        model.addAttribute("memberList", memberList);

        return "members/memberList";
    }
}
