package com.community.app.domain;

import lombok.*;

import javax.persistence.*;

@Getter @Setter @EqualsAndHashCode(of = "idx")
@Builder @AllArgsConstructor @NoArgsConstructor
@Entity
public class Member {

    @Id @GeneratedValue
    private Integer idx;
    private String email;
    private String passwd;
    private String nick;
    @Enumerated(EnumType.STRING)
    private RoleStatus role;
    // enum의 순서가 바뀌면 꼬일수있기때문에 String타입으로 사용하는걸 권장
    private String provider;
    private String providerId;
    private String regdate;

}
