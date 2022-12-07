package hobin.toyBoard.member.entity;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Address {
    String city;    // 도시
    String street;  // 구

    @Column(name = "address_detail")
    String detail;  // 상세 주소
    String zipcode; // 우편 번호
}
