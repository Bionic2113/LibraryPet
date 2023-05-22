package com.caterpillar.shamil.LibraryPet.grpc.books;

import com.caterpillar.shamil.LibraryPet.repository.BooksRepository;
import com.caterpillar.shamil.proto.Books;
import com.caterpillar.shamil.proto.PostBookServiceGrpc;
import io.grpc.stub.StreamObserver;
import org.lognet.springboot.grpc.GRpcService;

import static com.caterpillar.shamil.LibraryPet.grpc.books.BooksGetServiceGrpc.createBookProto;

/**
 * <h3>SpringPet</h3>
 *
 * @author Shamil Shyhiev
 * <a href="https://t.me/bionic2113">telegram<a>
 * @date 07.05.2023
 */
@GRpcService
public class BooksPostServiceGrpc extends PostBookServiceGrpc.PostBookServiceImplBase {

    private final BooksRepository booksRepository;

    public BooksPostServiceGrpc(BooksRepository booksRepository) {
        this.booksRepository = booksRepository;
    }

    @Override
    public void createBook(Books.BookRequest request, StreamObserver<Books.Book> responseObserver) {
        var book = new com.caterpillar.shamil.LibraryPet.entity.Books(
                                    request.getTitle(),
                                    request.getAuthor());
        booksRepository.save(book);
        var proto_book = createBookProto(book);
        responseObserver.onNext(proto_book);
        responseObserver.onCompleted();
    }
}
