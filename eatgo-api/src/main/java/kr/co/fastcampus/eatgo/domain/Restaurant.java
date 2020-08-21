package kr.co.fastcampus.eatgo.domain;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Transient;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter // +) @Setter -> 모든 필드에 접근자와 설정자가 자동으로 생성.
@Builder // 모델 객체를 생성할 때 Builder를 자동으로 추가해 줌. builder 패턴을 쉽게 적용할 수 있음.
@NoArgsConstructor // 파라미터가 없는 기본 생성자를 생성
@AllArgsConstructor // 모든 필드 값을 파라미터로 받는 생성자를 만들어 줌.
public class Restaurant {

    @Id
    @GeneratedValue
    private Long id;

    private String name;

    private String address;
//    private Long id;

    @Transient
    private List<MenuItem> menuItems = new ArrayList<MenuItem>();

//    // 기본 생성자 생성해주면서 final 제거
//    public Restaurant() {
//    }

    public Restaurant(String name, String address) {
        this.name = name;
        this.address = address;
    }

    public Restaurant(Long id, String name, String address) {
        this.id = id;
        this.name = name;
        this.address = address;
    }

    // id 자동으로 주기 위해서 생성..?
    public void setId(long id) {
        this.id = id;
    }

//    public Long getId() {
//        return id;
//    }
//
//    public String getName() {
//        return name;
//    }
//
//    public String getAddress() {
//        return address;
//    }

    public String getInfo() {
        return name + " in " + address;
    }

//    public List<MenuItem> getMenuItems() {
//        return menuItems;
//    }

    public void addMenuItem(MenuItem menuItem) {
        menuItems.add(menuItem);
    }

    public void setMenuItems(List<MenuItem> menuItems) {
        for (MenuItem menuItem : menuItems) {
            addMenuItem(menuItem);
        }
    }

    public void updateInformation(String name, String address) {
        this.name = name;
        this.address = address;
    }
}
