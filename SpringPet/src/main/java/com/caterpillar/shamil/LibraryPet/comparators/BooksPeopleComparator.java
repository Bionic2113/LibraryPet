package com.caterpillar.shamil.LibraryPet.comparators;

import com.caterpillar.shamil.LibraryPet.entity.BooksPeople;

import java.util.Comparator;

/**
 * <h3>SpringPet</h3>
 *
 * @author Shamil Shyhiev
 * <a href="https://t.me/bionic2113">telegram<a>
 * @date 06.05.2023
 */
public class BooksPeopleComparator implements Comparator<BooksPeople> {

    private final BooksComparator booksComparator;
    private final PeopleComparator peopleComparator;

    public BooksPeopleComparator(){
        booksComparator = new BooksComparator();
        peopleComparator = new PeopleComparator();
    }

    @Override
    public int compare(BooksPeople o1, BooksPeople o2) {
        int result = booksComparator.compare(o1.getBooks(),o2.getBooks());
        if(result != 0){
            return result;
        }
        return peopleComparator.compare(o1.getPeoples(),o2.getPeoples());
    }
}
