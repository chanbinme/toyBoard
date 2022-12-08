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

    String zipcode; // 우편 번호

    public void changeCity(String city) {
        this.city = city;
    }

    public void changeStreet(String street) {
        this.street = street;
    }

    public void changeZipcode(String zipcode) {
        this.zipcode = zipcode;
    }
}
