package com.wanted.associationmapping.section01.manytoone;

import org.jspecify.annotations.Nullable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ManyToOneService {

    @Autowired
    private ManyToOneRepository repository;

    public Menu findMenu(int menuCode) {
        return repository.find(menuCode);
    }

    public String findCategoryName(int menuCode) {
        return repository.findCategoryName(menuCode);
    }


    @Transactional
    public void registMenu(MenuDTO menuInfo) {

        Menu menu = new Menu(
                menuInfo.getMenuCode(),
                menuInfo.getMenuName(),
                menuInfo.getMenuPrice(),
                new Category(
                        menuInfo.getCategoryDTO().getCategoryCode(),
                        menuInfo.getCategoryDTO().getCategoryName(),
                        menuInfo.getCategoryDTO().getRefCategoryCode()
                ),
                menuInfo.getOrderableStatus()
        );

        repository.regist(menu);
    }
}
