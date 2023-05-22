package com.caterpillar.shamil.LibraryPet.comparators;

import com.caterpillar.shamil.LibraryPet.entity.People;

import java.util.Comparator;

import static com.caterpillar.shamil.LibraryPet.comparators.CompareCharArray.compareCharArray;

/**
 * <h3>SpringPet</h3>
 *
 * @author Shamil Shyhiev
 * <a href="https://t.me/bionic2113">telegram<a>
 * @date 06.05.2023
 */
public class PeopleComparator implements Comparator<People> {
    @Override
    public int compare(People o1, People o2) {
        int result = compareCharArray(o1.getLastname().toCharArray(),o2.getLastname().toCharArray());
        if (result != 0){
            return result;
        }
        result = compareCharArray(o1.getFirstname().toCharArray(),o2.getFirstname().toCharArray());
        if (result != 0){
            return result;
        }
        return compareCharArray(o1.getPatronymic().toCharArray(),o2.getPatronymic().toCharArray());
    }
}
