package com.caterpillar.shamil.LibraryPet.comparators;

import com.caterpillar.shamil.LibraryPet.entity.Books;

import java.util.Comparator;
import java.util.Locale;

import static com.caterpillar.shamil.LibraryPet.comparators.CompareCharArray.compareCharArray;

/**
 * <h3>SpringPet</h3>
 *
 * @author Shamil Shyhiev
 * <a href="https://t.me/bionic2113">telegram<a>
 * @date 06.05.2023
 */
public class BooksComparator implements Comparator<Books> {



    @Override
    public int compare(Books o1, Books o2) {
        int result = compareCharArray(o1.getTitle().toLowerCase(Locale.ROOT).toCharArray(),
                                      o2.getTitle().toLowerCase(Locale.ROOT).toCharArray());
        if (result != 0){
            return result;
        }
        return compareCharArray(o1.getAuthor().toLowerCase(Locale.ROOT).toCharArray(),
                                o2.getAuthor().toLowerCase(Locale.ROOT).toCharArray());
    }
}
