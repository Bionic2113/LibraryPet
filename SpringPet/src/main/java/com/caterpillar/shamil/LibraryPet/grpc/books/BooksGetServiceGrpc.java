package com.caterpillar.shamil.LibraryPet.grpc.books;

import com.caterpillar.shamil.LibraryPet.repository.BooksRepository;
import com.caterpillar.shamil.proto.Books;
import com.caterpillar.shamil.proto.GetBookServiceGrpc;
import com.google.protobuf.Empty;
import io.grpc.stub.StreamObserver;
import org.lognet.springboot.grpc.GRpcService;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

/**
 * <h3>SpringPet</h3>
 *
 * @author Shamil Shyhiev
 * <a href="https://t.me/bionic2113">telegram<a>
 * @date 07.05.2023
 */
@GRpcService
public class BooksGetServiceGrpc extends GetBookServiceGrpc.GetBookServiceImplBase{

    private final BooksRepository booksRepository;

    public BooksGetServiceGrpc(BooksRepository booksRepository) {
        this.booksRepository = booksRepository;
    }

    @Override
    public void getAllBooks(Empty request, StreamObserver<Books.ListBooks> responseObserver) {
        var books = booksRepository.findByActiveTrue();
        List<Books.Book> response_books = new ArrayList<>();
        for(com.caterpillar.shamil.LibraryPet.entity.Books book : books){
            response_books.add(createBookProto(book));
        }
        responseObserver.onNext(Books.ListBooks.newBuilder().addAllBooks(response_books).build());
        responseObserver.onCompleted();
    }

    @Override
    public void getBookById(Books.Id request, StreamObserver<Books.Book> responseObserver) {
        var book = booksRepository.findById(request.getId());
        if(book.isEmpty()){
            responseObserver.onError(new NoSuchElementException("Книга не найдена"));
        }
        else {
            responseObserver.onNext(createBookProto(book.orElseThrow()));
        }
        responseObserver.onCompleted();
    }

    public static Books.Book createBookProto(com.caterpillar.shamil.LibraryPet.entity.Books book){
        return Books.Book.newBuilder()
                                .setId(book.getId())
                                .setTitle(book.getTitle())
                                .setAuthor(book.getAuthor())
                                .setActive(book.isActive())
                                .build();
    }
}
