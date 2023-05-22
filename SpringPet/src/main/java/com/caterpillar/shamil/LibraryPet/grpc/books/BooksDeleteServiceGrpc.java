package com.caterpillar.shamil.LibraryPet.grpc.books;

import com.caterpillar.shamil.LibraryPet.repository.BooksRepository;
import com.caterpillar.shamil.proto.Books;
import com.caterpillar.shamil.proto.DeleteBookServiceGrpc;
import io.grpc.stub.StreamObserver;
import org.lognet.springboot.grpc.GRpcService;

import java.util.NoSuchElementException;

/**
 * <h3>SpringPet</h3>
 *
 * @author Shamil Shyhiev
 * <a href="https://t.me/bionic2113">telegram<a>
 * @date 07.05.2023
 */
@GRpcService
public class BooksDeleteServiceGrpc extends DeleteBookServiceGrpc.DeleteBookServiceImplBase {

    private final BooksRepository booksRepository;

    public BooksDeleteServiceGrpc(BooksRepository booksRepository) {
        this.booksRepository = booksRepository;
    }

    @Override
    public void deleteBook(Books.Id request, StreamObserver<Books.Book> responseObserver) {
        var book = booksRepository.findById(request.getId());
        if(book.isEmpty()){
            responseObserver.onError(new NoSuchElementException("Книга не найдена"));
        }
        else {
            var book_true  = book.orElseThrow();
            book_true.setActive(false);
            booksRepository.save(book_true);
            responseObserver.onNext(Books.Book.newBuilder()
                    .setId(book_true.getId())
                    .setTitle(book_true.getTitle())
                    .setAuthor(book_true.getAuthor())
                    .setActive(false)
                    .build());
        }
        responseObserver.onCompleted();
    }
}
