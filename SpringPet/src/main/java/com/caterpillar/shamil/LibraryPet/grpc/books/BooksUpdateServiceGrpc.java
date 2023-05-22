package com.caterpillar.shamil.LibraryPet.grpc.books;

import com.caterpillar.shamil.LibraryPet.repository.BooksRepository;
import com.caterpillar.shamil.proto.Books;
import com.caterpillar.shamil.proto.UpdateBookServiceGrpc;
import com.google.protobuf.Empty;
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
public class BooksUpdateServiceGrpc extends UpdateBookServiceGrpc.UpdateBookServiceImplBase {

    private final BooksRepository booksRepository;

    public BooksUpdateServiceGrpc(BooksRepository booksRepository) {
        this.booksRepository = booksRepository;
    }

    @Override
    public void updateBook(Books.Book request, StreamObserver<Books.Book> responseObserver) {
        var book = booksRepository.findById(request.getId());
        if(book.isEmpty()){
            responseObserver.onError(new NoSuchElementException("Книга не найдена"));
        }
        else {
            var book_true  = book.orElseThrow();
            book_true.setTitle(request.getTitle());
            book_true.setAuthor(request.getAuthor());
            booksRepository.save(book_true);
            responseObserver.onNext(request);
        }
        responseObserver.onCompleted();
    }
}
